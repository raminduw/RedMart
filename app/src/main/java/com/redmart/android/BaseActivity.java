package com.redmart.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.app.di.AppComponent;
import com.redmart.android.app.di.DaggerAppComponent;
import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.disposable.DisposableManager;
import com.redmart.android.utils.scheduler.SchedulerProvider;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */
public class BaseActivity extends AppCompatActivity {

    protected Activity activity;
    protected RedMartApi redMartApi;
    protected UIModelCreator viewModelCreator;
    protected SchedulerProvider schedulerProvider;
    protected DisposableManager disposableManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initDagger();
    }

    private void initDagger() {
        AppComponent appComponent = DaggerAppComponent.builder().build();
        redMartApi = appComponent.getRemoteApi();
        viewModelCreator = appComponent.getUIModelCreator();
        schedulerProvider = appComponent.getSchedulerProvider();
        disposableManager = appComponent.getDisposableManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableManager.dispose();
    }


}
