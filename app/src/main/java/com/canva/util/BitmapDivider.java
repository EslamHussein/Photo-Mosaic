package com.canva.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.canva.photomosaic.R;
import com.canva.photomosaic.model.dto.Tile;

import java.util.ArrayList;
import java.util.List;


public class BitmapDivider {

    private static final String TAG = BitmapDivider.class.getName();
    private int tileWidth;
    private int tileHeight;

    public BitmapDivider(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public List<List<Tile>> divide(Bitmap originalBitmap) throws Exception {
        int rowTilesLength = calculateLength(originalBitmap.getHeight(), tileHeight); // l tool
        int columnTilesLength = calculateLength(originalBitmap.getWidth(), tileWidth); // l 3ard

        List<List<Tile>> tiles = new ArrayList<>();

        for (int i = 0; i < rowTilesLength; i++) {
            tiles.add(new ArrayList<Tile>());
            for (int j = 0; j < columnTilesLength; j++) {

                int tempTileWidth;
                int tempTileHeight;
                if (i == rowTilesLength - 1) {
                    tempTileHeight = originalBitmap.getHeight() - (i * tileHeight);
                } else {
                    tempTileHeight = tileHeight;
                }

                if (j == columnTilesLength - 1) {
                    tempTileWidth = originalBitmap.getWidth() - (j * tileWidth);
                } else {
                    tempTileWidth = tileWidth;
                }
                int x = j * tileWidth;
                int y = i * tileHeight;
                Bitmap temp = Bitmap.createBitmap(originalBitmap, x, y, tempTileWidth, tempTileHeight);
                tiles.get(i).add(new Tile(x, y, temp, tempTileWidth, tempTileHeight));
            }

        }
        return tiles;
    }

    // (a+b-1)/b
    private int calculateLength(int bitmapLength, int tileLength) throws Exception {
        if (bitmapLength < 1 || tileLength < 1) {
            throw new Exception(TextUtils.getString(R.string.error_with_image_size));
        }
        return (bitmapLength + tileLength - 1) / tileLength;
    }


    public Tile combineBitmapsToRow(List<Tile> tiles, int width, int height) {

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

    public Bitmap combineImageRowsToBitmaps(List<Tile> bitmaps, int width, int height) {
        Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        int top = 0;
        for (int i = 0; i < bitmaps.size(); i++) {
            canvas.drawBitmap(bitmaps.get(i).getBitmap(), 0f, top, null);
            top += bitmaps.get(i).getHeight();
        }
        return temp;
    }

}
