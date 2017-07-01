package com.canva.photomosaic.ui.presenter;

import android.graphics.Bitmap;

import com.canva.photomosaic.business.PhotoMosaicBusiness;
import com.canva.photomosaic.model.dto.Tile;
import com.canva.util.Defs;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoMosaicPresenterImpl extends PhotoMosaicPresenter {
    public static final String TAG = "MosaicPresenterImpl";
    private PhotoMosaicBusiness photoMosaicBusiness;

    @Override
    public void startOperation(Bitmap bitmap) {
        photoMosaicBusiness = new PhotoMosaicBusiness(bitmap, 100, 100);
        if (!isViewAttached())
            return;
        getView().disablePickButton();
        getView().showLoading();
        divideImage(bitmap);
    }

    @Override
    public void divideImage(final Bitmap bitmap) {

        if (!isViewAttached())
            return;
        getView().updateStatus(Defs.DIVIDE_IMAGE);

        photoMosaicBusiness.divideImage().observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Observer<List<List<Tile>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<Tile>> tiles) {
                        if (!isViewAttached())
                            return;

                        getView().updateStatus(Defs.DIVIDE_IMAGE);
                        showPhotoMosaic(bitmap, tiles, bitmap.getWidth(), bitmap.getHeight());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached())
                            return;
                        getView().updateStatus(Defs.ERROR_MESSAGE);
                        getView().failure(Defs.ERROR_MESSAGE);
                        getView().hideLoading();
                        getView().enablePickButton();


                    }

                    @Override
                    public void onComplete() {


                    }
                });

    }

    @Override
    public void showPhotoMosaic(final Bitmap originalBitmap, final List<List<Tile>> tiles, final int originalBitmapWidth, final int originalBitmapHeight) {


        if (!isViewAttached())
            return;
        getView().updateStatus(Defs.LOADING_IMAGE);

        photoMosaicBusiness.mergeRowsToBigTile(originalBitmap, tiles, originalBitmapWidth, originalBitmapHeight).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).subscribe(new Observer<Tile>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Tile tile) {
                if (!isViewAttached())
                    return;
                getView().updateBitmap(tile.getBitmap());

            }


            @Override
            public void onError(Throwable e) {
                if (!isViewAttached())
                    return;
                getView().updateStatus(Defs.ERROR_MESSAGE);
                getView().failure(Defs.ERROR_MESSAGE);
                getView().hideLoading();
                getView().enablePickButton();


            }

            @Override
            public void onComplete() {
                if (!isViewAttached())
                    return;
                getView().updateStatus(Defs.COMPLETE);
                getView().hideLoading();
                getView().enablePickButton();


            }
        });

    }
}
