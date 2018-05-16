package com.redmart.android;

import android.app.Application;
/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
