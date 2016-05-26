package com.binge.present;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.binge.present.util.CommonUtil;
import com.hanks.htextview.HTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/25.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.intro_title)
    HTextView introTitle;

    @BindView(R.id.days_tv)
    HTextView daysTV;

    private String days;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    days = CommonUtil.getDays() + "天";
                    introTitle.animateText(getResources().getText(R.string.intro_title));
                    handler.sendEmptyMessageDelayed(1, 1500);
                    break;
                case 1:
                    playBoardAnim(daysTV);
                    daysTV.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(2, 1000);
                    break;
                case 2:
                    daysTV.animateText(days);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        handler.obtainMessage(0).sendToTarget();
    }

    private void playBoardAnim(View target) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ObjectAnimator.ofFloat(target, "scaleX", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "translationY", -target.getBottom(), 60, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1));
        animSet.setDuration(1000).start();
    }
}
