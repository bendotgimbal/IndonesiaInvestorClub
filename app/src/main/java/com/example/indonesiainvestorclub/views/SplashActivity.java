package com.example.indonesiainvestorclub.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indonesiainvestorclub.R;

public class SplashActivity extends Activity {

  private int mProgressStatus = 0;
  private ProgressBar mProgressBar;
  private TextView mTextView;
  private int i = 0;
  private Handler hdlr = new Handler();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_activity);

    mTextView = findViewById(R.id.textView);
    mProgressBar = findViewById(R.id.progressBar);
    i = mProgressBar.getProgress();
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
        });
        try {
          // Sleep for 100 milliseconds to show the progress slowly.
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void NextProgressDialog() {
    mProgressStatus = 50;
    while (mProgressStatus < 100) {
      try {

        mProgressStatus++;

        /** Invokes the callback method onProgressUpdate */
        publishProgress(mProgressStatus);

        /** Sleeps this thread for 100ms */
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
