package me.zhengken.zkmusicplayer;

import android.app.Application;
import android.content.Context;

/**
 * Created by zheng on 2016/10/7.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}
