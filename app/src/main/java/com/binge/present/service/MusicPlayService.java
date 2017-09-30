package com.binge.present.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2017/9/29.
 */

public class MusicPlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private static final String TAG = "MusicPlayService";

    private MediaPlayer mMediaPlayer;
    private AssetFileDescriptor[] dataSource;
    private AssetFileDescriptor lastSource;
    private Random mRandom = new Random();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

        try {
            AssetManager assetManager = getAssets();
            String[] musics = assetManager.list("music");
            dataSource = new AssetFileDescriptor[musics.length];
            for (int i = 0; i < musics.length; i++) {
                String name = musics[i];
                AssetFileDescriptor assetFileDescriptor = assetManager.openFd("music/" + name);
                dataSource[i] = assetFileDescriptor;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setDataRes(mMediaPlayer);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            setDataRes(mediaPlayer);
        }
    }

    private AssetFileDescriptor getPlaySource() {
        int i = mRandom.nextInt(dataSource.length);
        AssetFileDescriptor data = dataSource[i];
        if (lastSource == data) {
            getPlaySource();
        } else {
            lastSource = data;
            return data;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mp != null) {
            mp.release();
        }
        return false;
    }

    private void setDataRes(MediaPlayer mediaPlayer) {
        try {
            AssetFileDescriptor playSource = getPlaySource();
            if (playSource != null) {
                mediaPlayer.setDataSource(playSource.getFileDescriptor(), playSource.getStartOffset(), playSource.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
