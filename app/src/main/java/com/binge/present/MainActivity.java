package com.binge.present;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

import java.io.IOException;
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
    private String[] mPics;
    private String lastPic;
    private AlphaAnimation mAlphaAnimation;
    private AssetManager mAssetManager;

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
                    handler.sendEmptyMessage(4);
                    break;
                case 4:
                    try {
                        String pic = getPic();
                        Log.d(TAG, "handleMessage: " + pic);
                        mImageView.setImageBitmap(BitmapFactory.decodeStream(mAssetManager.open(pic)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getAnim().startAnimation(3000);
                    handler.sendEmptyMessageDelayed(5, 6000);
                    break;
                case 5:
                    mImageView.startAnimation(mAlphaAnimation);
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
        mAssetManager = getAssets();

        initAnim();
        initPic();
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
        }
        return getAnim();
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

        mAlphaAnimation = new AlphaAnimation(1f, 0f);
        mAlphaAnimation.setDuration(2000);
        mAlphaAnimation.setFillAfter(false);
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.sendEmptyMessage(4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initPic() {
        try {
            String[] pics = mAssetManager.list("pic");
            mPics = new String[pics.length];
            for (int i = 0; i < pics.length; i++) {
                mPics[i] = "pic/" + pics[i];
            }
        } catch (IOException e) {
            Log.d(TAG, "getPic: null");
            e.printStackTrace();
        }
    }

    private String getPic() {
        int i = mRandom.nextInt(mPics.length);
        if (!TextUtils.equals(lastPic, mPics[i])) {
            lastPic = mPics[i];
            Log.d(TAG, "getPic: " + i + " " + mPics[i]);
            return mPics[i];
        }

        return getPic();
    }
}
