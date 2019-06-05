package com.redmart.android.app.di;

import com.redmart.android.utils.scheduler.SchedulerProvider;
import com.redmart.android.utils.scheduler.SchedulerProviderImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerModule {

    @Provides
    @Singleton
    SchedulerProvider providesScheduler() {
        return new SchedulerProviderImp();
    }

}
