package cn.hylin.edu.szu.mynewsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import cn.hylin.edu.szu.mynewsapp.R;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        initAnimation();
    }

    /**
     * 初始化动画
     * 动画结束后启动主Activity
     */
    private void initAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        Animation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        animationSet.setFillAfter(true);

        Animation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setFillAfter(true);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        rootLayout.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
