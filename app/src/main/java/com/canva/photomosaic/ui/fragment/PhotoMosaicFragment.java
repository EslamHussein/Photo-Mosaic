package com.canva.photomosaic.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.canva.base.view.BaseFragment;
import com.canva.photomosaic.R;
import com.canva.photomosaic.ui.presenter.PhotoMosaicPresenter;
import com.canva.photomosaic.ui.presenter.PhotoMosaicPresenterImpl;
import com.canva.photomosaic.ui.view.PhotoMosaicView;
import com.canva.util.BitmapDivider;
import com.canva.util.TextUtils;

import java.io.IOException;


public class PhotoMosaicFragment extends BaseFragment<PhotoMosaicPresenter> implements PhotoMosaicView {

    private Button pickImageButton;
    private ImageView selectedImageView;
    private ImageView selectedTestImageView;

    private static final int PICK_FROM_GALLERY = 1000;

    private ProgressBar progressBar;

    public PhotoMosaicFragment() {
        // Required empty public constructor
    }


    public static PhotoMosaicFragment newInstance() {
        PhotoMosaicFragment fragment = new PhotoMosaicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_mosaic, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pickImageButton = (Button) view.findViewById(R.id.button_pick_image);
        selectedImageView = (ImageView) view.findViewById(R.id.image_view_selected_image);
        selectedTestImageView = (ImageView) view.findViewById(R.id.image_view_test_selected_image);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(galleryIntent, TextUtils.getString(R.string.pick_image_from_gallery)), PICK_FROM_GALLERY);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            if (data != null) {

                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    selectedImageView.setImageBitmap(bitmap);
                    getPresenter().startOperation(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    showLoading(e.getMessage());
                }
            }
        }

    }

    @Override
    protected PhotoMosaicPresenter createPresenter() {
        return new PhotoMosaicPresenterImpl();
    }

    @Override
    public void showLoading(String loadingMessage) {

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);


    }

    @Override
    public void updateStatus(String status, Bitmap updatedBitmap) {
        selectedTestImageView.setImageBitmap(updatedBitmap);

    }

    @Override
    public void success(String status, Bitmap updatedBitmap) {
        selectedTestImageView.setImageBitmap(updatedBitmap);

    }

    @Override
    public void failure(String status, String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();

    }
}
