package com.canva.photomosaic.model.dto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

public class Tile {

    private int xPos;
    private int yPos;
    private transient Bitmap bitmap;
    private Bitmap newBitmap;
    private String avgColor;
    private int width;
    private int height;


    public Tile() {
    }

    public Tile(int xPos, int yPos, Bitmap bitmap, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAvgColor() {
        return avgColor;
    }

    public void setAvgColor(String avgColor) {
        this.avgColor = avgColor;
    }

    public Bitmap getNewBitmap() {
        return newBitmap;
    }

    public void setNewBitmap(Bitmap newBitmap) {
        this.newBitmap = newBitmap;
    }
}
