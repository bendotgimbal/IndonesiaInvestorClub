package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.adapter.ParticipantAdapter;
import com.project.indonesiainvestorclub.adapter.PerformanceAdapter;
import com.project.indonesiainvestorclub.adapter.PerformanceListviewAdapter;
import com.project.indonesiainvestorclub.databinding.InvestActivityBinding;
import com.project.indonesiainvestorclub.helper.StringHelper;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.BankFundInvest;
import com.project.indonesiainvestorclub.models.CurrentData;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.FundInvest;
import com.project.indonesiainvestorclub.models.Invest;
import com.project.indonesiainvestorclub.models.Meta;
import com.project.indonesiainvestorclub.models.Month;
import com.project.indonesiainvestorclub.models.Monthly;
import com.project.indonesiainvestorclub.models.ParticipantInvest;
import com.project.indonesiainvestorclub.models.ParticipantInvestCurrent;
import com.project.indonesiainvestorclub.models.ParticipantInvestPrevious;
import com.project.indonesiainvestorclub.models.response.InvestRes;
import com.project.indonesiainvestorclub.models.response.PerformanceRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
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

public class InvestViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Invest> {

  private final static String TAG = InvestViewModel.class.getCanonicalName();

  private InvestActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageStatePerformances;
  public ObservableField<String> investIDValueTx;
  public ObservableField<String> investNameTx;
  public ObservableField<String> yearPerformancesValueTv;
  public ObservableField<String> ytdPerformancesValueTv;

  public ObservableField<String> fundsNameLabelTx;
  public ObservableField<String> fundsTypeValueTx;
  public ObservableField<String> fundsEquityProgressValueTx;
  public ObservableField<String> fundsSlotsValueTx;
  public ObservableField<String> fundsRoiValueTx;
  public ObservableField<String> fundsCompoundingValueTx;
  public ObservableField<String> fundsYouInvestValueTx;
  public ObservableField<String> fundsCcNoValueTx;
  public ObservableField<String> fundsInvestorPassValueTx;
  public ObservableField<String> fundsServerValueTx;
  public ObservableField<String> fundsUSDIDRValueTx;
  public ObservableField<String> fundsBankNameValueTx;
  public ObservableField<String> fundsBankAccNameValueTx;
  public ObservableField<String> fundsBankAccNoValueTx;
  public ObservableField<String> fundsUserDateValueTx;
  public ObservableField<String> fundsUserIDValueTx;
  public ObservableField<String> fundsUserNameValueTx;
  public ObservableField<String> fundsUserInvestValueTx;
  public ObservableField<String> fundsUserStatusIDValueTx;
  public ObservableField<String> fundsUserStatusValueTx;
  public ObservableField<String> fundsUserWdIDValueTx;

  public ObservableField<String> previousDate;
  public ObservableField<String> previousIvest;

  public ObservableBoolean tablePerformanceVisibility;
  public ObservableBoolean fundsListVisibility;
  public ObservableBoolean participantVisibility;
  public ObservableBoolean userListVisibility;

  public ObservableBoolean beforeButtonPerformancesVisibility;
  public ObservableBoolean nextButtonPerformancesVisibility;

  private int PAGEPERFORMANCE = 1;
  private int PAGE = 1;
  private PerformanceRes performanceRes;

  private PieChartView pieChartView;
  private PieChartData pieChartData;

  private LineChartView lineChartView;
  private LineChartData lineChartData;

  public ObservableBoolean pieChartVisibility;

  private PerformanceListviewAdapter simpleAdapter;
  private PerformanceAdapter performanceAdapter;
  private ParticipantAdapter participantAdapter;

