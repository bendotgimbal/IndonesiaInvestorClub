package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.example.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Month;
import com.example.indonesiainvestorclub.models.Performance;
import com.example.indonesiainvestorclub.models.response.PerformanceRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.example.indonesiainvestorclub.views.LoginActivity;
import com.google.gson.JsonElement;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

public class MainViewModel extends BaseViewModelWithCallback {

  private static final String TAG = MainViewModel.class.getCanonicalName();

  private ActivityMainBinding binding;
  private NavHeaderMainBinding navHeaderMainBinding;
  public ObservableBoolean loginState;
  public ObservableBoolean loadingState;
  public ObservableField<String> email;

  public MainViewModel(Context context, ActivityMainBinding binding,
      NavHeaderMainBinding navHeaderMainBinding) {
    super(context);
    this.binding = binding;
    this.navHeaderMainBinding = navHeaderMainBinding;

    loginState = new ObservableBoolean(false);
    loadingState = new ObservableBoolean(false);
    email = new ObservableField<>("");
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  public void start() {
    if (SharedPreferenceHelper.getLoginState()) {
      loginState.set(true);
      email.set(SharedPreferenceHelper.getUserName());
      menuVisible(true);
    } else {
      loginState.set(false);
      email.set("");
      menuVisible(false);
    }

    getPerformance();
  }

  public void menuVisible(boolean visible) {
    binding.navView.getMenu().findItem(R.id.nav_profile).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_lounge).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_agreement).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_network).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_transaction).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_funds).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_logout).setVisible(visible);
  }

  public void loginScreen(View view) {
    Intent i = new Intent(context, LoginActivity.class);
    context.startActivity(i);
  }

  //API CALL
  private void getPerformance() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.performanceRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getPerformance) {
          @Override
          protected void onSuccess(Response<JsonElement> response) {
            if (response.body() != null) {
              readPerformancesJSON(response.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readPerformancesJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      PerformanceRes performanceRes = new PerformanceRes();

      jsonObject = new JSONObject(response.toString());
      JSONObject object = jsonObject.getJSONObject("Performances");

      List<Performance> performances = new ArrayList<>();
      List<Datas> datasList = new ArrayList<>();
      List<Month> monthList = new ArrayList<>();

      for (int i = 1; i <= object.length(); i++) {
        JSONObject obj = object.getJSONObject(i + "");
        Performance performance;
        Datas data;

        String name = obj.getString("Name");
        JSONObject datas = obj.getJSONObject("Datas");

        for (int o = 1; o <= datas.length(); o++) {
          JSONObject obj1 = datas.getJSONObject(o + "");

          Month month = new Month(
              obj1.getString("YEAR"),
              obj1.getString("Jan"),
              obj1.getString("Feb"),
              obj1.getString("Mar"),
              obj1.getString("Apr"),
              obj1.getString("May"),
              obj1.getString("Jun"),
              obj1.getString("Jul"),
              obj1.getString("Aug"),
              obj1.getString("Sep"),
              obj1.getString("Oct"),
              obj1.getString("Nov"),
              obj1.getString("Dec"),
              obj1.getString("YTD")
          );
          monthList.add(month);
        }

        data = new Datas(monthList);
        datasList.add(data);

        performance = new Performance(name, datasList);
        performances.add(performance);
      }

      performanceRes.setPerformances(performances);

      hideLoading();
    } catch (JSONException e) {
      Log.e(TAG, e.toString());
      hideLoading();
    }
  }

  @SuppressWarnings("unused")
  public void openDrawer() {
    binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }

  public void closeDrawer() {
    binding.drawerLayout.closeDrawer(GravityCompat.START, true);
  }

  public boolean isDrawerOpen() {
    return binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
