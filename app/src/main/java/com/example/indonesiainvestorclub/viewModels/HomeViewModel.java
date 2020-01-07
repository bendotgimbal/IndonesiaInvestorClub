package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.indonesiainvestorclub.adapter.PerformanceAdapter;
import com.example.indonesiainvestorclub.databinding.HomeFragmentBinding;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Month;
import com.example.indonesiainvestorclub.models.Performance;
import com.example.indonesiainvestorclub.models.response.PerformanceRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Month> {

  private static final String TAG = HomeViewModel.class.getCanonicalName();

  private HomeFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;

  private PerformanceAdapter adapter;

  private int PAGE = 1;

  public HomeViewModel(Context context, HomeFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

    adapter = new PerformanceAdapter();
    adapter.setListener(this);

    this.binding.perfromancelist.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.perfromancelist.setAdapter(adapter);

//    getPerformance();
    start();
  }

  private void start() {
    getPerformance();
  }

  private void loading(boolean load) {
    loadingState.set(load);
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
        Toast.makeText(getContext(), "Total Data "+datas.length(), Toast.LENGTH_SHORT).show();

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

        int total_data = datas.length();

        data = new Datas(monthList);
        datasList.add(data);
        showPerformance(data, total_data);

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

  private void showPerformance(Datas data, int total_data) {
    hideLoading();

    if (data == null) return;

    adapter.setModels(data.getMonths());
    adapter.notifyDataSetChanged();

    pageState.set(PAGE + " / " + total_data);

    toogleButton(total_data);
  }

  @SuppressWarnings("unused")
  public void onButtonBeforeClick(View view) {
    PAGE--;
    getPerformance();
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    getPerformance();
  }

  private void toogleButton(int maxPages){
    if (PAGE >= 1) {
      nextButtonVisibility.set(true);
      beforeButtonVisibility.set(false);
      if (PAGE > 1) {
        beforeButtonVisibility.set(true);
      }
    }

    if (PAGE == maxPages) {
      nextButtonVisibility.set(false);
      beforeButtonVisibility.set(true);
      if (maxPages == 1){
        beforeButtonVisibility.set(false);
      }
    }

    if (maxPages == 1) {
      nextButtonVisibility.set(false);
      beforeButtonVisibility.set(false);
    }
  }

  private void initChart(PerformanceRes response){

  }

  @Override public void hideLoading() {
      loading(false);
  }

  @Override public void onClickAdapterItem(int index, Month model) {

  }
}
