package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.indonesiainvestorclub.adapter.PortfoliosAdapter;
import com.example.indonesiainvestorclub.databinding.PortfolioFragmentBinding;
import com.example.indonesiainvestorclub.helper.StringHelper;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Month;
import com.example.indonesiainvestorclub.models.Performance;
import com.example.indonesiainvestorclub.models.Portfolios;
import com.example.indonesiainvestorclub.models.response.PerformanceRes;
import com.example.indonesiainvestorclub.models.response.PortfolioRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Response;

public class PortfolioViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Portfolios> {

  private static final String TAG = PortfolioViewModel.class.getCanonicalName();

  private PortfolioFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableField<String> pageStatePerformances;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;

  public ObservableBoolean beforeButtonPerformancesVisibility;
  public ObservableBoolean nextButtonPerformancesVisibility;

  public ObservableField<String> yearValueTv;
  public ObservableField<String> ytdValueTv;

  public ObservableField<String> yearPerformancesValueTv;
  public ObservableField<String> ytdPerformancesValueTv;

  private PortfoliosAdapter adapter;

  public ObservableBoolean pieChartVisibility;

  private int PAGE = 1;
  private int PAGEPERFORMANCE = 1;
  private PerformanceRes performanceRes;

  private PieChartView pieChartView;
  private PieChartData pieChartData;

  private LineChartView lineChartView;
  private LineChartData lineChartData;

  public PortfolioViewModel(Context context, PortfolioFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

    pageStatePerformances = new ObservableField<>("1/1");
    beforeButtonPerformancesVisibility = new ObservableBoolean(false);
    nextButtonPerformancesVisibility = new ObservableBoolean(true);

    yearValueTv = new ObservableField<>("0000");
    ytdValueTv = new ObservableField<>("0%");

    yearPerformancesValueTv = new ObservableField<>("0000");
    ytdPerformancesValueTv = new ObservableField<>("0%");

    pieChartVisibility = new ObservableBoolean(false);

    performanceRes = new PerformanceRes();

    pieChartView = binding.chart;
    lineChartView = binding.chartLine;

    adapter = new PortfoliosAdapter();
    adapter.setListener(this);

    this.binding.portfolioslist.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.portfolioslist.setAdapter(adapter);

    start();
  }

  private void start() {
    getPortfolio();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getPortfolio() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.portfolioRequest(PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getPortfolio) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
              readPerformancesJSON(jsonElementResponse.body());
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

      showLineChartPerformance(performanceRes);
    } catch (JSONException e) {
      Log.e(TAG, e.toString());
    }

