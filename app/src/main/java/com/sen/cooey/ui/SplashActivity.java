package com.sen.cooey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sen.cooey.R;
import com.sen.cooey.storage.SessionManager;
import com.sen.cooey.utils.AnimUtilities;

public class SplashActivity extends AppCompatActivity {


    /**
     * a class to show splash screen and decide next activity
     */

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        TextView textViewSplash = findViewById(R.id.textViewSplash);
        AnimUtilities.setSlideInAnimation(this, textViewSplash, SPLASH_SCREEN_TIME_OUT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                decideActivity();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }//eof onCreate

    private void decideActivity() {
        Class<?> targetClass = RegisterActivity.class;

        SessionManager smsp = SessionManager.getInstance(SplashActivity.this);
        if (smsp.getLOGGED_IN() != -1)
            targetClass = MainActivity.class;

        //Intent - used to switch from one activity to another.
        Intent i = new Intent(SplashActivity.this, targetClass);
        startActivity(i);
        //the current activity will get finished.
        finish();

    }//eof decideActivity

}//eof SplashActivity
