package com.canva.photomosaic.ui.view;

import android.graphics.Bitmap;

import com.canva.base.view.MvpView;

public interface PhotoMosaicView extends MvpView {

    void showLoading(String loadingMessage);

    void hideLoading();

    void updateStatus(String status, Bitmap updatedBitmap);

    void success(String status, Bitmap updatedBitmap);


    void failure(String status, String message);
}
