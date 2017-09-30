package com.binge.present;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binge.present.anim.Anim;
import com.binge.present.anim.AnimBaiYeChuang;
import com.binge.present.anim.AnimCaChu;
import com.binge.present.anim.AnimHeZhuang;
import com.binge.present.anim.AnimJieTi;
import com.binge.present.anim.AnimLingXing;
import com.binge.present.anim.AnimLunZi;
import com.binge.present.anim.AnimPiLie;
import com.binge.present.anim.AnimQiPan;
import com.binge.present.anim.AnimQieRu;
import com.binge.present.anim.AnimShanXingZhanKai;
import com.binge.present.anim.AnimShiZiXingKuoZhan;
import com.binge.present.anim.AnimSuiJiXianTiao;
import com.binge.present.anim.AnimXiangNeiRongJie;
import com.binge.present.anim.AnimYuanXingKuoZhan;
import com.binge.present.service.MusicPlayService;
import com.binge.present.util.CommonUtil;
import com.binge.present.widget.EnterAnimLayout;
import com.hanks.htextview.HTextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/25.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.intro_title)
    HTextView mIntroTitle;

    @BindView(R.id.days_tv)
    HTextView mDaysTV;

    @BindView(R.id.enter_layout)
    EnterAnimLayout mEnterAnimLayout;

    @BindView(R.id.image_view)
    ImageView mImageView;

    private String days;
    private Anim[] mAnims;
    private Random mRandom = new Random();
    private Anim lastAnim;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    days = CommonUtil.getDays() + "天";
                    mIntroTitle.animateText(getResources().getText(R.string.intro_title));
                    handler.sendEmptyMessageDelayed(1, 1500);
                    break;
                case 1:
                    playBoardAnim(mDaysTV);
                    mDaysTV.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(2, 1000);
                    break;
                case 2:
                    mDaysTV.animateText(days);
                    handler.sendEmptyMessageDelayed(3, 1500);
                    break;
                case 3:
                    mIntroTitle.setVisibility(View.GONE);
                    mDaysTV.setVisibility(View.GONE);
                    handler.sendEmptyMessage(6);
                    break;
                case 4:
                    getAnim().startAnimation(3000);
                    handler.sendEmptyMessageDelayed(5, 6000);
                    break;
                case 5:
                    //替换成空图在执行一次

//                    lastAnim.startAnimation(2000);
                    handler.sendEmptyMessageDelayed(6, 2000);
                    break;
                case 6:
                    mImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.day_bg2));
                    handler.sendEmptyMessage(4);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        startService(new Intent(this, MusicPlayService.class));

        initAnim();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        handler.obtainMessage(0).sendToTarget();

    }

    private Anim getAnim() {
        int i = mRandom.nextInt(mAnims.length);
        Anim anim = mAnims[i];
        if (lastAnim != anim) {
            lastAnim = anim;
            return anim;
        } else {
            getAnim();
        }
        return anim;
    }

    private void playBoardAnim(View target) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ObjectAnimator.ofFloat(target, "scaleX", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "translationY", -target.getBottom(), 60, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1));
        animSet.setDuration(1000).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicPlayService.class));
    }

    private void initAnim() {
        mAnims = new Anim[]{
                new AnimBaiYeChuang(mEnterAnimLayout),
                new AnimCaChu(mEnterAnimLayout),
                new AnimHeZhuang(mEnterAnimLayout),
                new AnimJieTi(mEnterAnimLayout),
                new AnimLingXing(mEnterAnimLayout),
                new AnimLunZi(mEnterAnimLayout),
                new AnimPiLie(mEnterAnimLayout),
                new AnimQieRu(mEnterAnimLayout),
                new AnimQiPan(mEnterAnimLayout),
                new AnimShanXingZhanKai(mEnterAnimLayout),
                new AnimShiZiXingKuoZhan(mEnterAnimLayout),
                new AnimSuiJiXianTiao(mEnterAnimLayout),
                new AnimXiangNeiRongJie(mEnterAnimLayout),
                new AnimYuanXingKuoZhan(mEnterAnimLayout)
        };
    }
}
