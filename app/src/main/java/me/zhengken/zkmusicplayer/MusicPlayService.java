package me.zhengken.zkmusicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import me.zhengken.zkmusicplayer.eventbus.MediaPlayerCreatedEvent;
import me.zhengken.zkmusicplayer.eventbus.MusicControlEvent;
import me.zhengken.zkmusicplayer.eventbus.PlayServiceCreatedEvent;
import me.zhengken.zkmusicplayer.eventbus.UpdateUiEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhengken.me on 2016/11/27. ClassName    : MusicPlayService Description  :
 */

public class MusicPlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    private static final String TAG = MusicPlayService.class.getSimpleName();

    public static final int MUSIC_CONTROL_PLAY = 0;

    public static final int MUSIC_CONTROL_PAUSE = 1;

    private MediaPlayer mMediaPlayer;

    private State mCurrentState;

    private String mSongPath;


    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MediaPlayerCreatedEvent(mMediaPlayer));
        EventBus.getDefault().post(new PlayServiceCreatedEvent());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initMediaPlayer() {
        checkNotNull(mSongPath);
        try {
            mMediaPlayer.reset();
            setCurrentState(State.STATE_IDLE);
            mMediaPlayer.setDataSource(mSongPath);
            setCurrentState(State.START_INITIALIZED);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        setCurrentState(State.STATE_PREPARED);
        play();
    }

    @Subscribe
    public void onMusicControlEvent(MusicControlEvent event) {
        Log.d(TAG, "onMusicControlEvent()");
        switch (event.mCtrlId) {
            case MUSIC_CONTROL_PLAY:
                // resume music
                if (mSongPath != null && mSongPath.equals(event.mSongPath)) {
                    play();
                    return;
                }

                mSongPath = checkNotNull(event.mSongPath);
                initMediaPlayer();
                break;
            case MUSIC_CONTROL_PAUSE:
                pause();
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, mp.getCurrentPosition() + "");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.reset();
        mMediaPlayer.release();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void play() {
        if (mMediaPlayer != null && (mCurrentState == State.STATE_PAUSE || mCurrentState == State.STATE_PREPARED || mCurrentState == State.STATE_PLAYING || mCurrentState == State.STATE_COMPLETE)) {
            mMediaPlayer.start();
            setCurrentState(State.STATE_PLAYING);
            EventBus.getDefault().post(new UpdateUiEvent());
        }
    }

    private void pause() {
        if (mMediaPlayer != null && (mCurrentState == State.STATE_PLAYING || mCurrentState == State.STATE_PAUSE || mCurrentState == State.STATE_COMPLETE)) {
            mMediaPlayer.pause();
            setCurrentState(State.STATE_PAUSE);
        }
    }

    private void setCurrentState(State currentState) {
        mCurrentState = currentState;
    }

    private enum State {
        STATE_STOPPED, STATE_PREPARED, STATE_PLAYING, STATE_PAUSE, STATE_IDLE, STATE_END, STATE_ERROR, STATE_COMPLETE, START_INITIALIZED
    }
}
