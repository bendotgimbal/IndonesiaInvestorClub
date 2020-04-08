package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.adapter.CommissionsAdapter;
import com.project.indonesiainvestorclub.adapter.DownlineAdapter;
import com.project.indonesiainvestorclub.databinding.NetworkFragmentBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Commissions;
import com.project.indonesiainvestorclub.models.Network;
import com.project.indonesiainvestorclub.models.NetworkData;
import com.project.indonesiainvestorclub.models.NetworkDownline;
import com.project.indonesiainvestorclub.models.NetworkDownlineDownline;
import com.project.indonesiainvestorclub.models.response.NetworkRes;
import com.project.indonesiainvestorclub.models.response.NetworkResNew;
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

public class NetworkViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Commissions> {

  private NetworkFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> name;
  public ObservableField<String> data;
  public ObservableField<String> pageState;
  public ObservableBoolean downlineAvail;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;
  public ObservableBoolean networkListVisibility;

  private int PAGE = 1;

  private CommissionsAdapter adapter;
  private DownlineAdapter downlineAdapteradapter;

  public NetworkViewModel(Context context, NetworkFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    name = new ObservableField<>("??");
    data = new ObservableField<>("???");
    pageState = new ObservableField<>("1/1");

    downlineAvail = new ObservableBoolean(false);
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);
    networkListVisibility = new ObservableBoolean(false);

    adapter = new CommissionsAdapter();
    adapter.setListener(this);

    downlineAdapteradapter = new DownlineAdapter();
    downlineAdapteradapter.setListener(this);

    this.binding.downline.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.downline.setAdapter(adapter);

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
    JSONObject jsonObjectNetworkNew;
    try {
      NetworkResNew networkResNew;

      jsonObjectNetworkNew = new JSONObject(response.toString());
      JSONObject objectNetworkNew = jsonObjectNetworkNew.getJSONObject("Networks");
      String networkId = objectNetworkNew.getString("ID");
      Log.d("Debug", "Network ID " + networkId);
      List<NetworkData> networkDataList = new ArrayList<>();

      JSONObject dataObj = objectNetworkNew.getJSONObject("Data");
      for (int i = 1; i <= dataObj.length(); i++) {
        NetworkData loop = new NetworkData();
        loop.setPhrase(dataObj.getString(i + ""));

        networkDataList.add(loop);
      }

      JSONObject downlineObj = objectNetworkNew.getJSONObject("Downline");
      List<NetworkDownline> downlinelist = new ArrayList<>();
      for (int t = 1; t <= downlineObj.length(); t++) {
        JSONObject objDownline = downlineObj.getJSONObject(t + "");

        List<NetworkData> networkDataDownlineList = new ArrayList<>();
        JSONObject dataDownlineObj = objDownline.getJSONObject("Data");
        for (int i = 1; i <= dataDownlineObj.length(); i++) {
          NetworkData loop = new NetworkData();
          loop.setPhrase(dataDownlineObj.getString(i + ""));

          networkDataDownlineList.add(loop);
        }

        JSONObject objectDownlineDownline = objDownline.getJSONObject("Downline");
        NetworkDownlineDownline networkDownlineDownline;

        if (objectDownlineDownline.has("Commission(USD)") && objectDownlineDownline.has(
            "Commission(IDR)")) {
          networkDownlineDownline = new NetworkDownlineDownline(
              objectDownlineDownline.getString("Group"),
              objectDownlineDownline.getString("Commission(USD)"),
              objectDownlineDownline.getString("Commission(IDR)")
          );
        } else {
          networkDownlineDownline = new NetworkDownlineDownline(
              objectDownlineDownline.getString("Group"));
        }

        NetworkDownline networkDownline = new NetworkDownline(
            objDownline.getString("ID"),
            objDownline.getString("UplineID"),
            objDownline.getString("Name"),
            networkDataDownlineList,
            networkDownlineDownline
        );
        downlinelist.add(networkDownline);
      }

      Network network = new Network(
          objectNetworkNew.getString("ID"),
          objectNetworkNew.getString("UplineID"),
          objectNetworkNew.getString("Name"),
          networkDataList,
          objectNetworkNew.getString("Commission(USD)"),
          objectNetworkNew.getString("Commission(IDR)"),
          downlinelist
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

    name.set(networkResNew.getNetwork().getName());
    data.set(networkResNew.getNetwork().getNetworkData().get(0).getPhrase());

    downlineAdapteradapter.setModels(networkResNew.getNetwork().getNetworkDownline());
    downlineAdapteradapter.notifyDataSetChanged();

    if (downlineAdapteradapter.getItemCount() > 0){
      downlineAvail.set(true);
    }
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

  public void onClickNetworkListHideShow(View view) {

  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override
  public void onClickAdapterItem(int index, Commissions model) {

  }
}
