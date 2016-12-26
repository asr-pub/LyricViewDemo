package me.zhengken.zkmusicplayer.eventbus;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zheng on 2016/10/7.
 */

public class MediaPlayerCreatedEvent {

    public MediaPlayer mMediaPlayer;

    public MediaPlayerCreatedEvent(@NonNull MediaPlayer mediaPlayer) {
        checkNotNull(mediaPlayer);
        mMediaPlayer = mediaPlayer;
    }
}
