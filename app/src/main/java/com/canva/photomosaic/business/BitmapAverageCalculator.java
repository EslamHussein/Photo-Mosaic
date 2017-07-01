package com.canva.photomosaic.business;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.canva.photomosaic.model.dto.Tile;

import java.util.List;

public class BitmapAverageCalculator {


    public String calculate(Bitmap originalBitmap, Tile tile) {

        Bitmap temp = Bitmap.createBitmap(originalBitmap, tile.getXPos(), tile.getYPos(), tile.getWidth(), tile.getHeight());

        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int pixelCount = 0;

        for (int y = 0; y < temp.getHeight(); y++) {
            for (int x = 0; x < temp.getWidth(); x++) {
                int c = temp.getPixel(x, y);

                pixelCount++;
                redBucket += Color.red(c);
                greenBucket += Color.green(c);
                blueBucket += Color.blue(c);
                // does alpha matter?
            }
        }
        return String.format("%02x%02x%02x", (redBucket / pixelCount), greenBucket / pixelCount, blueBucket / pixelCount);

    }
}
