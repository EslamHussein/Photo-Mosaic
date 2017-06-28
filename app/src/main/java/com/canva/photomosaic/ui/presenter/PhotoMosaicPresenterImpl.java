package com.canva.photomosaic.ui.presenter;

import android.graphics.Bitmap;

import com.canva.photomosaic.business.PhotoMosaicBusiness;
import com.canva.photomosaic.model.dto.Tile;

import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoMosaicPresenterImpl extends PhotoMosaicPresenter {
    PhotoMosaicBusiness photoMosaicBusiness = new PhotoMosaicBusiness();

    @Override
    public void startOperation(Bitmap bitmap) {

        divideImage(bitmap);
    }

    @Override
    public void divideImage(final Bitmap bitmap) {

        if (!isViewAttached())
            getView().showLoading("Loading");

        final Observable<List<List<Tile>>> dividerObservable = Observable.fromCallable(new Callable<List<List<Tile>>>() {
            @Override
            public List<List<Tile>> call() throws Exception {
                return photoMosaicBusiness.divideImage(bitmap);
            }
        });


        dividerObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<List<List<Tile>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<List<Tile>> tiles) {


                if (!isViewAttached())
                    return;

                getView().success("Update", bitmap);
                getBitmap(tiles.get(0).get(0));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                if (!isViewAttached())
                    getView().hideLoading();


            }
        });

    }

    @Override
    public void getBitmap(final Tile tile) {


        Observable<Bitmap> getBitmapObservable = Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return photoMosaicBusiness.getBitmap(tile);
            }
        });


        getBitmapObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {


                if (!isViewAttached())
                    return;

                getView().success("Update", bitmap);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                if (!isViewAttached())
                    getView().hideLoading();


            }
        });

    }
}
