package com.canva.photomosaic.ui.presenter;

import android.graphics.Bitmap;

import com.canva.base.presenter.BasePresenter;
import com.canva.photomosaic.model.dto.Tile;
import com.canva.photomosaic.ui.view.PhotoMosaicView;

import java.util.List;


public abstract class PhotoMosaicPresenter extends BasePresenter<PhotoMosaicView> {
    public abstract void startOperation(Bitmap bitmap);

    public abstract void divideImage(Bitmap bitmap);

    public abstract void showPhotoMosaic(Bitmap originalBitmap, List<List<Tile>> tile, int originalBitmapWidth, int originalBitmapHeight);

}


