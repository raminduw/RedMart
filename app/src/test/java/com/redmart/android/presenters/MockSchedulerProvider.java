package com.redmart.android.presenters;

import com.redmart.android.utils.scheduler.SchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MockSchedulerProvider implements SchedulerProvider {
    @Override
    public Scheduler getMainScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getBackgroundScheduler() {
        return Schedulers.trampoline();
    }
}
