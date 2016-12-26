package me.zhengken.zkmusicplayer.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.zhengken.zkmusicplayer.MusicPlayService;
import me.zhengken.zkmusicplayer.R;
import me.zhengken.zkmusicplayer.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MainPresenter mPresenter;

    private Intent mPlayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
            //Create the fragment
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }

        mPlayService = new Intent(this, MusicPlayService.class);
        startService(mPlayService);
        mPresenter = new MainPresenter(mainFragment, this);

        mPresenter.processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        super.onNewIntent(intent);

        mPresenter.processIntent(intent);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        stopService(mPlayService);
        mPresenter.destroy();
    }
}