    readPortfolioJSON(response);
  }

  private void readPortfolioJSON(JsonElement response) {
    JSONObject jsonObjectPortofolio;
    try {
      PortfolioRes portfolioRes;

      jsonObjectPortofolio = new JSONObject(response.toString());
      JSONObject objectPortofolio = jsonObjectPortofolio.getJSONObject("Portfolios");

      List<Portfolios> portfoliolist = new ArrayList<>();
      for (int t = 1; t <= objectPortofolio.length(); t++) {
        JSONObject objPortofolio = objectPortofolio.getJSONObject(t + "");

        Portfolios portfolios = new Portfolios(
            objPortofolio.getString("Date"),
            objPortofolio.getString("Invest(USD)"),
            objPortofolio.getString("Profit(USD)"),
            objPortofolio.getString("Invest(IDR)"),
            objPortofolio.getString("Profit(IDR)"),
            objPortofolio.getString("USDIDR")
        );
        portfoliolist.add(portfolios);
      }

      int page = jsonObjectPortofolio.getInt("Page");
      int pages = jsonObjectPortofolio.getInt("Pages");

      portfolioRes = new PortfolioRes(page, pages, portfoliolist);
      showPortfolio(portfolioRes);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showPortfolio(PortfolioRes portfolioRes) {
    hideLoading();

    if (portfolioRes == null) return;

    adapter.setModels(portfolioRes.getPorfolios());
    adapter.notifyDataSetChanged();

    pageState.set(portfolioRes.getPage() + " / " + portfolioRes.getPages());

    toogleButton(portfolioRes.getPages());
  }

  private void showLineChartPerformance(PerformanceRes performanceRes) {
    hideLoading();

    if (performanceRes == null) return;

    setPerformanceRes(performanceRes);

    List<Line> lines = new ArrayList<>();
    List<PointValue> pieData = new ArrayList<>();

    String jan = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getJan());
    String feb = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getFeb());
    String mar = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getMar());
    String apr = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getApr());
    String may = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getMay());
    String jun = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getJun());
    String jul = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getJul());
    String aug = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getAug());
    String sep = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getSep());
    String oct = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getOct());
    String nov = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getNov());
    String dec = String.valueOf(
            performanceRes.getPerformances().get(0).getData().getMonths().get(PAGEPERFORMANCE - 1).getDec());

    pieData.add(new PointValue(0, StringHelper.setPieValue(jan)).setLabel("JAN"));
    pieData.add(new PointValue(1, StringHelper.setPieValue(feb)).setLabel("FEB"));
    pieData.add(new PointValue(2, StringHelper.setPieValue(mar)).setLabel("MAR"));
    pieData.add(new PointValue(3, StringHelper.setPieValue(apr)).setLabel("APR"));
    pieData.add(new PointValue(4, StringHelper.setPieValue(may)).setLabel("MEI"));
    pieData.add(new PointValue(5, StringHelper.setPieValue(jun)).setLabel("JUN"));
    pieData.add(new PointValue(6, StringHelper.setPieValue(jul)).setLabel("JUL"));
    pieData.add(new PointValue(7, StringHelper.setPieValue(aug)).setLabel("AUG"));
    pieData.add(new PointValue(8, StringHelper.setPieValue(sep)).setLabel("SEPT"));
    pieData.add(new PointValue(9, StringHelper.setPieValue(oct)).setLabel("OCT"));
    pieData.add(new PointValue(10, StringHelper.setPieValue(nov)).setLabel("NOV"));
    pieData.add(new PointValue(11, StringHelper.setPieValue(dec)).setLabel("DES"));

    Line line = new Line(pieData);
    //line.setColor(Color.parseColor("#57DAC6"));
    line.setColor(Color.WHITE);
    line.setShape(ValueShape.CIRCLE);
    //line.setPointColor(Color.parseColor("#57DAC6"));
    line.setPointColor(Color.WHITE);
    line.setCubic(false);
    line.setFilled(true);
    line.setHasLabels(false);
    line.setHasLabelsOnlyForSelected(false);
    line.setHasLines(true);
    line.setHasPoints(true);
    line.setPointRadius(3);
    line.setStrokeWidth(2);
    lines.add(line);

    lineChartData = new LineChartData(lines);

    List<AxisValue> values = new ArrayList<>();
    values.add(new AxisValue(0, "JAN".toCharArray()));
    values.add(new AxisValue(1, "FEB".toCharArray()));
    values.add(new AxisValue(2, "MAR".toCharArray()));
    values.add(new AxisValue(3, "APR".toCharArray()));
    values.add(new AxisValue(4, "MAY".toCharArray()));
    values.add(new AxisValue(5, "JUN".toCharArray()));
    values.add(new AxisValue(6, "JUL".toCharArray()));
    values.add(new AxisValue(7, "AUG".toCharArray()));
    values.add(new AxisValue(8, "SEP".toCharArray()));
    values.add(new AxisValue(9, "OCT".toCharArray()));
    values.add(new AxisValue(10, "NOV".toCharArray()));
    values.add(new AxisValue(11, "DES".toCharArray()));

    Axis axisX = new Axis(values).setHasSeparationLine(false).setTextColor(Color.WHITE);
    Axis axisY = new Axis().setHasSeparationLine(false).setHasLines(true).setTextColor(Color.WHITE);
    lineChartData.setAxisXBottom(axisX);
    lineChartData.setAxisYLeft(axisY);

    lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);

    lineChartView.setInteractive(false);
    lineChartView.setLineChartData(lineChartData);

//    pagingInit();
    pagingPerformancesInit();
  }

//  private void pagingInit() {
//
//    pageState.set(
//            PAGE + " / " + performanceRes.getPerformances().get(0).getData().getMonths().size());
//
//    yearValueTv.set(performanceRes.getPerformances()
//            .get(0)
//            .getData()
//            .getMonths()
//            .get(PAGE - 1)
//            .getYear());
//
//    ytdValueTv.set(StringHelper.setYTDValue(performanceRes.getPerformances()
//            .get(0)
//            .getData()
//            .getMonths()
//            .get(PAGE - 1)
//            .getYtd()));
//
//    toogleButton(performanceRes.getPerformances().get(0).getData().getMonths().size());
//  }

  private void pagingPerformancesInit() {

    pageStatePerformances.set(
            PAGEPERFORMANCE + " / " + performanceRes.getPerformances().get(0).getData().getMonths().size());

    yearPerformancesValueTv.set(performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getYear());

    ytdPerformancesValueTv.set(StringHelper.setYTDValue(performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getYtd()));

    toogleButtonPerformances(performanceRes.getPerformances().get(0).getData().getMonths().size());
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
    getPortfolio();
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    getPortfolio();
  }

  @SuppressWarnings("unused")
  public void onButtonPerformancesBeforeClick(View view) {
    PAGEPERFORMANCE--;
//    getPortfolio();
    showLineChartPerformance(getPerformanceRes());
  }

  @SuppressWarnings("unused")
  public void onButtonPerformancesNextClick(View view) {
    PAGEPERFORMANCE++;
//    getPortfolio();
    showLineChartPerformance(getPerformanceRes());
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
  }

  private void toogleButtonPerformances(int maxPages){
    if (PAGEPERFORMANCE >= 1) {
      nextButtonPerformancesVisibility.set(true);
      beforeButtonPerformancesVisibility.set(false);
      if (PAGEPERFORMANCE > 1) {
        beforeButtonPerformancesVisibility.set(true);
      }
    }

    if (PAGEPERFORMANCE == maxPages) {
      nextButtonPerformancesVisibility.set(false);
      beforeButtonPerformancesVisibility.set(true);
      if (maxPages == 1){
        beforeButtonPerformancesVisibility.set(false);
      }
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Portfolios model) {

  }
}
