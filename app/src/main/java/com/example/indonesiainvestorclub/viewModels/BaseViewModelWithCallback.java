package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModelWithCallback extends BaseViewModel {
  protected CompositeDisposable compositeDisposable = new CompositeDisposable();

  public BaseViewModelWithCallback(Context context) {
    super(context);
  }

  @Override
  public void destroySelf() {
    super.destroySelf();

    clearDisposable();
  }

  public void disposeDisposable() {
    if (compositeDisposable == null) return;
    compositeDisposable.clear();
  }

  private void clearDisposable() {
    if (compositeDisposable != null) {
      compositeDisposable.clear();
      compositeDisposable.dispose();
      compositeDisposable = null;
    }
  }
}
