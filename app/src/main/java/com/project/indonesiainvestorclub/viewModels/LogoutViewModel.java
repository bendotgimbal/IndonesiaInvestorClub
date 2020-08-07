package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import com.project.indonesiainvestorclub.databinding.LogoutActivityBinding;
import com.project.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.project.indonesiainvestorclub.models.response.LogoutRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.LogoutActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LogoutViewModel extends BaseViewModelWithCallback {

  private LogoutActivityBinding binding;

  public ObservableBoolean loadingState;

  public LogoutViewModel(Context context, LogoutActivityBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);

    logout();
  }

  private void loading(boolean load){
    loadingState.set(load);
  }

  //API CALL
  private void logout() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.logoutRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<LogoutRes>>(this, this::logout) {
          @Override
          protected void onSuccess(Response<LogoutRes> logoutResResponse) {
            if (logoutResResponse.body() != null){
              if (logoutResResponse.body().getStatus()){
                SharedPreferenceHelper.setLogin(false);
                SharedPreferenceHelper.setUserMarketing(false);
                SharedPreferenceHelper.setToken("");
                SharedPreferenceHelper.setUserKey("");
                SharedPreferenceHelper.setCookie("");
                SharedPreferenceHelper.setUserRealName("");
                SharedPreferenceHelper.setUserAva("");
                SharedPreferenceHelper.setUserName("");

                hideLoading();
                ((LogoutActivity)getContext()).finish();
              }
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
