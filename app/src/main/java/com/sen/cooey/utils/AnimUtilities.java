package com.sen.cooey.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class AnimUtilities {

    //synchronized the method, to make it run one by one
    public synchronized static void setSlideInAnimation(Context context, View view, int duration) {
        Animation animationFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animationFadeIn.setDuration(duration);
        view.startAnimation(animationFadeIn);
    }


}
