package com.binge.present.anim;

import android.graphics.Canvas;

import com.binge.present.widget.EnterAnimLayout;

/**
 * Created by wpm on 2017/3/30.
 */

public class AnimQieRu extends Anim {
    public AnimQieRu(EnterAnimLayout view) {
        super(view);
    }

    @Override
    public void handleCanvas(Canvas canvas, float rate) {

        canvas.translate(0, h - h * rate);

        canvas.save();
    }
}
