package me.zhengken.zkmusicplayer.musicdata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import me.zhengken.zkmusicplayer.MyApplication;
import me.zhengken.zkmusicplayer.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhengken.me on 2016/11/27.
 * ClassName    : Song
 * Description  :
 */

public class Song {


    public Song(@NonNull Uri songUri) {
        checkNotNull(songUri);

        try {
            String songPath = URLDecoder.decode(songUri.getPath().toString(), "UTF8");
            mSongPath = songPath;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Song(@NonNull String songPath) {
        mSongPath = checkNotNull(songPath);
    }


    private String mTitle;

    private String mArtist;

    private String mLrcPath;

    private String mSongPath;

    private int mDuration;

    private Bitmap mCover;

    private Bitmap mCoverMirror;

    private Bitmap mCoverGauss;

    public String getmSongPath() {
        return mSongPath;
    }

    private void setLrcPath() {
        checkNotNull(mSongPath);

        String lrcPath = mSongPath.substring(0, mSongPath.length() - 3) + "lrc";
        mLrcPath = lrcPath;
    }

    public String getLrcPath() {
        if (mLrcPath == null) {
            setLrcPath();
        }

        if (isLrcExist()) {
            return mLrcPath;
        }
        return null;
    }

    public boolean isLrcExist() {
        checkNotNull(mLrcPath);

        File lrcFile = new File(mLrcPath);
        if (lrcFile.exists()) {
            return true;
        }
        return false;
    }

    public void setmTitle() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mSongPath);
        mTitle = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
    }

    public String getmTitle() {
        if (mTitle == null)
            setmTitle();
        return mTitle;
    }

    private void setArtist() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mSongPath);
        mArtist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
    }

    public String getArtist() {
        if (mArtist == null) {
            setArtist();
        }
        return mArtist;
    }

    public void setDuration() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mSongPath);
        mDuration = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }

    public int getDuration() {
        if (mDuration == 0) {
            setDuration();
        }
        return mDuration;
    }

    public String getFormatDuration() {
        long time = getDuration();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        double minute = calendar.get(Calendar.MINUTE);
        double second = calendar.get(Calendar.SECOND);

        DecimalFormat format = new DecimalFormat("00");
        return format.format(minute) + ":" + format.format(second);
    }


    public void setCover() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mSongPath);
        byte[] bitmap = mmr.getEmbeddedPicture();
        if (bitmap != null) {
            mCover = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        } else {
            mCover = BitmapFactory.decodeResource(MyApplication.getContext().getResources(),
                    R.drawable.default_cover);
        }
    }

    public Bitmap getCover() {
        if (mCover == null) {
            setCover();
        }
        return mCover;
    }

    public void setCoverMirror() {
    }

    public void setCoverGauss() {
    }
}
