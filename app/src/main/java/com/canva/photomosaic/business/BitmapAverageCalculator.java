package com.canva.photomosaic.business;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.canva.photomosaic.model.dto.Tile;

import java.util.List;

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
        return String.format("%02x%02x%02x", (redBucket / pixelCount), greenBucket / pixelCount, blueBucket / pixelCount);

    }

    public List<List<Tile>> getAverageColorForTiles(List<List<Tile>> tilesList) {

        for (int i = 0; i < tilesList.size(); i++) {
            for (int j = 0; j < tilesList.get(i).size(); j++) {
                Tile temp = tilesList.get(i).get(j);
                temp.setAvgColor(calculate(temp.getBitmap()));
                tilesList.get(i).set(j, temp);
            }
        }
        return tilesList;
    }


}
