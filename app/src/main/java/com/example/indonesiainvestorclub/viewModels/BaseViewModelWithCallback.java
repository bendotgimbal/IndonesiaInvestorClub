package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.widget.Toast;
import com.example.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.example.indonesiainvestorclub.models.response.LoginRes;
import com.example.indonesiainvestorclub.models.response.LogoutRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public abstract class BaseViewModelWithCallback extends BaseViewModel {
  protected CompositeDisposable compositeDisposable = new CompositeDisposable();

  private int counterRetryRefreshToken = 0;

  public BaseViewModelWithCallback(Context context) {
    super(context);
  }

  public void refreshToken(Runnable function) {
    if (counterRetryRefreshToken < 3) {
      forceLogout(function);

      counterRetryRefreshToken++;
    } else {
      hideLoading();
      Toast.makeText(getContext(), "Can't access with default key", Toast.LENGTH_SHORT).show();
    }
  }

  private void forceLogin(Runnable function) {
    RequestBody username =
        RequestBody.create(MediaType.parse("text/plain"), SharedPreferenceHelper.getUserName());
    RequestBody password =
        RequestBody.create(MediaType.parse("text/plain"), SharedPreferenceHelper.getUserKey());

    Disposable disposable = ServiceGenerator.service.loginRequest(username, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<LoginRes>>(this, null) {
          @Override
          protected void onSuccess(Response<LoginRes> loginResponse) {
            if (loginResponse.body() != null) {
              hideLoading();
              SharedPreferenceHelper.setToken(loginResponse.body().getToken());
              function.run();
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void forceLogout(Runnable function) {
    Disposable disposable = ServiceGenerator.service.logoutRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<LogoutRes>>(this, null) {
          @Override
          protected void onSuccess(Response<LogoutRes> logoutResResponse) {
            if (logoutResResponse.body() != null) {
              if (logoutResResponse.body().getStatus()) {
                forceLogin(function);
              } else {
                Toast.makeText(getContext(), "EXPIRED SESSION", Toast.LENGTH_SHORT).show();
              }
            }
          }
        });
    compositeDisposable.add(disposable);
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

  public abstract void hideLoading();
}
