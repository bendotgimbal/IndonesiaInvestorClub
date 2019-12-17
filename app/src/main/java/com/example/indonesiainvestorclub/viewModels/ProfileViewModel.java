package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import com.example.indonesiainvestorclub.databinding.ProfileActivityBinding;
import com.example.indonesiainvestorclub.models.response.ProfileRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModelWithCallback {

  private ProfileActivityBinding binding;

  public ProfileViewModel(Context context, ProfileActivityBinding binding) {
    super(context);
    this.binding = binding;
  }

  private void getProfile() {
    Disposable disposable = ServiceGenerator.service.profileRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<ProfileRes>>(this, this::getProfile) {
          @Override
          protected void onSuccess(Response<ProfileRes> profileResResponse) {

          }
        });
    compositeDisposable.add(disposable);
  }

  @Override public void hideLoading() {

  }
}
