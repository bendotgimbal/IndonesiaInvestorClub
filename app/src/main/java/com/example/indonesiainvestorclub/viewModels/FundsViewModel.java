package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.databinding.ObservableBoolean;
import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Funds;
import com.example.indonesiainvestorclub.models.Meta;
import com.example.indonesiainvestorclub.models.Metalist;
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
      FundsRes fundsRes = new FundsRes();

      jsonObject = new JSONObject(response.toString());
      JSONObject objectFunds = jsonObject.getJSONObject("Funds");

      List<Funds> funds = new ArrayList<>();
      List<Meta> meta = new ArrayList<>();
      List<Metalist> metalist = new ArrayList<>();

      for (int i = 1; i <= objectFunds.length(); i++) {
        JSONObject objFunds = objectFunds.getJSONObject(i + "");
        Meta fundslist;
        Datas data;

        String name = objFunds.getString("Name");
        String type = objFunds.getString("Type");
        String manager = objFunds.getString("Manager");
        String invested = objFunds.getString("Invested");
        String equity = objFunds.getString("Equity");
        String slots = objFunds.getString("Slots");
        String compounding = objFunds.getString("Compounding");
        String roi = objFunds.getString("ROI");
        JSONObject metaObject = objFunds.getJSONObject("Meta");

        for (int o = 1; o <= metaObject.length(); o++) {
          JSONObject objMeta = metaObject.getJSONObject(o + "");

          Metalist metaList = new Metalist(
              objMeta.getString("AccNo"),
              objMeta.getString("InvestorPass"),
              objMeta.getString("Server")
          );
          metalist.add(metaList);
        }
        fundslist = new Meta(metalist);
        meta.add(fundslist);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
