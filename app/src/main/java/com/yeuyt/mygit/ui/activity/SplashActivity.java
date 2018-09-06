package com.yeuyt.mygit.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.widgets.PathAnimatorListener;
import com.yeuyt.mygit.widgets.TextPathView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.root_view)
    ConstraintLayout rootView;
    @BindView(R.id.text_path)
    TextPathView text_path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
        /*text_path.startAnimation(0, 1, TextPathView.REVERSE, 0);
        text_path.setAnimatorListener(new PathAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                SystemClock.sleep(1000);
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        });*/

    }
}
