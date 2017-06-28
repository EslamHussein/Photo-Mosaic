package com.canva.util;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by eslamhusseinawad on 6/27/17.
 */

public class BitmapAverageCalculator {

    public String calculate(Bitmap bitmap) {

        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int pixelCount = 0;

        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                int c = bitmap.getPixel(x, y);

                pixelCount++;
                redBucket += Color.red(c);
                greenBucket += Color.green(c);
                blueBucket += Color.blue(c);
                // does alpha matter?
            }
        }
        return String.format("#%02x%02x%02x", (redBucket / pixelCount), greenBucket / pixelCount, blueBucket / pixelCount);

    }


}
