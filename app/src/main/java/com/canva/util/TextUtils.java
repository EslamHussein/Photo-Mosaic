package com.canva.util;

import android.app.Activity;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.canva.App;

public class TextUtils {
    public static String getString(@StringRes int resId) {
        return App.get().getString(resId);
    }

    public static int getColor(@ColorRes int resId, Activity activity) {
        return activity.getResources().getColor(resId);
    }
}
