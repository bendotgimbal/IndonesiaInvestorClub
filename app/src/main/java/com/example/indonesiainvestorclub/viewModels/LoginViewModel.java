package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.example.indonesiainvestorclub.databinding.LoginActivityBinding;
import com.example.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.example.indonesiainvestorclub.models.response.LoginRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.example.indonesiainvestorclub.views.LoginActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModelWithCallback{

  private LoginActivityBinding binding;
  public ObservableField<String> username;
  public ObservableField<String> password;
  public ObservableBoolean loadingState;

  public LoginViewModel(Context context, LoginActivityBinding binding) {
    super(context);
    this.binding = binding;
    username = new ObservableField<>("");
    password = new ObservableField<>("");
    loadingState = new ObservableBoolean(false);
  }

  private void loading(boolean load){
    loadingState.set(load);
  }

  private String getUsername(){
    if (username.get() == null) return "";
    return username.get();
  }

  private String getPassword(){
    if (password.get() == null) return "";
    return password.get();
  }

  @SuppressWarnings("unused")
  public void onClickLogin(View view){
    loading(true);

    RequestBody username = RequestBody.create(MediaType.parse("text/plain"), getUsername());
    RequestBody password = RequestBody.create(MediaType.parse("text/plain"), getPassword());

    Disposable disposable = ServiceGenerator.service.loginRequest(username, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<LoginRes>>(this, () -> onClickLogin(view)) {
          @Override
          protected void onSuccess(Response<LoginRes> loginResponse) {
            onSuccessLogin(loginResponse.body());
          }

          @Override public void onNext(Response<LoginRes> loginResResponse) {
            super.onNext(loginResResponse);
            loading(false);
          }
        });
    compositeDisposable.add(disposable);
  }

  private void onSuccessLogin(LoginRes loginRes){
    if (loginRes != null){
      SharedPreferenceHelper.setLogin(true);
      SharedPreferenceHelper.setToken(loginRes.getToken());
      SharedPreferenceHelper.setUserName(loginRes.getUsername());

      //TODO encode before saving to shared preference
      SharedPreferenceHelper.setUserKey(getPassword());

      Toast.makeText(getContext(), "Selamat Anda Berhasil Masuk", Toast.LENGTH_SHORT).show();
      ((LoginActivity)context).finish();
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