  public InvestViewModel(Context context, InvestActivityBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    investIDValueTx = new ObservableField<>("");
    investNameTx = new ObservableField<>("");

    pageStatePerformances = new ObservableField<>("1/1");
    beforeButtonPerformancesVisibility = new ObservableBoolean(false);
    nextButtonPerformancesVisibility = new ObservableBoolean(true);

    yearPerformancesValueTv = new ObservableField<>("0000");
    ytdPerformancesValueTv = new ObservableField<>("0%");

    fundsNameLabelTx = new ObservableField<>("-");
    fundsTypeValueTx = new ObservableField<>("-");
    fundsEquityProgressValueTx = new ObservableField<>("-");
    fundsSlotsValueTx = new ObservableField<>("-");
    fundsRoiValueTx = new ObservableField<>("-");
    fundsCompoundingValueTx = new ObservableField<>("-");
    fundsYouInvestValueTx = new ObservableField<>("-");
    fundsCcNoValueTx = new ObservableField<>("-");
    fundsInvestorPassValueTx = new ObservableField<>("-");
    fundsServerValueTx = new ObservableField<>("-");
    fundsUSDIDRValueTx = new ObservableField<>("-");
    fundsBankNameValueTx = new ObservableField<>("-");
    fundsBankAccNameValueTx = new ObservableField<>("-");
    fundsBankAccNoValueTx = new ObservableField<>("-");
    fundsUserDateValueTx = new ObservableField<>("-");
    fundsUserIDValueTx = new ObservableField<>("-");
    fundsUserNameValueTx = new ObservableField<>("-");
    fundsUserInvestValueTx = new ObservableField<>("-");
    fundsUserStatusIDValueTx = new ObservableField<>("-");
    fundsUserStatusValueTx = new ObservableField<>("-");
    fundsUserWdIDValueTx = new ObservableField<>("-");

    previousDate = new ObservableField<>("-");
    previousIvest = new ObservableField<>("-");

    performanceAdapter = new PerformanceAdapter();
    participantAdapter = new ParticipantAdapter();

    tablePerformanceVisibility = new ObservableBoolean(true);
    fundsListVisibility = new ObservableBoolean(false);
    participantVisibility = new ObservableBoolean(false);
    userListVisibility = new ObservableBoolean(false);

    pieChartVisibility = new ObservableBoolean(false);

    arrowTabelPerformanceVisibility();
    arrowFundsListVisibility();
    arrowParticipantVisibility();
    arrowuserListVisibility();

    this.binding.tablePerformance.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.tablePerformance.setAdapter(performanceAdapter);
    this.binding.horizontalTableView.setSmoothScrollingEnabled(true);
    this.binding.horizontalTableView.setScrollbarFadingEnabled(false);

    this.binding.participantList.setLayoutManager(
        new GridLayoutManager(getContext(), 2));
    this.binding.participantList.setAdapter(participantAdapter);
  }

