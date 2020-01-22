package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.project.indonesiainvestorclub.databinding.SignupActivityBinding;

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

  @SuppressWarnings("unused")
  public void onClickSignUp(View view) {
    loading(true);

  }

  @Override public void hideLoading() {
    loading(false);
  }
}
