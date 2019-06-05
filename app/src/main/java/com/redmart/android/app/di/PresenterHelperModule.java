package com.redmart.android.app.di;

import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.disposable.DisposableManager;
import com.redmart.android.utils.scheduler.SchedulerProvider;
import com.redmart.android.utils.scheduler.SchedulerProviderImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterHelperModule {

    @Provides
    @Singleton
    UIModelCreator providesUIModelCreator() {
        return new UIModelCreator();
    }

    @Provides
    @Singleton
    SchedulerProvider providesScheduler() {
        return new SchedulerProviderImp();
    }

    @Provides
    @Singleton
    DisposableManager providesDisposableManager() {
        return new DisposableManager();
    }


}
