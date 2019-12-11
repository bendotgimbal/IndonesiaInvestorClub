package com.example.indonesiainvestorclub.services;

import com.example.indonesiainvestorclub.viewModels.BaseViewModelWithCallback;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public abstract class CallbackWrapper<T extends Response> extends DisposableObserver<T> {

  private BaseViewModelWithCallback viewModel;
  private Runnable function;

  public CallbackWrapper(BaseViewModelWithCallback viewModel, Runnable function) {
    this.viewModel = viewModel;
    this.function = function;
  }

  protected abstract void onSuccess(T t);

  @Override public void onNext(T t) {
    if (t.isSuccessful()) {
      onSuccess(t);
    }
  }

  @Override public void onError(Throwable e) {

  }

  @Override public void onComplete() {

  }
}
