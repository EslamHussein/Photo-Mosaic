package com.canva.photomosaic.ui.view;

import android.graphics.Bitmap;

import com.canva.base.view.MvpView;
import com.canva.util.Defs;

public interface PhotoMosaicView extends MvpView {

    void showLoading();

    void hideLoading();

    void updateStatus(@Defs.ImageStatus String status);

    void updateBitmap(Bitmap updatedBitmap);


    void failure(String message);

    void disablePickButton();

    void enablePickButton();
}
