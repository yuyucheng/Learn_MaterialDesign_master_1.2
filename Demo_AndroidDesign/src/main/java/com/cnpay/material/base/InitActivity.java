package com.cnpay.material.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.cnpay.material.R;
import com.cnpay.material.activity.MainActivity;
import com.cnpay.material.utils.ActivityUtils;
import com.cnpay.material.utils.HandlerUtils;

/**
 * 包   名:     com.cnpay.material.base
 * 类   名:     InitActivity
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/1 0001 15:00
 * 作   者:     yuyucheng
 */
public class InitActivity extends Activity {

    private ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        iv_logo = (ImageView) findViewById(R.id.init_iv_logo);

        HandlerUtils.postDelayed(runnable, 1000);
        //animationStart();
    }

    /**延时操作*/
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (ActivityUtils.isAlive(InitActivity.this)) {
                startActivity(new Intent(InitActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    /**
     * 通过动画延时
     */
    private void animationStart() {
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        iv_logo.startAnimation(anima);
        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                /** 执行一些初始化工作*/
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //开始后
                Intent intent = new Intent();
                intent.setClass(InitActivity.this, MainActivity.class);
                InitActivity.this.startActivity(intent);
                InitActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
