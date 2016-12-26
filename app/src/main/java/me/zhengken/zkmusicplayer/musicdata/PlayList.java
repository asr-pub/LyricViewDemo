package me.zhengken.zkmusicplayer.musicdata;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.zhengken.zkmusicplayer.eventbus.MusicControlEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhengken.me on 2016/11/27.
 * ClassName    : PlayList
 * Description  :
 */

public class PlayList {

    private ArrayList<Song> mSongList;

    private int mCurrIndex;

    private static PlayList mInstance;

    private Song mThirdSong;

    private boolean mIsThirdCall;

    private PlayList() {
        mSongList = new ArrayList<>();
        mCurrIndex = -1;
        mIsThirdCall = false;
    }

    public Song getThirdSong() {
        return mThirdSong;
    }

    public boolean isThirdCall() {
        return mIsThirdCall;
    }

    /**
     * @param mThirdSong 第三方传入音乐的值
     */
    public void setThirdSong(Song mThirdSong) {
        checkNotNull(mThirdSong);
        this.mThirdSong = mThirdSong;
    }

    /**
     * 是否第三方调用音乐播放程序
     *
     * @param mIsThirdCall -true 第三方调用 -false 非第三方调用
     */
    public void setIsThirdCall(boolean mIsThirdCall) {
        this.mIsThirdCall = mIsThirdCall;
    }

    public static PlayList getmInstance() {
        if (mInstance == null) {
            mInstance = new PlayList();
        }

        return mInstance;
    }

    public void addSong(@NonNull Song song) {
        checkNotNull(song);

        if (-1 == mCurrIndex) {
            mCurrIndex = 0;
        }

        mSongList.add(song);
    }

    public int getCurrIndex() {
        return mCurrIndex;
    }

    public Song getCurrSong() {
        if (mIsThirdCall) {
            return mThirdSong;
        }

        if (mSongList.size() == 0) {
            return null;
        }

        return mSongList.get(mCurrIndex);
    }

    public List<Song> getSongList() {
        return mSongList;
    }

    public void play() {
        String path;
        if (mIsThirdCall) {
            path = mThirdSong.getmSongPath();
        } else {
            path = mSongList.get(mCurrIndex).getmSongPath();
        }
        EventBus.getDefault().post(new MusicControlEvent(MusicControlEvent.MUSIC_CONTROL_PLAY, path));
    }

    public void pause() {
        EventBus.getDefault().post(new MusicControlEvent(MusicControlEvent.MUSIC_CONTROL_PAUSE, null));
    }

    public void next() {
    }

    public void prev() {
    }


}
