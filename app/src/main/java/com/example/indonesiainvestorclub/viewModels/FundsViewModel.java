package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Funds;
import com.example.indonesiainvestorclub.models.Meta;
import com.example.indonesiainvestorclub.models.response.FundsRes;
import com.example.indonesiainvestorclub.services.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FundsViewModel extends ViewModel {

  private FundsFragmentBinding binding;
  private CompositeDisposable compositeDisposable;
  private MutableLiveData<String> mText;

  public FundsViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is Funds fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }

  //API CALL
  private void getFunds() {
    Disposable disposable = ServiceGenerator.service.fundsRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onSuccess);
    compositeDisposable.add(disposable);
  }

  protected void onSuccess(Response<FundsRes> response) {
    if (response.body() != null) {
      readFundsJSON(response.body());
    }
  }


  private void readFundsJSON(FundsRes response){
    JSONObject jsonObject;
    try {
      FundsRes fundsRes = new FundsRes();

      jsonObject = new JSONObject(response.toString());
      JSONObject objectFunds = jsonObject.getJSONObject("Funds");

      List<Funds> fundslist = new ArrayList<>();
      List<Meta> metalist = new ArrayList<>();

      for (int i = 1; i <= objectFunds.length(); i++) {
        JSONObject objFunds = objectFunds.getJSONObject(i + "");
        Funds funds;
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

          Meta meta = new Meta(
                  objMeta.getString("AccNo"),
                  objMeta.getString("InvestorPass"),
                  objMeta.getString("Server")
          );
          metalist.add(meta);
        }

      }

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
