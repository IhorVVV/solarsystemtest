package com.ihor.solarsystem;


import android.app.Application;

public class SolarSystemApplication extends Application {

    private static SolarSystemApplication sApplication;

    public static SolarSystemApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
