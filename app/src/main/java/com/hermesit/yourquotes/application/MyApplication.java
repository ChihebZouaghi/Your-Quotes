package com.hermesit.yourquotes.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Chiheb on 06/08/2016.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }




}
