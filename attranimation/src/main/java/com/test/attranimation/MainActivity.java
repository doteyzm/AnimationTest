package com.test.attranimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnX;
    private Button btnY;
    private Button btnColor;
    private Button btnSet;
    private Button btnXml;
    private Button btnWidth;
    private Button btnH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnX = findViewById(R.id.btnX);
        btnY = findViewById(R.id.btnY);
        btnColor = findViewById(R.id.btnBackColor);
        btnSet = findViewById(R.id.btnSet);
        btnXml = findViewById(R.id.btnXml);
        btnWidth = findViewById(R.id.btnWidth);
        btnH = findViewById(R.id.btnH);
        //默认时间是300ms
        ObjectAnimator.ofFloat(btnX, "translationX", 200).setDuration(1000).start();
        ObjectAnimator.ofFloat(btnY, "translationY", -100).setDuration(1000).start();
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(btnColor, "backgroundColor", 0XFF3F51B5, 0XFFFF4081);//此处的颜色必须设置为16进制,否则无效
        colorAnim.setDuration(1000);
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);//设置动画重复的频率
        colorAnim.setEvaluator(new ArgbEvaluator());//设置动画的插值器
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
        /**
         * 动画集合
         */
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(btnSet, "translationX", 0, 90),
                ObjectAnimator.ofFloat(btnSet, "translationY", 0, 90),
                ObjectAnimator.ofFloat(btnSet, "rotationX", 0, 180),
                ObjectAnimator.ofFloat(btnSet, "rotationY", 0, 60),
                ObjectAnimator.ofFloat(btnSet, "scaleX", 1, 0.5f),
                ObjectAnimator.ofFloat(btnSet, "scaleY", 1, 2f),
                ObjectAnimator.ofFloat(btnSet, "alpha", 1, 0.5f),
                ObjectAnimator.ofInt(btnSet, "backgroundColor", 0XFF3F51B5, 0XFFFF4081)
        );
        set.setDuration(5000).start();
        /**
         * xml设置动画集合
         */
        AnimatorSet set1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.test);
        set1.setTarget(btnXml);
        set1.start();
        /**
         * 通过一个类来包装原始对象，间接提供get和set方法，实现自定义属性的动画
         */
        ViewWrapper wrapper = new ViewWrapper(btnWidth);
        ObjectAnimator.ofInt(wrapper, "width", 768).setDuration(3000).start();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator intEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前动画的进度值
                int currentValue = (int)animation.getAnimatedValue();
                //获取当前进度占整个动画的比例
                float fraction = animation.getAnimatedFraction();
                btnH.getLayoutParams().width = intEvaluator.evaluate(fraction,btnH.getWidth(),500);
                btnH.requestLayout();
            }
        });

        valueAnimator.setDuration(2000).start();


    }

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View mTarget) {
            this.mTarget = mTarget;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
