package com.canva.photomosaic.business;

import android.graphics.Bitmap;
import android.util.Log;

import com.canva.base.exception.AppException;
import com.canva.photomosaic.model.cloud.CloudRepo;
import com.canva.photomosaic.model.dto.Tile;
import com.canva.util.BitmapAverageCalculator;
import com.canva.util.BitmapDivider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eslamhusseinawad on 6/27/17.
 */

public class PhotoMosaicBusiness {
    private static final String TAG = PhotoMosaicBusiness.class.getName();


    BitmapDivider bitmapDivider;
    BitmapAverageCalculator averageCalculator;

    CloudRepo cloudRepo;

    public List<List<Tile>> divideImage(Bitmap originalBitmap) throws Exception {

        bitmapDivider = new BitmapDivider(32, 32);
        averageCalculator = new BitmapAverageCalculator();

        List<List<Tile>> tiles = bitmapDivider.divide(originalBitmap);// divided to tiles
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                Tile temp = tiles.get(i).get(j);
                temp.setAvgColor(averageCalculator.calculate(temp.getBitmap()));
                tiles.get(i).set(j, temp);
            }
        }

        return tiles;

    }


    public Bitmap getBitmap(Tile tile) throws IOException {
        return cloudRepo.getImage(tile);
    }


    public Bitmap mergeBitmap() {
//        List<Tile> tilesRows = new ArrayList<>();
//        for (int i = 0; i < tiles.size(); i++) {
//            Tile tile = bitmapDivider.combineBitmapsToRow(tiles.get(i), originalBitmap.getWidth(), tiles.get(i).get(0).getHeight());
//            tilesRows.add(tile);
//        }
//
//        return bitmapDivider.combineImageRowsToBitmaps(tilesRows, originalBitmap.getWidth(), originalBitmap.getHeight());
        return null;
    }


}
