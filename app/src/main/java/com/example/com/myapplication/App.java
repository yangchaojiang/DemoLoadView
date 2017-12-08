package com.example.com.myapplication;

import android.app.Application;

import com.helper.loadviewhelper.load.LoadViewHelper;

/**
 * Created by yangc on 2017/6/16.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
            LoadViewHelper.getBuilder()
                .setLoadEmpty(R.layout.this_error)
                .setLoadError(R.layout.this_error)
                .setLoadIng(R.layout.load_ing);
    }
}
