package com.canva.util;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Defs {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            DIVIDE_IMAGE,
            COMPLETE,
            LOADING_IMAGE,
            ERROR_MESSAGE
    })
    public @interface ImageStatus {
    }

    public static final String DIVIDE_IMAGE = "Dividing the image";
    public static final String COMPLETE = "Done!,you can select anther image.";
    public static final String LOADING_IMAGE = "Loading image...";
    public static final String ERROR_MESSAGE = "Something wrong,Please try again.";

}
