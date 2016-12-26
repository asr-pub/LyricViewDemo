package me.zhengken.zkmusicplayer.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zheng on 2016/10/7.
 */

public class TextUtils {
    public static String duration2String(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        double minute = calendar.get(Calendar.MINUTE);
        double second = calendar.get(Calendar.SECOND);

        DecimalFormat format = new DecimalFormat("00");
        return format.format(minute) + ":" + format.format(second);
    }
}
