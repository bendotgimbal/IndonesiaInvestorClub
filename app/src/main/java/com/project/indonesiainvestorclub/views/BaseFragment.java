package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

  private Toast toast;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initDataBinding();
  }

  protected abstract void initDataBinding();

  @SuppressWarnings("unused")
  public void showToast(String message) {
    showToast(message, Toast.LENGTH_SHORT);
  }

  protected void showToast(String message, int duration) {
    if (toast != null) {
      toast.cancel();
    }

    toast = Toast.makeText(getContext(), message, duration);
    toast.show();
  }
}
