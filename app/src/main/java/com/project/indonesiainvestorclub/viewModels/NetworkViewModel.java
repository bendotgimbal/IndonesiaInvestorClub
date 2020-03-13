package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.indonesiainvestorclub.adapter.CommissionsAdapter;
import com.project.indonesiainvestorclub.databinding.NetworkFragmentBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Commissions;
import com.project.indonesiainvestorclub.models.Groups;
import com.project.indonesiainvestorclub.models.Network;
import com.project.indonesiainvestorclub.models.NetworkData;
import com.project.indonesiainvestorclub.models.response.NetworkRes;
import com.project.indonesiainvestorclub.models.response.NetworkResNew;
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

public class NetworkViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Commissions>{

  private NetworkFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;
  public ObservableBoolean networkListVisibility;

  private int PAGE = 1;

  private CommissionsAdapter adapter;

  public NetworkViewModel(Context context, NetworkFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);
    networkListVisibility = new ObservableBoolean(false);

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

    Disposable disposable = ServiceGenerator.service.networkRequest()
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
//    JSONObject jsonObjectNetwork;
    JSONObject jsonObjectNetworkNew;
    try {
//      NetworkRes networkRes;
//
//      jsonObjectNetwork = new JSONObject(response.toString());
//      JSONObject objectNetwork = jsonObjectNetwork.getJSONObject("Commissions");
//
//      int page = jsonObjectNetwork.getInt("Page");
//      int pages = jsonObjectNetwork.getInt("Pages");
//
//      List<Commissions> commissionslist = new ArrayList<>();
//      for (int t = 1; t <= objectNetwork.length(); t++) {
//        JSONObject objNetwork = objectNetwork.getJSONObject(t + "");
//        Commissions commissions;
//
//        commissions = new Commissions(
//                objNetwork.getString("Date"),
//                objNetwork.getString("PerLots(USD)"),
//                objNetwork.getString("Invest(USD)"),
//                objNetwork.getString("Commission(USD)"),
//                objNetwork.getString("Invest(IDR)"),
//                objNetwork.getString("Commission(IDR)"),
//                objNetwork.getString("USDIDR")
//        );
//        commissionslist.add(commissions);
//        networkRes = new NetworkRes(page, pages, commissionslist);
//        showNetwork(networkRes);
//      }

      NetworkResNew networkResNew;
//      Network network;
//      NetworkData networkData;

      jsonObjectNetworkNew = new JSONObject(response.toString());
      JSONObject objectNetworkNew = jsonObjectNetworkNew.getJSONObject("Networks");
      String networkId = objectNetworkNew.getString("ID");
      Log.d("Debug", "Network ID " + networkId);
      List<NetworkData> networkDataList = new ArrayList<>();

      JSONObject groupObj = objectNetworkNew.getJSONObject("Data");
      for (int i = 1; i <= groupObj.length(); i++) {
        NetworkData loop = new NetworkData();
        loop.setPhrase(groupObj.getString(i + ""));

        networkDataList.add(loop);
      }

      Network network = new Network(
              objectNetworkNew.getString("ID"),
              objectNetworkNew.getString("UplineID"),
              objectNetworkNew.getString("Name"),
              networkDataList,
              objectNetworkNew.getString("Commission(USD)"),
              objectNetworkNew.getString("Commission(IDR)")
      );

      networkResNew = new NetworkResNew(network);
      showNetworkNew(networkResNew);
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

  private void showNetworkNew(NetworkResNew networkResNew) {
    hideLoading();

    if (networkResNew == null) return;

    String strNetworkID = networkResNew.getNetwork().getID();
    String strNetworkUplineID = networkResNew.getNetwork().getUplineID();
    String strNetworkName = networkResNew.getNetwork().getName();
    String strNetworkData = networkResNew.getNetwork().getNetworkData().get(0).getPhrase();
    String strNetworkCommissionUSD = networkResNew.getNetwork().getCommissionUSD();
    String strNetworkCommissionIDR = networkResNew.getNetwork().getCommissionIDR();
    Log.d("Debug", "Network --> ID = "+ strNetworkID +" || Upline ID = "+ strNetworkUplineID +" || Name = "+ strNetworkName
            +" || Data = "+ strNetworkData+" || Commission(USD) = "+ strNetworkCommissionUSD+" || Commission(IDR) = "+ strNetworkCommissionIDR);

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

  public void onClickNetworkListHideShow(View view) {

  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Commissions model) {

  }
}
