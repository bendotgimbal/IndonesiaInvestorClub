package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.project.indonesiainvestorclub.databinding.SignupActivityBinding;
import com.project.indonesiainvestorclub.helper.StringHelper;
import com.project.indonesiainvestorclub.models.response.SignUpRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class SignUpViewModel extends BaseViewModelWithCallback {

  private SignupActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> mReferralCode;
  public ObservableField<String> mFirstName;
  public ObservableField<String> mLastName;
  public ObservableField<String> mMobilePhone;
  public ObservableField<String> mEmail;
  public ObservableField<String> mPassword;
  public ObservableField<String> mRetypePassword;

  public SignUpViewModel(Context context, SignupActivityBinding binding) {
    super(context);
    this.binding = binding;
    loadingState = new ObservableBoolean(false);
    mReferralCode = new ObservableField<>("");
    mFirstName = new ObservableField<>("");
    mLastName = new ObservableField<>("");
    mMobilePhone = new ObservableField<>("");
    mEmail = new ObservableField<>("");
    mPassword = new ObservableField<>("");
    mRetypePassword = new ObservableField<>("");
  }

  private void loading(boolean load){
    loadingState.set(load);
  }

  private String getReferralCode(){
    if (mReferralCode.get() == null) return "";
    return mReferralCode.get();
  }

  private String getFirstName(){
    if (mFirstName.get() == null) return "";
    return mFirstName.get();
  }

  private String getLastName(){
    if (mLastName.get() == null) return "";
    return mLastName.get();
  }

  private String getMobilePhone(){
    if (mMobilePhone.get() == null) return "";
    return mMobilePhone.get();
  }

  private String getEmail(){
    if (mEmail.get() == null) return "";
    return mEmail.get();
  }

  private String getPassword(){
    if (mPassword.get() == null) return "";
    return mPassword.get();
  }

  @SuppressWarnings("unused")
  public void onClickSignUp(View view) {
    loading(true);

    RequestBody referral = RequestBody.create(MediaType.parse("text/plain"), getReferralCode());
    RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), getFirstName());
    RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), getLastName());
    RequestBody phone_no = RequestBody.create(MediaType.parse("text/plain"), getMobilePhone());
    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), getEmail());
    RequestBody password = RequestBody.create(MediaType.parse("text/plain"), getPassword());

    Disposable disposable = ServiceGenerator.service.signUpRequest(referral, first_name, last_name, phone_no, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<SignUpRes>>(this, () -> onClickSignUp(view)) {
              @Override
              protected void onSuccess(Response<SignUpRes> signupResponse) {
                onSuccessRegister(signupResponse.body());
                String cookie = signupResponse.headers().get("Set-Cookie");
                StringHelper.getCookie(cookie);
              }

              @Override public void onNext(Response<SignUpRes> signupResResponse) {
                super.onNext(signupResResponse);
                loading(false);
              }
            });
    compositeDisposable.add(disposable);

  }

  private void onSuccessRegister(SignUpRes signupRes){
    if (signupRes != null) {

      Toast.makeText(getContext(), "Selamat Anda Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
    }

  }

  @Override public void hideLoading() {
    loading(false);
  }
}
