package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import androidx.databinding.ObservableField;
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
  public ObservableField<String> username;
  public ObservableField<String> password;

  public LoginViewModel(Context context, LoginActivityBinding binding) {
    super(context);
    this.binding = binding;
  }

  @SuppressWarnings("unused")
  public void onClickLogin(View view){
    Disposable disposable = ServiceGenerator.service.loginRequest(username.get(),username.get())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<Login>>(this, () -> onClickLogin(view)) {
          @Override protected void onSuccess(Response<Login> loginResponse) {

          }
        });
    compositeDisposable.add(disposable);
  }

}
