package com.binge.present.anim;

import android.graphics.Canvas;

import com.binge.present.widget.EnterAnimLayout;


/**
 * Created by wpm on 2017/3/30.
 */
public abstract class Anim {
    protected EnterAnimLayout view;
    protected float w;
    protected float h;
    public float totalPaintTime;//控制绘制时间

    public Anim(EnterAnimLayout view) {
        this(view, 2000);
    }

    public Anim(EnterAnimLayout view, float totalPaintTime) {
        this.totalPaintTime = totalPaintTime;
        this.view = view;
    }

    public void setTarget(){
        this.view.setAnim(this);
        w = view.getWidth();
        h = view.getHeight();
    }

    public float getH() {
        return h;
    }

    public float getW() {
        return w;
    }

    public void startAnimation() {
        setTarget();

        view.setAnimationRun(true);
        view.setStartTime(System.currentTimeMillis());

        view.invalidate();
    }

    public void startAnimation(long animTime) {
        totalPaintTime = animTime;
        startAnimation();
    }

    public abstract void handleCanvas(Canvas canvas, float rate);
}
