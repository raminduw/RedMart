package com.redmart.android.utils.scheduler;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProviderImp implements SchedulerProvider {

    @Override
    public Scheduler getMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler getBackgroundScheduler() {
        return Schedulers.io();
    }
}
