package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Month;
import com.example.indonesiainvestorclub.models.Performance;
import com.example.indonesiainvestorclub.models.response.PerformanceRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.example.indonesiainvestorclub.views.InvestFragment;
import com.example.indonesiainvestorclub.views.LoginActivity;
import com.example.indonesiainvestorclub.views.LogoutFragment;
import com.example.indonesiainvestorclub.views.NetworkFragment;
import com.example.indonesiainvestorclub.views.PerformanceActivity;
import com.example.indonesiainvestorclub.views.TransactionsFragment;
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

  private ActivityMainBinding binding;

  public MainViewModel(Context context, ActivityMainBinding binding) {
    super(context);
    this.binding = binding;
    initNavMenu();
    getPerformance();
  }

  //API CALL
  private void getPerformance() {
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

  private void readPerformancesJSON(JsonElement response){
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
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  //NAVIGATION LINE
  private void initNavMenu() {
    binding.navView.setNavigationItemSelectedListener(menuItem -> {
      int id = menuItem.getItemId();
      Fragment fragment = null;
      switch (id) {
        case R.id.nav_home:
          goToMenu(PerformanceActivity.class);
          Toast.makeText(context, "PerformanceActivity is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_network:
          fragment = new NetworkFragment();
          Toast.makeText(context, "NetworkFragment is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_transactions:
          fragment = new TransactionsFragment();
          Toast.makeText(context, "TransactionsFragment is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_invest:
          fragment = new InvestFragment();
          Toast.makeText(context, "InvestmentFragment is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_login:
          goToMenu(LoginActivity.class);
          Toast.makeText(context, "LoginActivity is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_logout:
          fragment = new LogoutFragment();
          Toast.makeText(context, "LogoutFragment is clicked", Toast.LENGTH_SHORT).show();
          break;
      }
      return true;
    });
  }

  private void goToMenu(Class activity){
    closeDrawer();
    context.startActivity(new Intent(context, activity));
  }

  @SuppressWarnings("unused")
  public void openDrawer() {
    binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }

  public void closeDrawer() {
    binding.drawerLayout.closeDrawer(GravityCompat.START, true);
  }

  public boolean isDrawerOpen(){
    return binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }
}
