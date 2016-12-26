package me.zhengken.zkmusicplayer.mainactivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhengken.lyricview.LyricView;
import me.zhengken.zkmusicplayer.R;
import me.zhengken.zkmusicplayer.util.TextUtils;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainFragment extends Fragment implements MainContract.View, View.OnClickListener, SeekBar.OnSeekBarChangeListener, LyricView.OnPlayerClickListener {

    private static final String TAG = "MainFragment";

    private MainContract.Presenter mPresenter;

    private boolean displayLrc = false;

    @BindView(R.id.background_blur)
    ImageView mCoverGauss;

    @BindView(R.id.linear_layout_music_cover)
    LinearLayout mDisplayLrc;

    @BindView(R.id.custom_lyric_view)
    LyricView mLyricView;

    @BindView(R.id.cover)
    ImageView mCover;

    @BindView(R.id.cover_mirror)
    ImageView mCoverMirror;

    @BindView(R.id.music_title)
    TextView mTitle;

    @BindView(R.id.music_artist)
    TextView mArtist;

    @BindView(R.id.end_time)
    TextView mEndTime;

    @BindView(R.id.start_time)
    TextView mStartTime;

    @BindView(R.id.music_seek_bar)
    SeekBar mSeekBar;

    @BindView(R.id.btn_mode)
    ImageView mPlayMode;

    @BindView(R.id.btn_prev)
    ImageView mPrev;

    @BindView(R.id.btn_play_pause)
    ImageView mPlayPause;

    @BindView(R.id.btn_next)
    ImageView mNext;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListener();
    }

    @Override
    public void updateTitle(String title) {
        if (mTitle == null) {
            return;
        }
        checkNotNull(title);
        mTitle.setText(title);
    }

    @Override
    public void updateArtist(String artist) {
        if (mArtist == null) {
            return;
        }
        checkNotNull(artist);
        mArtist.setText(artist);
    }

    @Override
    public void setEndTime(String time) {
        if (mEndTime == null) {
            return;
        }
        mEndTime.setText(time);
    }

    @Override
    public void resetSeekBar(int max) {
        if (mSeekBar == null) {
            return;
        }
        mSeekBar.setMax(max);
        mSeekBar.setProgress(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_pause:
                mPresenter.onBtnPlayPausePressed();
                break;
            case R.id.linear_layout_music_cover:
                if (displayLrc = !displayLrc) {
                    mLyricView.setVisibility(View.VISIBLE);
                    mCover.setVisibility(View.GONE);
                    mCoverMirror.setVisibility(View.GONE);
                } else {
                    mLyricView.setVisibility(View.GONE);
                    mCover.setVisibility(View.VISIBLE);
                    mCoverMirror.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void setListener() {
        mDisplayLrc.setOnClickListener(this);
        mPlayMode.setOnClickListener(this);
        mPrev.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mLyricView.setOnPlayerClickListener(this);
    }

    @Override
    public void initLrcView(File lrcFile) {
        mLyricView.setLyricFile(lrcFile);
    }

    @Override
    public void updateLrcView(int progress) {
        mLyricView.setCurrentTimeMillis(progress);
    }

    @Override
    public void updateCoverGauss(Bitmap bitmap) {
        if (bitmap != null) {
            mCoverGauss.setImageBitmap(bitmap);
        } else {
            mCoverGauss.setImageDrawable(getResources().getDrawable(R.drawable.default_cover_blur));
        }
    }

    @Override
    public void updateCover(Bitmap bitmap) {
        if (bitmap != null) {
            mCover.setImageBitmap(bitmap);
        } else {
            mCover.setImageDrawable(getResources().getDrawable(R.drawable.default_cover));
        }
    }

    @Override
    public void updateCoverMirror(Bitmap bitmap) {
        if (bitmap != null) {
            mCoverMirror.setImageBitmap(bitmap);
        } else {
            mCover.setImageDrawable(getResources().getDrawable(R.drawable.default_cover_mirror));
        }
    }

    @Override
    public void updatePlayButton(boolean setPlayImage) {
        if (mPlayPause == null) {
            return;
        }
        if (setPlayImage) {
            mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.btn_play_selector));
        } else {
            mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.btn_pause_selector));
        }
    }

    @Override
    public void updateSeekBar(int progress) {
        if (mSeekBar == null || mStartTime == null) {
            return;
        }
        mStartTime.setText(TextUtils.duration2String(progress));
        mSeekBar.setProgress(progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mPresenter.onProgressChanged(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mPresenter.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPresenter.play();
    }

    @Override
    public void onPlayerClicked(long progress, String content) {
        mPresenter.play();
        mPresenter.onProgressChanged((int) progress);
    }
}
