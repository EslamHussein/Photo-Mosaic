package com.canva;

import android.app.Application;

public class App extends Application {
    private static App instance;


    public static App get() {
        if (instance == null)
            throw new IllegalStateException("Something went horribly wrong, no application attached!");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
