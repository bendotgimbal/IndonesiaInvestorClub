package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.project.indonesiainvestorclub.databinding.HomeFragmentBinding;
import com.project.indonesiainvestorclub.helper.StringHelper;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.About;
import com.project.indonesiainvestorclub.models.Childs;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.Month;
import com.project.indonesiainvestorclub.models.Performance;
import com.project.indonesiainvestorclub.models.response.AboutRes;
import com.project.indonesiainvestorclub.models.response.PerformanceRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
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
  public ObservableField<String> ytdValueTv;

  public ObservableField<String> aboutTx;
  private MutableLiveData<String> mText;
  private int count = 0;
    public ObservableField<String> alertMessage = new ObservableField<>();
  public ObservableField<String> clicksMessage = new ObservableField<>("No clicks");

  public ObservableBoolean pieChartVisibility;

  private int PAGE = 1;
  private PerformanceRes performanceRes;

  private PieChartView pieChartView;
  private PieChartData pieChartData;

  private LineChartView lineChartView;
  private LineChartData lineChartData;

  public HomeViewModel(Context context, HomeFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

    yearValueTv = new ObservableField<>("0000");
    ytdValueTv = new ObservableField<>("0%");

    aboutTx = new ObservableField<>("");

    pieChartVisibility = new ObservableBoolean(false);

    performanceRes = new PerformanceRes();

    pieChartView = binding.chart;
    lineChartView = binding.chartLine;

    start();
  }

  private void start() {
    getPerformance();
    getAbout();
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

  //API CALL
  private void getAbout() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.aboutRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getAbout) {
              @Override
              protected void onSuccess(Response<JsonElement> response) {
                if (response.body() != null) {
                  readAboutJSON(response.body());
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

      //showPerformance(performanceRes);
      showLineChartPerformance(performanceRes);

      hideLoading();
    } catch (JSONException e) {
      Log.e(TAG, e.toString());
      hideLoading();
    }
  }

  private void readAboutJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      AboutRes aboutRes;

      jsonObject = new JSONObject(response.toString());
      JSONObject objectAbout = jsonObject.getJSONObject("ABOUT_IIC");

      List<About> about = new ArrayList<>();

      for (int i = 1; i <= objectAbout.length(); i++) {
        JSONObject objAbout = objectAbout.getJSONObject(i + "");

        String parent = objAbout.getString("Parent");
        List<Childs> childsList = new ArrayList<>();

        if (objAbout.has("Childs")) {

          //Correction childObject get value from objAbout not from objectAbout
          JSONObject childsObject = objAbout.getJSONObject("Childs");

          for (int o = 1; o <= childsObject.length(); o++) {
            Childs childs = new Childs(childsObject.getString(o + ""));
            childsList.add(childs);
          }
        } else {
          Childs childs = new Childs("");
          childsList.add(childs);
        }

        about.add(new About(parent, childsList));
      }

      aboutRes = new AboutRes(about);

//        Toast.makeText(getContext(), "Result 1 = "+aboutRes.getAbout().get(0).getParent()+" || Result 2 = "+aboutRes.getAbout().get(1).getParent(), Toast.LENGTH_LONG).show();

      showAbout(aboutRes);
      hideLoading();
    } catch (JSONException e) {
      e.printStackTrace();
      hideLoading();
    }
  }

  @SuppressWarnings("unused")
  private void showPerformance(PerformanceRes performanceRes) {
    hideLoading();

    if (performanceRes == null) return;

    setPerformanceRes(performanceRes);

    List<SliceValue> pieData = new ArrayList<>();

    String jan = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJan())
        .replace("-", "0 %");
    String feb = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getFeb())
        .replace("-", "0 %");
    String mar = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getMar())
        .replace("-", "0 %");
    String apr = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getApr())
        .replace("-", "0 %");
    String may = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getMay())
        .replace("-", "0 %");
    String jun = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJun())
        .replace("-", "0 %");
    String jul = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJul())
        .replace("-", "0 %");
    String aug = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getAug())
        .replace("-", "0 %");
    String sep = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getSep())
        .replace("-", "0 %");
    String oct = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getOct())
        .replace("-", "0 %");
    String nov = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getNov())
        .replace("-", "0 %");
    String dec = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getDec())
        .replace("-", "0 %");
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

    pagingInit();
  }

  private void showAbout(AboutRes response) {
    hideLoading();
    if (response == null) return;
    if (response.getAbout() == null) return;
    StringBuilder about = new StringBuilder();

    for (int i = 0; i < response.getAbout().size(); i++) {

    }

    Toast.makeText(getContext(), "Result "+response.getAbout().get(0).getParent(), Toast.LENGTH_LONG).show();
    aboutTx.set(response.getAbout().get(0).getParent());
//    mText = new MutableLiveData<>();
//    mText.setValue(response.getAbout().get(0).getParent());
//      alertMessage.set(response.getAbout().get(0).getParent());
    alertMessage.set("TEST");
    clicksMessage.set(response.getAbout().get(0).getParent());

  }

//  public LiveData<String> getText() {
//    return mText;
//  }

//  public void onBtnClick(View view) {
//    alertMessage.set("TEST");
//  }
//
//  public void onDialogOkClick(DialogInterface dialog, int which) {
//    clicksMessage.set(++count + " clicks");
//  }

  private void showLineChartPerformance(PerformanceRes performanceRes) {
    hideLoading();

    if (performanceRes == null) return;

    setPerformanceRes(performanceRes);

    List<Line> lines = new ArrayList<>();
    List<PointValue> pieData = new ArrayList<>();

    String jan = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJan());
    String feb = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getFeb());
    String mar = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getMar());
    String apr = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getApr());
    String may = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getMay());
    String jun = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJun());
    String jul = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getJul());
    String aug = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getAug());
    String sep = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getSep());
    String oct = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getOct());
    String nov = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getNov());
    String dec = String.valueOf(
        performanceRes.getPerformances().get(0).getData().getMonths().get(PAGE - 1).getDec());

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

    pagingInit();
  }

  private void pagingInit() {

    pageState.set(
        PAGE + " / " + performanceRes.getPerformances().get(0).getData().getMonths().size());

    yearValueTv.set(performanceRes.getPerformances()
        .get(0)
        .getData()
        .getMonths()
        .get(PAGE - 1)
        .getYear());

    ytdValueTv.set(StringHelper.setYTDValue(performanceRes.getPerformances()
        .get(0)
        .getData()
        .getMonths()
        .get(PAGE - 1)
        .getYtd()));

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
    //showPerformance(getPerformanceRes());
    showLineChartPerformance(getPerformanceRes());
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    //showPerformance(getPerformanceRes());
    showLineChartPerformance(getPerformanceRes());
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
