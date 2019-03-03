package com.cuishang.aircraftwar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(findViewById(R.id.lanuch_image),"scaleX", 0f,1.0f);

        objectAnimator.setDuration(2000);
        objectAnimator.start();

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
