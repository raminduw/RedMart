package com.redmart.android.app.di;

import com.redmart.android.utils.scheduler.SchedulerProvider;
import com.redmart.android.utils.scheduler.SchedulerProviderImp;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SchedulerModule {

    @Binds
    @Singleton
    abstract SchedulerProvider bindScheduler(SchedulerProviderImp schedulerProvider) ;

}
