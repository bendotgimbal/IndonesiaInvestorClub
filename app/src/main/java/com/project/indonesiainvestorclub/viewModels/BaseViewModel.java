package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

public class BaseViewModel {

  protected Context context;

  public BaseViewModel(Context context) {
    this.context = context;
  }

  public Context getContext() {
    return context;
  }

  public void destroySelf() {
    context = null;
  }
}
