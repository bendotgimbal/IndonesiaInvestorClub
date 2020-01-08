package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.indonesiainvestorclub.adapter.CommissionsAdapter;
import com.example.indonesiainvestorclub.databinding.NetworkFragmentBinding;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Commissions;
import com.example.indonesiainvestorclub.models.response.NetworkRes;
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

public class NetworkViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Commissions>{

  private NetworkFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;

  private int PAGE = 1;

  private CommissionsAdapter adapter;

  public NetworkViewModel(Context context, NetworkFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

    adapter = new CommissionsAdapter();
    adapter.setListener(this);

    this.binding.commissionslist.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.commissionslist.setAdapter(adapter);

    start();
  }

  private void start() {
    getNetwork();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getNetwork() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.networkRequest(PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getNetwork) {
              @Override
              protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                if (jsonElementResponse.body() != null) {
                  loading(false);
                  readNetworkJSON(jsonElementResponse.body());
                }
              }
            });
    compositeDisposable.add(disposable);
  }

  private void readNetworkJSON(JsonElement response) {
    JSONObject jsonObjectNetwork;
    try {
      NetworkRes networkRes;

      jsonObjectNetwork = new JSONObject(response.toString());
      JSONObject objectNetwork = jsonObjectNetwork.getJSONObject("Commissions");

      int page = jsonObjectNetwork.getInt("Page");
      int pages = jsonObjectNetwork.getInt("Pages");

      List<Commissions> commissionslist = new ArrayList<>();
      for (int t = 1; t <= objectNetwork.length(); t++) {
        JSONObject objNetwork = objectNetwork.getJSONObject(t + "");
        Commissions commissions;

        commissions = new Commissions(
                objNetwork.getString("Date"),
                objNetwork.getString("PerLots(USD)"),
                objNetwork.getString("Invest(USD)"),
                objNetwork.getString("Commission(USD)"),
                objNetwork.getString("Invest(IDR)"),
                objNetwork.getString("Commission(IDR)"),
                objNetwork.getString("USDIDR")
        );
        commissionslist.add(commissions);
        networkRes = new NetworkRes(page, pages, commissionslist);
        showNetwork(networkRes);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showNetwork(NetworkRes networkRes) {
    hideLoading();

    if (networkRes == null) return;

    adapter.setModels(networkRes.getCommissions());
    adapter.notifyDataSetChanged();

    adapter.getItemCount();

    pageState.set(networkRes.getPage() + " / " + networkRes.getPages());

    toogleButton(networkRes.getPages());
  }

  @SuppressWarnings("unused")
  public void onButtonBeforeClick(View view) {
    PAGE--;
    getNetwork();
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    getNetwork();
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

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Commissions model) {

  }
}
