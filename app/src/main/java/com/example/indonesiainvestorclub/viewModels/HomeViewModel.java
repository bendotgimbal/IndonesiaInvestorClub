package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.example.indonesiainvestorclub.databinding.HomeFragmentBinding;
import com.example.indonesiainvestorclub.helper.StringHelper;
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
import lecho.lib.hellocharts.util.ChartUtils;
import org.json.JSONException;
import org.json.JSONObject;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Month> {

  private static final String TAG = HomeViewModel.class.getCanonicalName();

  private HomeFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;

  public ObservableField<String> yearValueTv;
  public ObservableField<String> monthValuePie;

  //private PerformanceAdapter adapter;

  private int PAGE = 1;
  private PerformanceRes performanceRes;

  private PieChartView pieChartView;
  private PieChartData pieChartData;

  public HomeViewModel(Context context, HomeFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

    yearValueTv = new ObservableField<>("");
    monthValuePie = new ObservableField<>("");

    performanceRes = new PerformanceRes();

    pieChartView = binding.chart;

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

        performance = new Performance(name, data);
        performances.add(performance);
      }

      performanceRes.setPerformances(performances);

      showPerformance(performanceRes);

      hideLoading();
    } catch (JSONException e) {
      Log.e(TAG, e.toString());
      hideLoading();
    }
  }

  private void showPerformance(PerformanceRes performanceRes) {
    hideLoading();

    if (performanceRes == null) return;

    setPerformanceRes(performanceRes);

    List<SliceValue> pieData = new ArrayList<>();

    String jan = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJan()).replace("-","0 %");
    String feb = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getFeb()).replace("-","0 %");
    String mar = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getMar()).replace("-","0 %");
    String apr = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getApr()).replace("-","0 %");
    String may = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getMay()).replace("-","0 %");
    String jun = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJun()).replace("-","0 %");
    String jul = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJul()).replace("-","0 %");
    String aug = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getAug()).replace("-","0 %");
    String sep = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getSep()).replace("-","0 %");
    String oct = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getOct()).replace("-","0 %");
    String nov = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getNov()).replace("-","0 %");
    String dec = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getDec()).replace("-","0 %");
    String ytd = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getYtd());
    String year = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getYear());

    pieData.add(new SliceValue(StringHelper.setPieValue(jan), ChartUtils.pickColor()).setLabel(
        "Jan: " + jan));
    pieData.add(new SliceValue(StringHelper.setPieValue(feb), ChartUtils.pickColor()).setLabel(
        "Feb: " + feb));
    pieData.add(new SliceValue(StringHelper.setPieValue(mar), ChartUtils.pickColor()).setLabel(
        "Mar: " + mar));
    pieData.add(new SliceValue(StringHelper.setPieValue(apr), ChartUtils.pickColor()).setLabel(
        "Apr: " + apr));
    pieData.add(new SliceValue(StringHelper.setPieValue(may), ChartUtils.pickColor()).setLabel(
        "May: " + may));
    pieData.add(new SliceValue(StringHelper.setPieValue(jun), ChartUtils.pickColor()).setLabel(
        "Jun: " + jun));
    pieData.add(new SliceValue(StringHelper.setPieValue(jul), ChartUtils.pickColor()).setLabel(
        "Jul: " + jul));
    pieData.add(new SliceValue(StringHelper.setPieValue(aug), ChartUtils.pickColor()).setLabel(
        "Aug: " + aug));
    pieData.add(new SliceValue(StringHelper.setPieValue(sep), ChartUtils.pickColor()).setLabel(
        "Sep: " + sep));
    pieData.add(new SliceValue(StringHelper.setPieValue(oct), ChartUtils.pickColor()).setLabel(
        "Oct: " + oct));
    pieData.add(new SliceValue(StringHelper.setPieValue(nov), ChartUtils.pickColor()).setLabel(
        "Nov: " + nov));
    pieData.add(new SliceValue(StringHelper.setPieValue(dec), ChartUtils.pickColor()).setLabel(
        "Dec: " + dec));

    pieChartData = new PieChartData(pieData);
    pieChartData.setHasLabels(true).setValueLabelTextSize(14);
    pieChartData.setHasCenterCircle(true)
        .setCenterText1("Performance in year")
        .setCenterText1FontSize(20)
        .setCenterText1Color(Color.parseColor("#0097A7"));
    pieChartView.setPieChartData(pieChartData);

    yearValueTv.set(performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE -1).getYear());

    pageState.set(
        PAGE + " / " + performanceRes.getPerformances().get(0).getData().getMonths().size());

    toogleButton(performanceRes.getPerformances().get(0).getData().getMonths().size());
  }

  private PerformanceRes getPerformanceRes() {
    return performanceRes;
  }

  private void setPerformanceRes(
      PerformanceRes performanceRes) {
    this.performanceRes = performanceRes;
  }

  @SuppressWarnings("unused")
  public void onButtonBeforeClick(View view) {
    PAGE--;
    showPerformance(getPerformanceRes());
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    showPerformance(getPerformanceRes());
  }

  private void toogleButton(int maxPages) {
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
      if (maxPages == 1) {
        beforeButtonVisibility.set(false);
      }
    }
  }

  @SuppressWarnings("unused")
  private void initChart(PerformanceRes response) {

  }

  @Override public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Month model) {

  }
}
