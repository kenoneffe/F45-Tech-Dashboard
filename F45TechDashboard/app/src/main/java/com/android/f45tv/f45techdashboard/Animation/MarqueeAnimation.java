package com.android.f45tv.f45techdashboard.Animation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;


public class MarqueeAnimation extends FrameLayout{

    private Animation anim;
    private Context context;

    public MarqueeAnimation(@NonNull Context context) {
        super(context);

    }


    private void setAniamation(Context context)
    {
        this.context = context;
        anim = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, +1f,
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, +0f,
                Animation.RELATIVE_TO_SELF, -0f
        );

        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.RESTART);
        anim.setDuration(Animation.INFINITE);
    }


    private Animation getAnim()
    {
        return this.anim;
    }

    public void startAnim()
    {
        startAnimation(getAnim());
    }

}
