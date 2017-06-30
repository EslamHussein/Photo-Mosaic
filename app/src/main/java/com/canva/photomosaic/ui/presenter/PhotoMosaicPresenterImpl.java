package com.canva.photomosaic.ui.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.canva.photomosaic.business.BitmapCombiner;
import com.canva.photomosaic.business.PhotoMosaicBusiness;
import com.canva.photomosaic.model.dto.Tile;
import com.canva.util.Defs;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PhotoMosaicPresenterImpl extends PhotoMosaicPresenter {
    public static final String TAG = "MosaicPresenterImpl";
    private PhotoMosaicBusiness photoMosaicBusiness;
    private Bitmap updatedBitmap;

    @Override
    public void startOperation(Bitmap bitmap) {
        updatedBitmap = bitmap;
        photoMosaicBusiness = new PhotoMosaicBusiness(bitmap, 32, 32);
        if (!isViewAttached())
            return;
        getView().showLoading();
        divideImage(bitmap);
    }

    @Override
    public void divideImage(final Bitmap bitmap) {

        if (!isViewAttached())
            return;
        getView().updateStatus(Defs.DIVIDE_IMAGE, bitmap);

        photoMosaicBusiness.divideImage().observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Observer<List<List<Tile>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<Tile>> tiles) {
                        if (!isViewAttached())
                            return;

                        getView().updateStatus(Defs.RETRIEVING_IMAGE, bitmap);
                        getBitmap(bitmap, tiles, bitmap.getWidth(), bitmap.getHeight());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached())
                            return;
                        getView().failure(e.getMessage());
                        getView().hideLoading();

                    }

                    @Override
                    public void onComplete() {


                    }
                });

    }

    @Override
    public void getBitmap(final Bitmap originalBitmap, final List<List<Tile>> tiles, final int originalBitmapWidth, final int originalBitmapHeight) {

        getView().updateStatus(Defs.RETRIEVING_IMAGE, null);

        updatedBitmap =  Bitmap.createBitmap(originalBitmapWidth, originalBitmapHeight, Bitmap.Config.ARGB_8888);


        List<Observable<Tile>> observables = new ArrayList<>();
        for (List<Tile> tile : tiles) {
            observables.add(photoMosaicBusiness.zipRowOfTilesObservables(tile, originalBitmapWidth));
        }


        Observable.mergeDelayError(observables).doOnNext(new Consumer<Tile>() {
            @Override
            public void accept(Tile tile) throws Exception {
                BitmapCombiner bitmapCombine = new BitmapCombiner();
                updatedBitmap = bitmapCombine.combineBitmapsVertical(updatedBitmap, tile, originalBitmapWidth, originalBitmapHeight);
                tile.setBitmap(updatedBitmap);
                tile.setAvgColor("Eslam");

                Log.d(TAG, "accept() returned: " + tile.getHeight());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Tile>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Tile tile) {


                getView().updateStatus(Defs.RETRIEVING_IMAGE, tile.getBitmap());


            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
                getView().updateStatus(Defs.COMPLETE, null);

            }
        });

       /* photoMosaicBusiness.zipRowsToBigTile(observables, originalBitmapWidth, originalBitmapHeight)
                .observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {

                        if (!isViewAttached())
                            return;

                        getView().updateStatus(Defs.RETRIEVING_IMAGE, bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached())
                            return;
                        getView().failure(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                        if (!isViewAttached())
                            return;
                        getView().hideLoading();


                    }
                });
*/
    }
}
