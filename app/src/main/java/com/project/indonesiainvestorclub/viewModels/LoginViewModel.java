package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.project.indonesiainvestorclub.databinding.LoginActivityBinding;
import com.project.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.project.indonesiainvestorclub.helper.StringHelper;
import com.project.indonesiainvestorclub.models.Groups;
import com.project.indonesiainvestorclub.models.response.LoginRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.LoginActivity;
import com.project.indonesiainvestorclub.views.SignUpActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
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
            String cookie = loginResponse.headers().get("Set-Cookie");
            StringHelper.getCookie(cookie);
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

      SharedPreferenceHelper.setUserRealName(loginRes.getFirstName()+""+loginRes.getLastName());
      SharedPreferenceHelper.setUserAva(loginRes.getAvatar());

      List<Groups> groups = new ArrayList<>();
      JSONObject jsonObject;
      try {
        jsonObject = new JSONObject(loginRes.getGroups().toString());

        for (int i = 1; i <= jsonObject.length(); i++) {
          Groups objGroup = new Groups();
          objGroup.setDepartment(jsonObject.getString(i + ""));

          groups.add(objGroup);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

      if (groups.size() > 0){
        if (groups.get(1).getDepartment() != null && groups.get(1).getDepartment().equalsIgnoreCase("MARKETING")){
          SharedPreferenceHelper.setUserMarketing(true);
        }
      }

      Toast.makeText(getContext(), "Selamat Anda Berhasil Masuk", Toast.LENGTH_SHORT).show();
      ((LoginActivity)context).finish();
    }
  }

  @SuppressWarnings("unused")
  public void onButtonSignUpClick(View view) {
    Intent intent = new Intent(context, SignUpActivity.class);
    context.startActivity(intent);
    ((LoginActivity)context).finish();
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
