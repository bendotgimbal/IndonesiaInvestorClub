package com.project.indonesiainvestorclub.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.project.indonesiainvestorclub.R;

public class SplashActivity extends Activity {

  private ProgressBar mProgressBar;
  private int i = 0;
  private Handler handler = new Handler();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_activity);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.parseColor("#FFFFFF"));
    }

    ImageView mImg = findViewById(R.id.imageView);
    mProgressBar = findViewById(R.id.progressBar);
    i = mProgressBar.getProgress();

    Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
    mImg.startAnimation(animFadeIn);
    ProgressDialog();
  }

  private void ProgressDialog() {
    i = mProgressBar.getProgress();
    new Thread(() -> {
      while (i < 100) {
        handler.post(() -> {
          i += 1;
          mProgressBar.setProgress(i);
          Log.w("Debug", "Loading " + i + " %");
          if (i == 100) {
            doneProgress();
          }
        });
        try {
          Thread.sleep(20);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void doneProgress() {
    Intent i = new Intent(this, MainActivity.class);
    startActivity(i);
    finish();
  }
}
