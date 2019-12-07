package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import com.example.indonesiainvestorclub.databinding.LoginActivityBinding;
import com.example.indonesiainvestorclub.models.response.Login;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModelWithCallback{

  private LoginActivityBinding binding;

  public LoginViewModel(Context context, LoginActivityBinding binding) {
    super(context);
    this.binding = binding;
  }

  private void login(){
    Disposable disposable = ServiceGenerator.service.loginRequest("","")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<Login>>(this, this::login) {
          @Override protected void onSuccess(Response<Login> loginResponse) {

          }
        });
    compositeDisposable.add(disposable);
  }

}
