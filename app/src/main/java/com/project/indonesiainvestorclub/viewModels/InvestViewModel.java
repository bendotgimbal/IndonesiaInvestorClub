package com.project.indonesiainvestorclub.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.project.indonesiainvestorclub.adapter.UserAdapter;
import com.project.indonesiainvestorclub.databinding.InvestActivityBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.BankFundInvest;
import com.project.indonesiainvestorclub.models.CurrentData;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.FundInvest;
import com.project.indonesiainvestorclub.models.Funds;
import com.project.indonesiainvestorclub.models.Invest;
import com.project.indonesiainvestorclub.models.Meta;
import com.project.indonesiainvestorclub.models.Month;
import com.project.indonesiainvestorclub.models.ParticipantInvest;
import com.project.indonesiainvestorclub.models.ParticipantInvestCurrent;
import com.project.indonesiainvestorclub.models.ParticipantInvestPrevious;
import com.project.indonesiainvestorclub.models.response.FundsRes;
import com.project.indonesiainvestorclub.models.response.InvestRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.views.InvestFundsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.project.indonesiainvestorclub.views.MainActivity.FUND_MENU;

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

  //SecondFunds
  public ObservableField<String> fundsSecondNameLabelTx;
  public ObservableField<String> fundsSecondTypeValueTx;
  public ObservableField<String> fundsSecondEquityProgressValueTx;
  public ObservableField<String> fundsSecondSlotsValueTx;
  public ObservableField<String> fundsSecondRoiValueTx;
  public ObservableField<String> fundsSecondCompoundingValueTx;
  public ObservableField<String> fundsSecondYouInvestValueTx;
  public ObservableField<String> fundsSecondCcNoValueTx;
  public ObservableField<String> fundsSecondInvestorPassValueTx;
  public ObservableField<String> fundsSecondServerValueTx;
  private String investId;
  private String investSlot;
  private String investIDRValue;
  private int page;
  private String pages;
  private int PAGES_SecondFunds;
  private int pageFirst;
  private int pageSecond;
  private String pagesTotal;

  public ObservableField<String> strInvestUSDValue;
  public ObservableField<String> strInvestIDRValue;
  public ObservableField<String> strInvestID;

  public ObservableField<String> strPages;

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
  private UserAdapter userAdapter;

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

    //SecondFunds
    fundsSecondNameLabelTx = new ObservableField<>("-");
    fundsSecondTypeValueTx = new ObservableField<>("-");
    fundsSecondEquityProgressValueTx = new ObservableField<>("-");
    fundsSecondSlotsValueTx = new ObservableField<>("-");
    fundsSecondRoiValueTx = new ObservableField<>("-");
    fundsSecondCompoundingValueTx = new ObservableField<>("-");
    fundsSecondYouInvestValueTx = new ObservableField<>("-");
    fundsSecondCcNoValueTx = new ObservableField<>("-");
    fundsSecondInvestorPassValueTx = new ObservableField<>("-");
    fundsSecondServerValueTx = new ObservableField<>("-");

    previousDate = new ObservableField<>("-");
    previousInvest = new ObservableField<>("-");

    strInvestUSDValue = new ObservableField<>("0");
    strInvestIDRValue = new ObservableField<>("0");
    strInvestID = new ObservableField<>("0");

    strPages = new ObservableField<>("0");

    fundsTab = new ObservableBoolean(true);
    performanceTab = new ObservableBoolean(false);
    participantTab = new ObservableBoolean(false);
    userTab = new ObservableBoolean(false);

    performanceAdapter = new PerformanceAdapter();
    participantAdapter = new ParticipantAdapter();
    performanceYearAdapter = new PerformanceYearAdapter();

    this.binding.investPerformance.tablePerformance.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.investPerformance.tablePerformance.setAdapter(performanceAdapter);

    this.binding.investPerformance.yearColumn.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.investPerformance.yearColumn.setAdapter(performanceYearAdapter);

    //this.binding.investParticipant.participantList.setLayoutManager(
    //    new GridLayoutManager(getContext(), 2));
    this.binding.investParticipant.participantList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.investParticipant.participantList.setAdapter(participantAdapter);

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (binding.investPerformance.tablePerformance == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 1;
        } else if (binding.investPerformance.yearColumn == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 2;
        }
      }

      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (draggingView == 1 && recyclerView == binding.investPerformance.tablePerformance) {
          binding.investPerformance.yearColumn.scrollBy(dx, dy);
        } else if (draggingView == 2 && recyclerView == binding.investPerformance.yearColumn) {
          binding.investPerformance.tablePerformance.scrollBy(dx, dy);
        }
      }
    };

    this.binding.investPerformance.tablePerformance.addOnScrollListener(scrollListener);
    this.binding.investPerformance.yearColumn.addOnScrollListener(scrollListener);
  }

  public void start(String investSlot, String investIDRValue, String id, String pages) {
    getInvest(id);
//    getSecondFunds(pages);
    getFunds();
    strInvestUSDValue.set(investSlot);
    strInvestIDRValue.set(investIDRValue);
    strInvestID.set(id);
    Log.d("Debug", "USD "
            + getStrInvestUSDValue()
            + " / "
            + " IDR "
            + getStrInvestIDRValue()
            + " || "
            + " ID this page "
            + getStrInvestID());
    strPages.set(pages);
    Log.d("Debug", "page = "+getStrPages());
    PAGES_SecondFunds = Integer.parseInt(getStrPages());
  }

  public String getStrInvestUSDValue() {
    if (strInvestUSDValue.get() == null) {
      return "";
    }
    return strInvestUSDValue.get();
  }

  @SuppressWarnings("unused")
  public String getStrInvestIDRValue() {
    if (strInvestIDRValue.get() == null) {
      return "";
    }
    return strInvestIDRValue.get();
  }

  @SuppressWarnings("unused")
  public String getStrInvestID() {
    if (strInvestID.get() == null) {
      return "";
    }
    return strInvestID.get();
  }

  @SuppressWarnings("unused")
  public String getStrPages() {
    if (strPages.get() == null) {
      return "";
    }
    return strPages.get();
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

  private void getFunds() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.fundsRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getFunds) {
              @Override
              protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                if (jsonElementResponse.body() != null) {
                  loading(false);
                  readFundsJSON(jsonElementResponse.body());
                }
              }
            });
    compositeDisposable.add(disposable);
  }

  private void getSecondFunds(String pages) {
    loading(true);

//    Disposable disposable = ServiceGenerator.service.fundsSecondRequest(PAGES_SecondFunds)
    Disposable disposable = ServiceGenerator.service.fundsSecondRequest(pages)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getSecondFunds) {
            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, () -> getSecondFunds(pages)) {
              @Override
              protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                if (jsonElementResponse.body() != null) {
                  loading(false);
                  readSecondFundsJSON(jsonElementResponse.body());
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
        JSONObject objUser = objectUser.getJSONObject(i + "");

        CurrentData currentData = new CurrentData(
            objUser.getString("Date"),
            objUser.getString("UserID"),
            objUser.getString("Name"),
            objUser.getString("Invest"),
            objUser.getString("StatusID"),
            objUser.getString("Status"),
            objUser.getString("WdID")
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

  private void readFundsJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      FundsRes fundsRes;

      jsonObject = new JSONObject(response.toString());
      pageFirst = jsonObject.getInt("Page");
      pagesTotal = jsonObject.getString("Pages");
      Log.d("Debug", "this page = "+pageFirst+" || pages = "+pagesTotal);
//      Toast.makeText(getContext(), "page = "+page+" || pages = "+pages, Toast.LENGTH_SHORT).show();

      JSONObject objectFunds = jsonObject.getJSONObject("Funds");

      List<Funds> fundsList = new ArrayList<>();

      for (int i = 1; i <= objectFunds.length(); i++) {
        JSONObject objFunds = objectFunds.getJSONObject(i + "");
        investId = objFunds.getString("ID");
        investSlot = objFunds.getString("Slots");
        investIDRValue = objFunds.getString("IDR_Value");

        Funds funds;
        Meta meta;

        JSONObject metaObject = objFunds.getJSONObject("Meta");

        meta = new Meta(
                metaObject.getString("AccNo"),
                metaObject.getString("InvestorPass"),
                metaObject.getString("Server")
        );

        funds = new Funds(
                objFunds.getString("ID"),
                objFunds.getString("Name"),
                objFunds.getString("Type"),
                objFunds.getString("Manager"),
                objFunds.getString("Invested"),
                objFunds.getString("Equity"),
                objFunds.getString("Slots"),
                objFunds.getString("Compounding"),
                objFunds.getString("ROI"),
                meta, objFunds.getString("IDR_Value")
        );

        fundsList.add(funds);
      }

      fundsRes = new FundsRes(fundsList);

      showFunds(fundsRes);
//      getSecondFunds();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void readSecondFundsJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      FundsRes fundsRes;

      jsonObject = new JSONObject(response.toString());
      page = jsonObject.getInt("Page");
      pages = jsonObject.getString("Pages");
      Log.d("Debug", "this page = "+page+" || pages = "+pages);
//      Toast.makeText(getContext(), "page = "+page+" || pages = "+pages, Toast.LENGTH_SHORT).show();

      JSONObject objectFunds = jsonObject.getJSONObject("Funds");

      List<Funds> fundsList = new ArrayList<>();

      for (int i = 1; i <= objectFunds.length(); i++) {
        JSONObject objFunds = objectFunds.getJSONObject(i + "");
        investId = objFunds.getString("ID");
        investSlot = objFunds.getString("Slots");
        investIDRValue = objFunds.getString("IDR_Value");

        Funds funds;
        Meta meta;

        JSONObject metaObject = objFunds.getJSONObject("Meta");

        meta = new Meta(
                metaObject.getString("AccNo"),
                metaObject.getString("InvestorPass"),
                metaObject.getString("Server")
        );

        funds = new Funds(
                objFunds.getString("ID"),
                objFunds.getString("Name"),
                objFunds.getString("Type"),
                objFunds.getString("Manager"),
                objFunds.getString("Invested"),
                objFunds.getString("Equity"),
                objFunds.getString("Slots"),
                objFunds.getString("Compounding"),
                objFunds.getString("ROI"),
                meta, objFunds.getString("IDR_Value")
        );

        fundsList.add(funds);
      }

      fundsRes = new FundsRes(fundsList);

      showSecondFunds(fundsRes);
    } catch (JSONException e) {
      e.printStackTrace();
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

//    fundsNameLabelTx.set(investRes.getFunds().getName());
//    fundsTypeValueTx.set(investRes.getFunds().getTypeManager());
//    fundsYouInvestValueTx.set(investRes.getFunds().getInvested());
//    fundsEquityProgressValueTx.set(investRes.getFunds().getEquity());
//    fundsSlotsValueTx.set(investRes.getFunds().getSlots());
//    fundsRoiValueTx.set(investRes.getFunds().getROI());
//    fundsCompoundingValueTx.set(investRes.getFunds().getCompounding());
//    fundsCcNoValueTx.set(investRes.getFunds().getMeta().getAccNo());
//    fundsInvestorPassValueTx.set(investRes.getFunds().getMeta().getInvestorPass());
//    fundsServerValueTx.set(investRes.getFunds().getMeta().getServer());
    fundsUSDIDRValueTx.set(investRes.getFunds().getUSDIDR());
    fundsBankNameValueTx.set(investRes.getFunds().getBankFundInvest().getName());
    fundsBankAccNameValueTx.set(investRes.getFunds().getBankFundInvest().getAccName());
    fundsBankAccNoValueTx.set(investRes.getFunds().getBankFundInvest().getAccNo());

    fundsUserDateValueTx.set(investRes.getUser().get(0).getDateText());
    fundsUserIDValueTx.set(investRes.getUser().get(0).getUserIDText());
    fundsUserNameValueTx.set(investRes.getUser().get(0).getNameText());
    fundsUserInvestValueTx.set(investRes.getUser().get(0).getInvestText());
    fundsUserStatusIDValueTx.set(investRes.getUser().get(0).getUserIDValueText());
    fundsUserStatusValueTx.set(investRes.getUser().get(0).getStatusText());
    fundsUserWdIDValueTx.set(investRes.getUser().get(0).getWdIDText());

    previousDate.set(investRes.getParticipant().getParticipantInvestPrevious().getDateText());
    previousInvest.set(investRes.getParticipant().getParticipantInvestPrevious().getInvestText());

    participantAdapter.setModels(
        investRes.getParticipant().getParticipantInvestCurrent().getCurrent());
    participantAdapter.notifyDataSetChanged();
  }

  private void showFunds(FundsRes fundsRes) {
    hideLoading();

    if (fundsRes == null) return;

    fundsNameLabelTx.set(fundsRes.getFunds().get(0).getName());
    fundsTypeValueTx.set(fundsRes.getFunds().get(0).getTypeManager());
    fundsEquityProgressValueTx.set(fundsRes.getFunds().get(0).getEquity());
    fundsSlotsValueTx.set(fundsRes.getFunds().get(0).getSlots());
    fundsRoiValueTx.set(fundsRes.getFunds().get(0).getROI());
    fundsCompoundingValueTx.set(fundsRes.getFunds().get(0).getCompounding());
    fundsYouInvestValueTx.set(fundsRes.getFunds().get(0).getInvested());
    fundsCcNoValueTx.set(fundsRes.getFunds().get(0).getMeta().getAccNo());
    fundsInvestorPassValueTx.set(fundsRes.getFunds().get(0).getMeta().getInvestorPass());
    fundsServerValueTx.set(fundsRes.getFunds().get(0).getMeta().getServer());
  }

  private void showSecondFunds(FundsRes fundsRes) {
    hideLoading();

    if (fundsRes == null) return;

    fundsSecondNameLabelTx.set(fundsRes.getFunds().get(0).getName());
    fundsSecondTypeValueTx.set(fundsRes.getFunds().get(0).getTypeManager());
    fundsSecondEquityProgressValueTx.set(fundsRes.getFunds().get(0).getEquity());
    fundsSecondSlotsValueTx.set(fundsRes.getFunds().get(0).getSlots());
    fundsSecondRoiValueTx.set(fundsRes.getFunds().get(0).getROI());
    fundsSecondCompoundingValueTx.set(fundsRes.getFunds().get(0).getCompounding());
    fundsSecondYouInvestValueTx.set(fundsRes.getFunds().get(0).getInvested());
    fundsSecondCcNoValueTx.set(fundsRes.getFunds().get(0).getMeta().getAccNo());
    fundsSecondInvestorPassValueTx.set(fundsRes.getFunds().get(0).getMeta().getInvestorPass());
    fundsSecondServerValueTx.set(fundsRes.getFunds().get(0).getMeta().getServer());
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

  @SuppressWarnings("unused")
  public void onButtonInvestClick(View view) {
    Intent intent = new Intent(context, InvestFundsActivity.class);
    Toast.makeText(getContext(), "Click Invest Button", Toast.LENGTH_SHORT).show();
    intent.putExtra("investSlot", getStrInvestUSDValue());
    intent.putExtra("investIDRValue", getStrInvestIDRValue());
    intent.putExtra("investId", String.valueOf(pageFirst));
    Log.d("Debug", "2nd Invest - USD "
            + getStrInvestUSDValue()
            + " / "
            + " IDR "
            + getStrInvestIDRValue()
            + " || "
            + " ID For Invest "
            + pageFirst);
    Activity activity = (Activity) context;
    activity.startActivityForResult(intent, FUND_MENU);
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Invest model) {

  }
}
