package com.redmart.android.utils.scheduler;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler getMainScheduler();
    Scheduler getBackgroundScheduler();

}
