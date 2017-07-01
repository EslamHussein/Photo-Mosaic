package com.canva.photomosaic.business;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.canva.photomosaic.model.dto.Tile;

import java.util.List;

public class BitmapCombiner {


    public Tile combineBitmapsHorizontal(List<Tile> tiles, int width, int height) {

        Tile tile = new Tile();
        tile.setXPos(tiles.get(0).getXPos());
        tile.setYPos(tiles.get(0).getYPos());
        Bitmap tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tempBitmap);
        int left = 0;
        for (int i = 0; i < tiles.size(); i++) {
            canvas.drawBitmap(tiles.get(i).getBitmap(), left, 0f, null);
            left += tiles.get(i).getWidth();
        }
        tile.setBitmap(tempBitmap);

        tile.setWidth(width);
        tile.setHeight(height);
        return tile;
    }

    public Bitmap combineBitmapsVertical(Bitmap originalBitmap, Tile newTile) {
        Bitmap smallBitmap = Bitmap.createBitmap(newTile.getWidth(), newTile.getHeight(), Bitmap.Config.ARGB_8888);
        smallBitmap.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(originalBitmap);
        int top = newTile.getYPos();
        canvas.drawBitmap(smallBitmap, 0f, top, null);
        canvas.drawBitmap(newTile.getBitmap(), 0f, top, null);

        return originalBitmap;
    }
}
