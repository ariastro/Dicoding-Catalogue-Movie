package com.example.astronout.cataloguemovie30.service;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class UpcomingTask {

    private GcmNetworkManager mGcmNetworkManager;

    public UpcomingTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(UpcomingReminder.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(UpcomingReminder.TAG_TASK_UPCOMING)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(UpcomingReminder.TAG_TASK_UPCOMING, UpcomingReminder.class);
        }
    }
}

