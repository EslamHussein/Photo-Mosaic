package com.canva.photomosaic.ui.activity;

import android.os.Bundle;

import com.canva.base.view.BaseActivity;
import com.canva.photomosaic.R;
import com.canva.photomosaic.ui.fragment.PhotoMosaicFragment;
import com.canva.util.FragmentUtils;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUtils.replaceFragment(this, PhotoMosaicFragment.newInstance(), R.id.fragment_container, false, null);
    }
}
