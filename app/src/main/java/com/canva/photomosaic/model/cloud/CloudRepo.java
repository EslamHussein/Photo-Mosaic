package com.canva.photomosaic.model.cloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.canva.photomosaic.model.dto.Tile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class CloudRepo {

    private static final String BASE_URL = "http://192.168.1.9:8765/color/";

    public Bitmap getImage(Tile tile) throws IOException {
        URL url = new URL(buildUrl(tile));
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoInput(true);
        connection.setConnectTimeout(1000);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;
    }


    private String buildUrl(Tile tile) {
        return BASE_URL.concat(String.valueOf(tile.getWidth()).concat("/").
                concat(String.valueOf(tile.getHeight())).concat("/").concat(tile.getAvgColor()));
    }
}
