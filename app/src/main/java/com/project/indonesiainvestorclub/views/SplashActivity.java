package com.project.indonesiainvestorclub.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.indonesiainvestorclub.R;

public class SplashActivity extends Activity {

  private int mProgressStatus = 0;
  private ProgressBar mProgressBar;
  private TextView mTextView;
  private ImageView mImg;
  private int i = 0;
  private Handler hdlr = new Handler();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_activity);

    mImg = (ImageView)findViewById(R.id.imageView);
    mTextView = findViewById(R.id.textView);
    mProgressBar = findViewById(R.id.progressBar);
    i = mProgressBar.getProgress();

    Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
    mImg.startAnimation(animFadeIn);
    ProgressDialog();
  }

  private void ProgressDialog() {
    i = mProgressBar.getProgress();
    new Thread(() -> {
      while (i < 100) {
        i += 1;
        // Update the progress bar and display the current value in text view
        hdlr.post(() -> {
          mProgressBar.setProgress(i);
          // mTextView.setText(i+"/"+mProgressBar.getMax());
          mTextView.setText(i + " %");
          Log.w("Debug", "Loading " + i + " %");

          // if (i == 50){
          //    NextProgressDialog();
          // }

          if (i == 100){
            doneProgress();
          }
        });
        try {
          // Sleep for 100 milliseconds to show the progress slowly.
          Thread.sleep(20);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void doneProgress(){
    Intent i = new Intent(this, MainActivity.class);
    startActivity(i);
    finish();
  }

  private void NextProgressDialog() {
    mProgressStatus = 50;
    while (mProgressStatus < 100) {
      try {

        mProgressStatus++;

        //Invokes the callback method onProgressUpdate
        publishProgress(mProgressStatus);

        //Sleeps this thread for 100ms
        Thread.sleep(100);
      } catch (Exception e) {
        Log.d("Exception", e.toString());
      }
    }
  }

  private void publishProgress(Integer... progress) {
    mTextView.setText("" + progress[0] + " %");
    Log.w("Debug", "Loading Connection Server " + progress[0] + " %");

    if (progress[0] == 80) {
      Toast.makeText(SplashActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    mProgressBar.setProgress(progress[0]);
  }
}
