package com.canva.base.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.canva.base.presenter.MvpPresenter;


public abstract class BaseFragment<T extends MvpPresenter> extends Fragment implements MvpView {

    private T presenter;

    protected
    @NonNull
    T getPresenter() {
        if (presenter == null)
            presenter = createPresenter();
        if (presenter == null)
            throw new IllegalStateException("createPresenter() implementation returns null!");
        return presenter;
    }

    protected abstract T createPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onAttach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDetach();
    }

}
