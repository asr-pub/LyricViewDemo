package me.zhengken.zkmusicplayer.eventbus;

import android.support.annotation.Nullable;

/**
 * Created by zheng on 2016/10/4.
 */

public class MusicControlEvent {

    public static int MUSIC_CONTROL_PLAY = 0;

    public static int MUSIC_CONTROL_PAUSE = 1;

    public int mCtrlId;

    public String mSongPath;

    public MusicControlEvent(int ctrlId, @Nullable String songPath) {
        mCtrlId = ctrlId;
        mSongPath = songPath;
    }

}
