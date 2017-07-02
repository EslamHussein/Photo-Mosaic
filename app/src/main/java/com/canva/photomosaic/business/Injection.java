package com.canva.photomosaic.business;

import android.graphics.Bitmap;

import com.canva.photomosaic.model.cloud.CloudRepo;

/**
 * Created by eslamhusseinawad on 7/2/17.
 */

public class Injection {


    public static BitmapAverageCalculator provideBitmapAverageCalculator() {
        return new BitmapAverageCalculator();
    }

    public static BitmapDivider provideBitmapDivider(Bitmap originalBitmap, int tileWidth, int tileHeight) {
        return new BitmapDivider(originalBitmap, tileWidth, tileHeight);
    }

    public static BitmapCombiner provideBitmapCombiner() {
        return new BitmapCombiner();
    }

    public static CloudRepo provideCloudRepo() {
        return new CloudRepo();
    }
}
