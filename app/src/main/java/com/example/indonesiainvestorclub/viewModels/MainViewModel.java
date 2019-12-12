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

  private static final String TAG = MainViewModel.class.getCanonicalName();
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
            if (response.body() != null){
              JSONObject jsonObject;
              try {
                PerformanceRes performanceRes = null;

                jsonObject = new JSONObject(response.body().toString());
                JSONObject object = jsonObject.getJSONObject("Performances");
                List<Performance> performances = new ArrayList<>();
                for (int i = 1; i <= object.length(); i++){
                  JSONObject obj = object.getJSONObject(i+"");
                  Performance performance = null;

                  String name = obj.getString("Name");
                  JSONObject datas = obj.getJSONObject("Datas");

                  List<Datas> datasList = new ArrayList<>();
                  for (int o = 1; o <= datas.length(); o++){
                    JSONObject obj1 = datas.getJSONObject(i+"");

                    Datas datasL = null;
                    List<Month> monthList = new ArrayList<>();
                    for (int p = 1; p <= obj1.length(); p++){
                      JSONObject obj2 = obj1.getJSONObject(p+"");

                      Month month = new Month(
                          obj2.getJSONObject("YEAR").toString(),
                          obj2.getJSONObject("Jan").toString(),
                          obj2.getJSONObject("Feb").toString(),
                          obj2.getJSONObject("Mar").toString(),
                          obj2.getJSONObject("Apr").toString(),
                          obj2.getJSONObject("May").toString(),
                          obj2.getJSONObject("Jun").toString(),
                          obj2.getJSONObject("Jul").toString(),
                          obj2.getJSONObject("Aug").toString(),
                          obj2.getJSONObject("Sep").toString(),
                          obj2.getJSONObject("Oct").toString(),
                          obj2.getJSONObject("Nov").toString(),
                          obj2.getJSONObject("Dec").toString(),
                          obj2.getJSONObject("YTD").toString()
                      );

                      monthList.add(month);
                    }
                    datasL.setMonths(monthList);
                    datasList.add(datasL);
                  }
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          }
        });
    compositeDisposable.add(disposable);
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
      }
      return true;
    });
  }

  //  private void initNavMenu() {
  //    binding.navView.setNavigationItemSelectedListener(menuItem -> {
  //      int id = menuItem.getItemId();
  //      Fragment fragment = null;
  //      switch (id) {
  //        case R.id.nav_home:
  //          goToMenu(PerformanceActivity.class);
  //          break;
  //        case R.id.nav_login:
  //          goToMenu(LoginActivity.class);
  //          break;
  //        case R.id.nav_profile:
  //          goToMenu(ProfileActivity.class);
  //          break;
  //        case R.id.menu_network:
  ////          goToMenu(AgreementActivity.class);
  //          fragment = new NetworkFragment();
  //          break;
  //      }
  //      return true;
  //    });
  //  }

  private void goToMenu(Class activity) {
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

  public boolean isDrawerOpen() {
    return binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }
}
