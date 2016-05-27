package com.binge.present.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.binge.present.R;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/27.
 */
public class MusicServer extends Service {
    public MediaPlayer mp;
    private ActivityManager am;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    // 初始化音乐资源
    @Override
    public void onCreate() {
        mp = new MediaPlayer();
//        mp = MediaPlayer.create(MusicServer.this, R.raw.background_music);
        try {
            mp.prepare();
        } catch (IllegalStateException e1) {
        } catch (IOException e1) {
        }

        super.onCreate();

        am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(final MediaPlayer mp) {
                // 扫描任务栈顶端应用
                new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            String packageName = am.getRunningTasks(1).get(0).topActivity
                                    .getPackageName();
                            try{
                                if ("cn.com.dwsoft.ckx".equals(packageName)) {
                                    if(!mp.isPlaying()){
                                        mp.start();
                                    }
                                } else {
                                    if(mp.isPlaying()){
                                        mp.pause();
                                    }
                                }
                            }catch(IllegalStateException e){
                                //do nothing
                            }
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                            }
                            continue;
                        }

                    }
                }.start();
            }
        });
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        mp.start();
        mp.setLooping(true);

        // 播放时发生错误的事件
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 释放资源

                try {
                    mp.release();

                } catch (Exception e) {
                }
                return false;
            }
        });

        super.onStart(intent, startId);
    }

}
