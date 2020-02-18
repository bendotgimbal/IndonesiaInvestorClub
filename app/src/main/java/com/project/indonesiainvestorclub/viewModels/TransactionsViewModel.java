package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.adapter.TransactionsAdapter;
import com.project.indonesiainvestorclub.databinding.TransactionsFragmentBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Transactions;
import com.project.indonesiainvestorclub.models.response.TransactionsRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TransactionsViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Transactions> {

  private TransactionsFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;
  public ObservableBoolean requestWDButtonEnable;
  public ObservableBoolean uploadBuktiTransferButtonEnable;
  public ObservableBoolean requestWDButtonColor;
  public ObservableBoolean uploadBuktiTransferButtonColor;

  private TransactionsAdapter adapter;

  private int PAGE = 1;
  private int DPSTATUS = 0;
  private int WDSTATUS = 0;

  public TransactionsViewModel(Context context, TransactionsFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);
      requestWDButtonEnable = new ObservableBoolean(false);
      uploadBuktiTransferButtonEnable = new ObservableBoolean(false);
//    requestWDButtonColor = new ObservableBoolean(Boolean.parseBoolean("#AF4CAC"));

    adapter = new TransactionsAdapter();
    adapter.setListener(this);

    this.binding.transactionslist.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.transactionslist.setAdapter(adapter);

    start();
  }

  private void start() {
    getTransactions();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getTransactions() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.transactionsRequest(PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getTransactions) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
              readTransactionsJSON(jsonElementResponse.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readTransactionsJSON(JsonElement response) {
    JSONObject jsonObjectTransactions;
    try {
      TransactionsRes transactionsRes;

      jsonObjectTransactions = new JSONObject(response.toString());
      JSONObject objectTransactions = jsonObjectTransactions.getJSONObject("Transactions");

      List<Transactions> transactionslist = new ArrayList<>();
      for (int t = 1; t <= objectTransactions.length(); t++) {
        JSONObject objTransactions = objectTransactions.getJSONObject(t + "");

        Transactions transactions = new Transactions(
            objTransactions.getString("Name"),
            objTransactions.getString("InvestDate"),
            objTransactions.getString("InvestUSD"),
            objTransactions.getString("InvestIDR"),
            objTransactions.getString("DPDate"),
            objTransactions.getString("DPAmount"),
            objTransactions.getString("DPID"),
            objTransactions.getString("DPStatus"),
            objTransactions.getString("DPProof"),
            objTransactions.getString("StartDate"),
            objTransactions.getString("EndDate"),
            objTransactions.getString("Status"),
            objTransactions.getString("WDDate"),
            objTransactions.getString("WDAmount"),
            objTransactions.getString("WDID"),
            objTransactions.getString("WDStatus")
        );
        transactionslist.add(transactions);
      }

      int page = jsonObjectTransactions.getInt("Page");
      int pages = jsonObjectTransactions.getInt("Pages");

      transactionsRes = new TransactionsRes(page, pages, transactionslist);
      showTransactions(transactionsRes);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showTransactions(TransactionsRes transactionsRes) {
    hideLoading();

    if (transactionsRes == null) return;

    adapter.setModels(transactionsRes.getTransactions());
    adapter.notifyDataSetChanged();

    pageState.set(transactionsRes.getPage() + " / " + transactionsRes.getPages());

    toogleButton(transactionsRes.getPages());
    toogleButtonWDEnable(Integer.parseInt(transactionsRes.getTransactions().get(0).getWDID()));
    toogleButtonUploadBuktiTransferEnable(
        Integer.parseInt(transactionsRes.getTransactions().get(0).getDPID()));
  }

  @SuppressWarnings("unused")
  public void onButtonBeforeClick(View view) {
    PAGE--;
    getTransactions();
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    getTransactions();
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

  private void toogleButtonWDEnable(int enableWd) {
    if (WDSTATUS == enableWd) {
      this.binding.btnRequestWD.setBackgroundColor(Color.rgb(175, 76, 168));
        requestWDButtonEnable.set(true);
                  Toast.makeText(context, "WD = 0 || " + enableWd, Toast.LENGTH_LONG).show();
//      Log.d("DEBUG", "WD = 0 || " + String.valueOf(enableWd));
    } else if (enableWd > 1) {
      this.binding.btnRequestWD.setBackgroundColor(Color.rgb(239, 220, 238));
        requestWDButtonEnable.set(false);
                  Toast.makeText(context, "WD > 0 || " + enableWd, Toast.LENGTH_LONG).show();
//      Log.d("DEBUG", "WD > 0 || " + String.valueOf(enableWd));
    } else {
      this.binding.btnRequestWD.setBackgroundColor(Color.rgb(239, 220, 238));
        requestWDButtonEnable.set(false);
      Toast.makeText(context, "WD Others || " + enableWd, Toast.LENGTH_LONG).show();
//      Log.d("DEBUG", String.valueOf(enableWd));
    }
  }

  private void toogleButtonUploadBuktiTransferEnable(int uploadValue) {
    if (DPSTATUS == uploadValue) {
      this.binding.btnUploadBuktiFransfer.setBackgroundColor(Color.rgb(175, 76, 168));
        uploadBuktiTransferButtonEnable.set(true);
                  Toast.makeText(context, "Upload = 0 || " + uploadValue, Toast.LENGTH_LONG).show();
//      Log.d("DEBUG", "Upload = 0 || " + String.valueOf(uploadValue));
    } else if (uploadValue > 1) {
      this.binding.btnUploadBuktiFransfer.setBackgroundColor(Color.rgb(239, 220, 238));
        uploadBuktiTransferButtonEnable.set(false);
                  Toast.makeText(context, "Upload > 0 || " + uploadValue, Toast.LENGTH_LONG).show();
//      Log.d("DEBUG", "Upload > 0 || " + String.valueOf(uploadValue));
    } else {
      this.binding.btnUploadBuktiFransfer.setBackgroundColor(Color.rgb(239, 220, 238));
        uploadBuktiTransferButtonEnable.set(false);
      Toast.makeText(context, "Upload Others || " + uploadValue, Toast.LENGTH_LONG).show();
//      Log.d("DEBUG", String.valueOf(uploadValue));
    }
  }

  @SuppressWarnings("unused")
  public void onButtonRequestWDClick(View view) {
    Toast.makeText(context, "Button Request WD ", Toast.LENGTH_LONG).show();
  }

  @SuppressWarnings("unused")
  public void onButtonUploadBuktiTransferClick(View view) {
    Toast.makeText(context, "Button Upload Bukti Transfer ", Toast.LENGTH_LONG).show();
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override
  public void onClickAdapterItem(int index, Transactions model) {

  }
}
