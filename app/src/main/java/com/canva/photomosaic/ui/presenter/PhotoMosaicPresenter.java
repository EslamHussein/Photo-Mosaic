package com.canva.photomosaic.ui.presenter;

import android.graphics.Bitmap;

import com.canva.base.presenter.BasePresenter;
import com.canva.photomosaic.model.dto.Tile;
import com.canva.photomosaic.ui.view.PhotoMosaicView;


public abstract class PhotoMosaicPresenter extends BasePresenter<PhotoMosaicView> {
    public abstract void startOperation(Bitmap bitmap);

    public abstract void divideImage(Bitmap bitmap);

    public abstract void getBitmap(Tile tile);

}


