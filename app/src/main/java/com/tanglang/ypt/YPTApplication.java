package com.tanglang.ypt;

import android.app.Activity;
import android.app.Application;


import java.util.ArrayList;
import java.util.List;

public class YPTApplication extends Application {

    private List<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();

        activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void exitApp() {
        List<Activity> list = new ArrayList<>();
        list.addAll(activities);

        for (int i = 0; i < list.size(); i++) {
            activities.get(i).finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());//获取PID
        System.exit(0);
    }
}
