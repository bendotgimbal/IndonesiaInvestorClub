package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.viewModels.SimpleMessageDialogViewModel;

public abstract class BaseActivity extends AppCompatActivity {

  private Toast toast;
  private SimpleMessageDialogViewModel simpleMessageDialogViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDataBinding();
    simpleMessageDialogViewModel = new SimpleMessageDialogViewModel(this, null);
  }

  public abstract void initDataBinding();

  public void showToast(String message, int duration) {
    if (toast != null) {
      toast.cancel();
    }
    toast = Toast.makeText(this, message, duration);
    toast.show();
  }

  @SuppressWarnings("unused")
  public void showAlertDialog(@NonNull String title, @NonNull String message) {
    simpleMessageDialogViewModel.show(title, message);
  }

  public void showAlertDialog(@NonNull String title, @NonNull String message, @NonNull
      ActionInterface.CustomDialogActionButton listener){
    simpleMessageDialogViewModel.show(title, message, listener);
  }

}
