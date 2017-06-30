package com.canva.util;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Defs {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            DIVIDE_IMAGE,
            COMPLETE,
            RETRIEVING_IMAGE
    })
    public @interface ImageStatus {
    }

    public static final String DIVIDE_IMAGE = "dividing_the_image";
    public static final String COMPLETE = "COMPLETE";
    public static final String RETRIEVING_IMAGE = "retrieving_image";

}
