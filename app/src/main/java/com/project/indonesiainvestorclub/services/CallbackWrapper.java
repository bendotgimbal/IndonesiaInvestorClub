package com.project.indonesiainvestorclub.services;

import android.util.Log;
import com.project.indonesiainvestorclub.viewModels.BaseViewModelWithCallback;
import io.reactivex.observers.DisposableObserver;
import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class CallbackWrapper<T extends Response> extends DisposableObserver<T> {

  private static final String TAG = CallbackWrapper.class.getCanonicalName();

  private BaseViewModelWithCallback viewModel;
  private Runnable function;

  private static final int ERROR_AUTH = 401;

  public CallbackWrapper(BaseViewModelWithCallback viewModel, Runnable function) {
    this.viewModel = viewModel;
    this.function = function;
  }

  protected abstract void onSuccess(T t);

  @Override public void onNext(T t) {
    if (t.isSuccessful()) {
      onSuccess(t);
    } else {
      if (t.code() == ERROR_AUTH){
        viewModel.refreshToken(function);
      }
    }
  }

  @Override public void onError(Throwable e) {
    Log.e(TAG, "onError: " + e.getLocalizedMessage() + " cause : " + e.getCause());
    if (e instanceof HttpException) {
      Response<?> body = ((HttpException) e).response();
      Log.e(TAG, "HttpException");
    } else if (e instanceof SocketTimeoutException) {
      Log.e(TAG, "SocketTimeoutException");
    } else if (e instanceof IOException) {
      Log.e(TAG, "IOException");
    } else {
      Log.e(TAG, "Else");
    }
  }

  @Override public void onComplete() {

  }
}
