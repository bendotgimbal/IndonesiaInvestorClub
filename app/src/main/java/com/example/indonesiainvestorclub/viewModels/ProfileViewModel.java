package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;

import com.example.indonesiainvestorclub.databinding.ProfileFragmentBinding;
import com.example.indonesiainvestorclub.models.response.ProfileRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModelWithCallback {

  private ProfileFragmentBinding binding;
    public ObservableBoolean loadingState;

  public ProfileViewModel(Context context, ProfileFragmentBinding binding) {
    super(context);
    this.binding = binding;

      loadingState = new ObservableBoolean(false);

      start();
  }

    private void start() {
        getProfile();
    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

  private void getProfile() {
      loading(true);

//    Disposable disposable = ServiceGenerator.service.profileRequest()
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeWith(new CallbackWrapper<Response<ProfileRes>>(this, this::getProfile) {
//          @Override
//          protected void onSuccess(Response<ProfileRes> profileResResponse) {
//
//          }
//        });
//    compositeDisposable.add(disposable);

      Disposable disposable = ServiceGenerator.service.profileRequest()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getProfile) {
                  @Override
                  protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                      if (jsonElementResponse.body() != null) {
                          loading(false);
                          readProfileJSON(jsonElementResponse.body());
                      }
                  }
              });
      compositeDisposable.add(disposable);
  }

    private void readProfileJSON(JsonElement response){
        JSONObject jsonObject;
        try {
            ProfileRes profileRes = new ProfileRes();

            jsonObject = new JSONObject(response.toString());
            JSONObject objectLogin = jsonObject.getJSONObject("Login");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading() {
        loading(false);
    }
}
