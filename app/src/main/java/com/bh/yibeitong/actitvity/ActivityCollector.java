package com.bh.yibeitong.actitvity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingang on 2016/10/19.
 * 管理所有的Activity
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}