  public void start(String id) {
    getInvest(id);
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getInvest(String id) {
    loading(true);

    Disposable disposable = ServiceGenerator.service.investRequest(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, () -> getInvest(id)) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
              loading(false);
              readInvestJSON(jsonElementResponse.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readInvestJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      InvestRes investRes;

      Invest invest;
      Datas data;

      jsonObject = new JSONObject(response.toString());

      //PERFORMANCE
      JSONObject objectInvest = jsonObject.getJSONObject("Performance");

      String name = objectInvest.getString("Name");
      JSONObject datasObj = objectInvest.getJSONObject("Datas");

      List<Month> monthList = new ArrayList<>();

      for (int i = 1; i <= datasObj.length(); i++) {
        JSONObject objInvest = datasObj.getJSONObject(i + "");

        Month month = new Month(
            objInvest.getString("YEAR"),
            objInvest.getString("Jan"),
            objInvest.getString("Feb"),
            objInvest.getString("Mar"),
            objInvest.getString("Apr"),
            objInvest.getString("May"),
            objInvest.getString("Jun"),
            objInvest.getString("Jul"),
            objInvest.getString("Aug"),
            objInvest.getString("Sep"),
            objInvest.getString("Oct"),
            objInvest.getString("Nov"),
            objInvest.getString("Dec"),
            objInvest.getString("YTD")
        );
        monthList.add(month);
      }
      data = new Datas(monthList);

      invest = new Invest(name, data);

      //FUNDS
      Meta meta;
      BankFundInvest bankFundInvest;

      JSONObject fundObj = jsonObject.getJSONObject("Fund");
      JSONObject fundMetaObj = fundObj.getJSONObject("Meta");

      String fundMetaAccNo = fundMetaObj.getString("AccNo");
      String fundMetaInvestorPass = fundMetaObj.getString("InvestorPass");
      String fundMetaServer = fundMetaObj.getString("Server");

      meta = new Meta(fundMetaAccNo, fundMetaInvestorPass, fundMetaServer);

      JSONObject fundBankObj = fundObj.getJSONObject("Bank");
      String fundBankName = fundBankObj.getString("Name");
      String fundBankAccName = fundBankObj.getString("AccName");
      String fundBankAccNo = fundBankObj.getString("AccNo");

      bankFundInvest = new BankFundInvest(fundBankName, fundBankAccName, fundBankAccNo);

      FundInvest fundInvest = new FundInvest(
          fundObj.getString("Name"),
          fundObj.getString("Type"),
          fundObj.getString("Manager"),
          fundObj.getString("Invested"),
          fundObj.getString("Equity"),
          fundObj.getString("Slots"),
          fundObj.getString("Compounding"),
          fundObj.getString("ROI"),
          meta,
          fundObj.getString("USDIDR"),
          bankFundInvest
      );

      //PARTICIPANT
      JSONObject objectParticipant = jsonObject.getJSONObject("Participant");
      JSONObject participantPreviousObj = objectParticipant.getJSONObject("Previous");

      String participantPreviousDate = participantPreviousObj.getString("Date");
      String participantPreviousInvest = participantPreviousObj.getString("Invest");

      ParticipantInvestPrevious previous =
          new ParticipantInvestPrevious(participantPreviousDate, participantPreviousInvest);

      JSONObject participantCurrentObj = objectParticipant.getJSONObject("Current");
      List<CurrentData> currentDatalist = new ArrayList<>();
      for (int i = 1; i <= participantCurrentObj.length(); i++) {
        JSONObject objCurrent = participantCurrentObj.getJSONObject(i + "");
        CurrentData currentData;

        currentData = new CurrentData(
            objCurrent.getString("Date"),
            objCurrent.getString("UserID"),
            objCurrent.getString("Name"),
            objCurrent.getString("Invest"),
            objCurrent.getString("StatusID"),
            objCurrent.getString("Status"),
            objCurrent.getString("WdID")
        );
        currentDatalist.add(currentData);
      }

      ParticipantInvestCurrent current = new ParticipantInvestCurrent(currentDatalist);
      ParticipantInvest participantInvest = new ParticipantInvest(previous, current);

      //USER
      JSONObject objectUser = jsonObject.getJSONObject("User");
      List<CurrentData> users = new ArrayList<>();
      for (int i = 1; i <= objectUser.length(); i++) {
        JSONObject objCurrent = participantCurrentObj.getJSONObject(i + "");

        CurrentData currentData = new CurrentData(
            objCurrent.getString("Date"),
            objCurrent.getString("UserID"),
            objCurrent.getString("Name"),
            objCurrent.getString("Invest"),
            objCurrent.getString("StatusID"),
            objCurrent.getString("Status"),
            objCurrent.getString("WdID")
        );

        users.add(currentData);
      }

      investRes = new InvestRes(invest, fundInvest, participantInvest, users);

      showInvest(investRes);

      showPerformanceTable(investRes);

      hideLoading();
    } catch (JSONException e) {
      e.printStackTrace();
      Log.e(TAG, e.toString());
      hideLoading();
    }
  }

  private void showPerformanceTable(InvestRes investRes) {
    performanceAdapter.setModels(investRes.getInvests().getData().getMonths());
    performanceAdapter.notifyDataSetChanged();
  }

  private void showInvest(InvestRes investRes) {
    hideLoading();
    if (investRes == null) return;
    investNameTx.set(investRes.getInvests().getName());
    yearPerformancesValueTv.set(investRes.getInvests().getData().getMonths().get(0).getYear());
    ytdPerformancesValueTv.set(investRes.getInvests().getData().getMonths().get(0).getYtd());

    fundsNameLabelTx.set(investRes.getFunds().getName());
    fundsTypeValueTx.set(investRes.getFunds().getTypeManager());
    fundsYouInvestValueTx.set(investRes.getFunds().getInvested());
    fundsEquityProgressValueTx.set(investRes.getFunds().getEquity());
    fundsSlotsValueTx.set(investRes.getFunds().getSlots());
    fundsRoiValueTx.set(investRes.getFunds().getROI());
    fundsCompoundingValueTx.set(investRes.getFunds().getCompounding());
    fundsCcNoValueTx.set(investRes.getFunds().getMeta().getAccNo());
    fundsInvestorPassValueTx.set(investRes.getFunds().getMeta().getInvestorPass());
    fundsServerValueTx.set(investRes.getFunds().getMeta().getServer());
    fundsUSDIDRValueTx.set(investRes.getFunds().getUSDIDR());
    fundsBankNameValueTx.set(investRes.getFunds().getBankFundInvest().getName());
    fundsBankAccNameValueTx.set(investRes.getFunds().getBankFundInvest().getAccName());
    fundsBankAccNoValueTx.set(investRes.getFunds().getBankFundInvest().getAccNo());
    fundsUserDateValueTx.set(investRes.getUser().get(0).getDate());
    fundsUserIDValueTx.set(investRes.getUser().get(0).getUserID());
    fundsUserNameValueTx.set(investRes.getUser().get(0).getName());
    fundsUserInvestValueTx.set(investRes.getUser().get(0).getInvest());
    fundsUserStatusIDValueTx.set(investRes.getUser().get(0).getStatusID());
    fundsUserStatusValueTx.set(investRes.getUser().get(0).getStatus());
    fundsUserWdIDValueTx.set(investRes.getUser().get(0).getWdID());


    previousDate.set(investRes.getParticipant().getParticipantInvestPrevious().getDateText());
    previousIvest.set(investRes.getParticipant().getParticipantInvestPrevious().getInvestText());

    participantAdapter.setModels(
        investRes.getParticipant().getParticipantInvestCurrent().getCurrent());
    participantAdapter.notifyDataSetChanged();

    //TODO recyclerview
  }

  private void showLineChartPerformance(PerformanceRes performanceRes) {
    hideLoading();

    if (performanceRes == null) return;

    setPerformanceRes(performanceRes);

    List<Line> lines = new ArrayList<>();
    List<PointValue> pieData = new ArrayList<>();

    String jan = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getJan());
    String feb = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getFeb());
    String mar = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getMar());
    String apr = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getApr());
    String may = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getMay());
    String jun = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getJun());
    String jul = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getJul());
    String aug = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getAug());
    String sep = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getSep());
    String oct = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getOct());
    String nov = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getNov());
    String dec = String.valueOf(
        performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .get(PAGEPERFORMANCE - 1)
            .getDec());

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

    simpleAdapter.clear();

    simpleAdapter.add(new Monthly("JANUARY", jan));
    simpleAdapter.add(new Monthly("FEBRUARY", feb));
    simpleAdapter.add(new Monthly("MARCH", mar));
    simpleAdapter.add(new Monthly("APRIL", apr));
    simpleAdapter.add(new Monthly("MAY", may));
    simpleAdapter.add(new Monthly("JUNE", jun));
    simpleAdapter.add(new Monthly("JULY", jul));
    simpleAdapter.add(new Monthly("AUGUST", aug));
    simpleAdapter.add(new Monthly("SEPTEMBER", sep));
    simpleAdapter.add(new Monthly("OCTOBER", oct));
    simpleAdapter.add(new Monthly("NOVEMBER", nov));
    simpleAdapter.add(new Monthly("DESEMBER", dec));

    simpleAdapter.notifyDataSetChanged();

    Line line = new Line(pieData);
    line.setColor(Color.parseColor("#57DAC6"));
    //line.setColor(Color.WHITE);
    line.setShape(ValueShape.CIRCLE);
    line.setPointColor(Color.parseColor("#57DAC6"));
    //line.setPointColor(Color.WHITE);
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

    Axis axisX =
        new Axis(values).setHasSeparationLine(false).setTextColor(Color.parseColor("#57DAC6"));
    Axis axisY = new Axis().setHasSeparationLine(false)
        .setHasLines(true)
        .setTextColor(Color.parseColor("#57DAC6"));
    lineChartData.setAxisXBottom(axisX);
    lineChartData.setAxisYLeft(axisY);

    lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);

    lineChartView.setInteractive(false);
    lineChartView.setLineChartData(lineChartData);

    pagingInit();
  }

  private void pagingInit() {

    pageStatePerformances.set(
        PAGEPERFORMANCE + " / " + performanceRes.getPerformances()
            .get(0)
            .getData()
            .getMonths()
            .size());

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
    PAGEPERFORMANCE--;
    showLineChartPerformance(getPerformanceRes());
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGEPERFORMANCE++;
    showLineChartPerformance(getPerformanceRes());
  }

  private void toogleButton(int maxPages) {
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
      if (maxPages == 1) {
        beforeButtonPerformancesVisibility.set(false);
      }
    }
  }

  @SuppressWarnings("unused")
  public void onClickTablePerformanceHideShow(View view) {
    if (!tablePerformanceVisibility.get()) {
      tablePerformanceVisibility.set(true);
      arrowTabelPerformanceVisibility();
      return;
    }

    tablePerformanceVisibility.set(false);
    arrowTabelPerformanceVisibility();
  }

  @SuppressWarnings("unused")
  public void onClickFundsListHideShow(View view) {
    if (!fundsListVisibility.get()) {
      fundsListVisibility.set(true);
      arrowFundsListVisibility();
      return;
    }

    fundsListVisibility.set(false);
    arrowFundsListVisibility();
  }

  @SuppressWarnings("unused")
  public void onClickParticipantHideShow(View view) {
    if (!participantVisibility.get()) {
      participantVisibility.set(true);
      arrowParticipantVisibility();
      return;
    }

    participantVisibility.set(false);
    arrowParticipantVisibility();
  }


  public void onClickUserListHideShow(View view){
    if (!userListVisibility.get()){
      userListVisibility.set(true);
      arrowuserListVisibility();
      return;
    }

    userListVisibility.set(false);
    arrowuserListVisibility();
  }

  private void arrowTabelPerformanceVisibility() {
    if (tablePerformanceVisibility.get()) {
      binding.tablePerformanceVisibilityButton.setImageResource(
          R.drawable.ic_arrow_drop_down_black_24dp);
    } else {
      binding.tablePerformanceVisibilityButton.setImageResource(
          R.drawable.ic_arrow_drop_up_black_24dp);
    }
  }

  private void arrowFundsListVisibility() {
    if (fundsListVisibility.get()) {
      binding.fundsListVisibilityButton.setImageResource(
          R.drawable.ic_arrow_drop_down_black_24dp);
    } else {
      binding.fundsListVisibilityButton.setImageResource(
          R.drawable.ic_arrow_drop_up_black_24dp);
    }
  }

  private void arrowParticipantVisibility() {
    if (participantVisibility.get()) {
      binding.participantVisibilityButton.setImageResource(
          R.drawable.ic_arrow_drop_down_black_24dp);
    } else {
      binding.participantVisibilityButton.setImageResource(
          R.drawable.ic_arrow_drop_up_black_24dp);
    }
  }

  private void arrowuserListVisibility(){
    if (userListVisibility.get()){
      binding.userListVisibilityButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
    }else {
      binding.userListVisibilityButton.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Invest model) {

  }
}
