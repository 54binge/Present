package com.binge.present.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/27.
 */
public class AudioMgr {
    private HashMap<String, Integer> soundMap = new HashMap<>();
    private static final AudioMgr AUDIO_MGR = new AudioMgr();
    private SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

    public static AudioMgr getInstance() {
        return AUDIO_MGR;
    }

    public void init(Context context) {
        soundMap.put("audio1", soundPool.load(context, 1, 1));
        soundMap.put("audio1", soundPool.load(context, 1, 1));
        soundMap.put("audio1", soundPool.load(context, 1, 1));
    }

    public void play(String tag) {
        soundPool.play(soundMap.get(tag), 10, 10, 0, 0, 1);
    }
}
