package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.project.indonesiainvestorclub.databinding.InvestActivityBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.BankFundInvest;
import com.project.indonesiainvestorclub.models.CurrentData;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.FundInvest;
import com.project.indonesiainvestorclub.models.Invest;
import com.project.indonesiainvestorclub.models.Meta;
import com.project.indonesiainvestorclub.models.Month;
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
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
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
  private MutableLiveData<String> mText;
  private ObservableField<String> mInvestID;

  public ObservableBoolean beforeButtonPerformancesVisibility;
  public ObservableBoolean nextButtonPerformancesVisibility;

  private int PAGEPERFORMANCE = 1;
  private PerformanceRes performanceRes;

  private PieChartView pieChartView;
  private PieChartData pieChartData;

  private LineChartView lineChartView;
  private LineChartData lineChartData;

  public ObservableBoolean pieChartVisibility;

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

    performanceRes = new PerformanceRes();

    pieChartVisibility = new ObservableBoolean(false);

    pieChartView = binding.chart;
    lineChartView = binding.chartLine;
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
      InvestRes investRes = new InvestRes();

      Invest invest;
      Datas data;

      jsonObject = new JSONObject(response.toString());
      JSONObject objectInvest = jsonObject.getJSONObject("Performance");

      String name = objectInvest.getString("Name");
      JSONObject datasObj = objectInvest.getJSONObject("Datas");

      List<Invest> invests = new ArrayList<>();
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
      invests.add(invest);

      Meta meta;
      BankFundInvest bankFundInvest;
      List<FundInvest> fundsInvestList = new ArrayList<>();
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
      fundsInvestList.add(fundInvest);

      JSONObject objectParticipant = jsonObject.getJSONObject("Participant");
      JSONObject participantPreviousObj = objectParticipant.getJSONObject("Previous");
      String participantPreviousDate = participantPreviousObj.getString("Date");
      String participantPreviousInvest = participantPreviousObj.getString("Invest");
      ParticipantInvestPrevious participantInvestPrevious =
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
      //            Current current = new Current(currentDatalist);
      ParticipantInvestCurrent participantInvestCurrent =
          new ParticipantInvestCurrent(currentDatalist);
      ParticipantInvest participantInvest =
          new ParticipantInvest(participantInvestPrevious, participantInvestCurrent);

      //            investRes = new InvestRes(participantInvest,fundsInvestList,participantInvest,participantInvest);
      //            investRes = new InvestRes(investList);
      investRes.setInvests(invest);
      showInvest(investRes);

      hideLoading();
    } catch (JSONException e) {
      e.printStackTrace();
      Log.e(TAG, e.toString());
      hideLoading();
    }
  }

  private void showInvest(InvestRes investRes) {
    hideLoading();
    if (investRes == null) return;
    investNameTx.set(investRes.getInvests().getName());
    yearPerformancesValueTv.set(investRes.getInvests().getData().getMonths().get(0).getYear());
    ytdPerformancesValueTv.set(investRes.getInvests().getData().getMonths().get(0).getYtd());

    Toast.makeText(context, "Name Invest " + investNameTx.get(), Toast.LENGTH_SHORT).show();
    Log.d(TAG, "Year : "+yearPerformancesValueTv.get());
    Log.d(TAG, "YTD : "+ytdPerformancesValueTv.get());

    //TODO recyclerview
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Invest model) {

  }
}
