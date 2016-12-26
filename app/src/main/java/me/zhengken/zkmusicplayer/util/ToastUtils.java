package me.zhengken.zkmusicplayer.util;

/**
 * Created by zheng on 2016/11/14.
 *
 * Android Toast封装
 */

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import me.zhengken.zkmusicplayer.MyApplication;
import me.zhengken.zkmusicplayer.R;

public class ToastUtils {

    // 短时间显示Toast信息
    public static void showShort(Context context, String info) {
        Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, MyApplication.getContext().getResources().getInteger(R.integer.toast_margin_top_center_in_screen));
        toast.show();
    }

    // 长时间显示Toast信息
    public static void showLong(Context context, String info) {
        Toast toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0,  MyApplication.getContext().getResources().getInteger(R.integer.toast_margin_top_center_in_screen));
        toast.show();
    }

}
