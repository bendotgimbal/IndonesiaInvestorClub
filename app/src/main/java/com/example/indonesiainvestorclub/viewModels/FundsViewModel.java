package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.databinding.ObservableBoolean;
import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.example.indonesiainvestorclub.models.Funds;
import com.example.indonesiainvestorclub.models.Meta;
import com.example.indonesiainvestorclub.models.response.FundsRes;
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
import retrofit2.Response;

public class FundsViewModel extends BaseViewModelWithCallback {

  private FundsFragmentBinding binding;
  public ObservableBoolean loadingState;

  public FundsViewModel(Context context, FundsFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);

    start();
  }

  private void start() {
    getFunds();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
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

  private void readFundsJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      FundsRes fundsRes;

      jsonObject = new JSONObject(response.toString());
      JSONObject objectFunds = jsonObject.getJSONObject("Funds");

      List<Funds> fundsList = new ArrayList<>();

      for (int i = 1; i <= objectFunds.length(); i++) {
        JSONObject objFunds = objectFunds.getJSONObject(i + "");
        Funds funds;

        JSONObject metaObject = objFunds.getJSONObject("Meta");
        Meta meta = new Meta(
            metaObject.getString("AccNo"),
            metaObject.getString("InvestorPass"),
            metaObject.getString("Server")
        );

        funds = new Funds(
            objFunds.getString("Name"),
            objFunds.getString("Type"),
            objFunds.getString("Manager"),
            objFunds.getString("Invested"),
            objFunds.getString("Equity"),
            objFunds.getString("Slots"),
            objFunds.getString("Compounding"),
            objFunds.getString("ROI"),
            meta
        );

        fundsList.add(funds);
      }

      fundsRes = new FundsRes(fundsList);

      showFunds(fundsRes);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showFunds(FundsRes response) {
    hideLoading();
    if (response == null) return;
    if (response.getFunds() == null) return;

    //TODO recyclerview
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
