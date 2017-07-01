package com.canva.photomosaic.business;

import android.graphics.Bitmap;
import android.util.Log;

import com.canva.photomosaic.model.cloud.CloudRepo;
import com.canva.photomosaic.model.dto.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class PhotoMosaicBusiness {

    private BitmapDivider bitmapDivider;
    private BitmapAverageCalculator averageCalculator;
    private CloudRepo cloudRepo;
    private BitmapCombiner bitmapCombiner;
    private Bitmap originalBitmap;
    private static final String TAG = PhotoMosaicBusiness.class.getName();


    public PhotoMosaicBusiness(Bitmap originalBitmap, int tileWidth, int tileHeight) {
        this.originalBitmap = originalBitmap;
        bitmapDivider = new BitmapDivider(originalBitmap, tileWidth, tileHeight);
        averageCalculator = new BitmapAverageCalculator();
        cloudRepo = new CloudRepo();
        bitmapCombiner = new BitmapCombiner();
    }

    public Observable<List<List<Tile>>> divideImage() {


        return Observable.fromCallable(new Callable<List<List<Tile>>>() {
            @Override
            public List<List<Tile>> call() throws Exception {
                List<List<Tile>> tiles = bitmapDivider.divide();// divided to tiles
                return tiles;
            }
        });

    }

    private Observable<Tile> getEquivalentBitmapTile(final Tile tile) {


        return Observable.fromCallable(new Callable<Tile>() {
            @Override
            public Tile call() throws Exception {
                tile.setAvgColor(averageCalculator.calculate(originalBitmap, tile));
                tile.setBitmap(cloudRepo.getImage(tile));
                return tile;

            }
        });

    }

    private List<Observable<Tile>> getEquivalentBitmapsTiles(List<Tile> tiles) {

        List<Observable<Tile>> observables = new ArrayList<>();
        for (Tile tile : tiles) {
            observables.add(getEquivalentBitmapTile(tile));
        }
        return observables;
    }

    public Observable<Tile> zipRowOfTilesObservables(List<Tile> tiles, final int originalBitmapWidth) {

        return Observable.zip(getEquivalentBitmapsTiles(tiles), new Function<Object[], Tile>() {
            @Override
            public Tile apply(Object[] objects) throws Exception {

                List<Tile> tileList = new ArrayList<>();
                for (Object object : objects) {
                    Tile tile = (Tile) object;
                    tileList.add(tile);
                }

                return bitmapCombiner.combineBitmapsHorizontal(tileList, originalBitmapWidth, tileList.get(0).getHeight());


            }
        });

    }

    Bitmap mutableBitmap;

    public Observable<Tile> mergeRowsToBigTile(Bitmap imuoriginalBitmap, List<List<Tile>> tiles, final int originalBitmapWidth, final int originalBitmapHeight) {

        mutableBitmap = imuoriginalBitmap.copy(Bitmap.Config.ARGB_8888, true);

        List<Observable<Tile>> observables = new ArrayList<>();
        for (List<Tile> tile : tiles) {
            observables.add(zipRowOfTilesObservables(tile, originalBitmapWidth));
        }


        return Observable.mergeDelayError(observables).doOnNext(new Consumer<Tile>() {
            @Override
            public void accept(Tile tile) throws Exception {
                BitmapCombiner bitmapCombine = new BitmapCombiner();
                mutableBitmap = bitmapCombine.combineBitmapsVertical(mutableBitmap, tile);
                tile.setBitmap(mutableBitmap);

                Log.d(TAG, "accept() returned: " + tile.getHeight());
            }
        });


    }


}
