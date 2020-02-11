package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.adapter.ParticipantAdapter;
import com.project.indonesiainvestorclub.adapter.PerformanceAdapter;
import com.project.indonesiainvestorclub.adapter.PerformanceYearAdapter;
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
  public ObservableField<String> previousInvest;

  public ObservableBoolean fundsTab;
  public ObservableBoolean performanceTab;
  public ObservableBoolean participantTab;
  public ObservableBoolean userTab;

  public ObservableBoolean beforeButtonPerformancesVisibility;
  public ObservableBoolean nextButtonPerformancesVisibility;

  private int draggingView = -1;

  private PerformanceAdapter performanceAdapter;
  private ParticipantAdapter participantAdapter;
  private PerformanceYearAdapter performanceYearAdapter;

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
    previousInvest = new ObservableField<>("-");

    fundsTab = new ObservableBoolean(true);
    performanceTab = new ObservableBoolean(false);
    participantTab = new ObservableBoolean(false);
    userTab = new ObservableBoolean(false);

    performanceAdapter = new PerformanceAdapter();
    participantAdapter = new ParticipantAdapter();
    performanceYearAdapter = new PerformanceYearAdapter();

    this.binding.tablePerformance.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.tablePerformance.setAdapter(performanceAdapter);

    this.binding.yearColumn.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.yearColumn.setAdapter(performanceYearAdapter);

    this.binding.participantList.setLayoutManager(
        new GridLayoutManager(getContext(), 2));
    this.binding.participantList.setAdapter(participantAdapter);

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (binding.tablePerformance == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 1;
        } else if (binding.yearColumn == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 2;
        }
      }

      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (draggingView == 1 && recyclerView == binding.tablePerformance) {
          binding.yearColumn.scrollBy(dx, dy);
        } else if (draggingView == 2 && recyclerView == binding.yearColumn) {
          binding.tablePerformance.scrollBy(dx, dy);
        }
      }
    };

    this.binding.tablePerformance.addOnScrollListener(scrollListener);
    this.binding.yearColumn.addOnScrollListener(scrollListener);
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
    performanceYearAdapter.setModels(investRes.getInvests().getData().getMonths());

    performanceAdapter.notifyDataSetChanged();
    performanceYearAdapter.notifyDataSetChanged();
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
    previousInvest.set(investRes.getParticipant().getParticipantInvestPrevious().getInvestText());

    participantAdapter.setModels(
        investRes.getParticipant().getParticipantInvestCurrent().getCurrent());
    participantAdapter.notifyDataSetChanged();
  }

  @SuppressWarnings("unused")
  public void onClickFunds(View view) {
    if (!fundsTab.get()) {
      fundsTab.set(true);
      performanceTab.set(false);
      participantTab.set(false);
      userTab.set(false);
    }
  }

  @SuppressWarnings("unused")
  public void onClickPerformance(View view) {
    if (!performanceTab.get()) {
      fundsTab.set(false);
      performanceTab.set(true);
      participantTab.set(false);
      userTab.set(false);
    }
  }

  @SuppressWarnings("unused")
  public void onClickParticipant(View view) {
    if (!participantTab.get()) {
      fundsTab.set(false);
      performanceTab.set(false);
      participantTab.set(true);
      userTab.set(false);
    }
  }

  @SuppressWarnings("unused")
  public void onClickUser(View view) {
    if (!userTab.get()) {
      fundsTab.set(false);
      performanceTab.set(false);
      participantTab.set(false);
      userTab.set(true);
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Invest model) {

  }
}
