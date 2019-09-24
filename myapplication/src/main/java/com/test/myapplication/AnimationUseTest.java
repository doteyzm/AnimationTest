package com.test.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;

public class AnimationUseTest extends AppCompatActivity {
    Button viewBtn;
    Button roateBtn;
    Button frameBtn;
    LinearLayout linearLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_local_test);
        /**
         * View动画
         */
        viewBtn = (Button) findViewById(R.id.test1);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.test);
        viewBtn.startAnimation(animation);

        /**
         * 自定义View动画
         */
        roateBtn = (Button) findViewById(R.id.test2);
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 360, 200, 200, 0, true);
        rotate3dAnimation.setDuration(30 * 1000);
        roateBtn.startAnimation(rotate3dAnimation);
        /**
         * 帧动画
         */
        frameBtn = (Button) findViewById(R.id.test3);
        frameBtn.setBackgroundResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) frameBtn.getBackground();
        animationDrawable.start();
        /**
         * LayoutAnimation动画，可以设置ViewGroup里面子元素的动画效果
         * 代码设置和xml文件设置
         */
        linearLayout = findViewById(R.id.layout);
        //在代码中设置
        Animation layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.alaph_anim);
        LayoutAnimationController controller = new LayoutAnimationController(layoutAnimation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(0.5f);//表示子元素开始动画的时间延迟，比如子元素入场动画时间周期为300ms那么0.5表示每个子元素需要延迟150s才能播放入场动画
        linearLayout.setLayoutAnimation(controller);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AnimationUseTest.this, SecondActivity.class);
                startActivity(intent);
                //此方法只能用于startActivity和finish之后，否则不起作用
                overridePendingTransition(R.anim.alaph_anim, R.anim.test);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        viewBtn.clearAnimation();
        roateBtn.clearAnimation();
        linearLayout.clearAnimation();
        if (animationDrawable!=null)
            animationDrawable.stop();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alaph_anim, R.anim.test);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
