package com.canva.photomosaic.ui.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.canva.photomosaic.business.BitmapCombiner;
import com.canva.photomosaic.business.PhotoMosaicBusiness;
import com.canva.photomosaic.model.dto.Tile;
import com.canva.util.Defs;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PhotoMosaicPresenterImpl extends PhotoMosaicPresenter {
    public static final String TAG = "MosaicPresenterImpl";
    private PhotoMosaicBusiness photoMosaicBusiness;

    @Override
    public void startOperation(Bitmap bitmap) {
        photoMosaicBusiness = new PhotoMosaicBusiness(bitmap, 32, 32);
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

                        getView().updateStatus(Defs.RETRIEVING_IMAGE);
                        getBitmap(bitmap, tiles, bitmap.getWidth(), bitmap.getHeight());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached())
                            return;
                        getView().failure(e.getMessage());
                        getView().hideLoading();
                        getView().enablePickButton();


                    }

                    @Override
                    public void onComplete() {


                    }
                });

    }

    @Override
    public void getBitmap(final Bitmap originalBitmap, final List<List<Tile>> tiles, final int originalBitmapWidth, final int originalBitmapHeight) {


        if (!isViewAttached())
            return;
        getView().updateStatus(Defs.RETRIEVING_IMAGE);

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
                getView().failure(e.getMessage());
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
