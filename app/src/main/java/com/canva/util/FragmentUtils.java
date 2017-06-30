package com.canva.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;

/**
 * Created by eslamhusseinawad on 6/26/17.
 */

public class FragmentUtils {

    /**
     * @param activity
     * @param fragment
     * @param container
     * @param isNeedToAddToStack true if you need to push this fragment to stack
     * @param fragmentTag
     */
    public static void replaceFragment(Activity activity, Fragment fragment, @IdRes int container,
                                       boolean isNeedToAddToStack,
                                       String fragmentTag) {

        String fragTag = getFragmentTag(fragment, fragmentTag);
        FragmentManager manager = activity.getFragmentManager();
        boolean isInStack = manager.popBackStackImmediate(fragTag, 0);
        FragmentTransaction ft = manager.beginTransaction();

        if (isInStack) {
            fragment = manager.findFragmentByTag(fragTag);
        }
        ft.replace(container, fragment, fragTag);
        if (!isInStack && isNeedToAddToStack) {
            ft.addToBackStack(fragTag);
        }

        ft.commit();

    }

    private static String getFragmentTag(Fragment fragment, String fragmentTag) {
        String backStateName;
        if (fragmentTag == null) {
            backStateName = fragment.getClass().getName();
        } else {
            backStateName = fragmentTag;
        }
        return backStateName;
    }
}
