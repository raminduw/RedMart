package com.redmart.android.app.di;

import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.disposable.DisposableManager;
import com.redmart.android.utils.scheduler.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, PresenterHelperModule.class})

public interface AppComponent {

    RedMartApi getRemoteApi();

    UIModelCreator getUIModelCreator();

    SchedulerProvider getSchedulerProvider();

    DisposableManager getDisposableManager();

}
