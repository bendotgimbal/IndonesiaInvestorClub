package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.adapter.DownlineAdapter;
import com.project.indonesiainvestorclub.databinding.SubNetworkNewItemBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Network;
import com.project.indonesiainvestorclub.models.NetworkData;
import com.project.indonesiainvestorclub.models.NetworkDownline;
import com.project.indonesiainvestorclub.models.NetworkDownlineDownline;
import com.project.indonesiainvestorclub.models.response.NetworkResNew;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

public class DownlineListItemViewModel extends BaseViewModelWithCallback {

  private SubNetworkNewItemBinding binding;
  public ObservableField<String> name;
  public ObservableField<String> data;
  public ObservableBoolean downlineActive;
  public ObservableBoolean loading;

  private NetworkDownline downline;
  private DownlineAdapter downlineAdapteradapter;
  private ActionInterface.AdapterItemListener<NetworkDownline> listener;

  public DownlineListItemViewModel(Context context, SubNetworkNewItemBinding binding, NetworkDownline downline,  ActionInterface.AdapterItemListener<NetworkDownline> listener) {
    super(context);
    this.binding = binding;
    this.downline = downline;
    this.listener = listener;

    downlineActive = new ObservableBoolean(true);
    loading = new ObservableBoolean(false);

    name = new ObservableField<>(downline.getName());
    data = new ObservableField<>(downline.getNetworkData().get(0).getPhrase());

    downlineAdapteradapter = new DownlineAdapter();

    this.binding.downline.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.downline.setAdapter(downlineAdapteradapter);
  }

  @SuppressWarnings("unused")
  public void onClickDownline(View view){
    getNetwork();
  }

  private void showLoading(){
    loading.set(true);
  }

  private void getNetwork() {
    showLoading();

    Disposable disposable = ServiceGenerator.service.networkRequest(Integer.parseInt(downline.getID()))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getNetwork) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
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

      List<NetworkDownline> downlinelist = new ArrayList<>();

      if (objectNetworkNew.has("Downline")){
        JSONObject downlineObj = objectNetworkNew.getJSONObject("Downline");
        for (int t = 1; t <= downlineObj.length(); t++) {
          JSONObject objDownline = downlineObj.getJSONObject(t + "");

          List<NetworkData> networkDataDownlineList = new ArrayList<>();
          JSONObject dataDownlineObj = objDownline.getJSONObject("Data");
          for (int i = 1; i <= dataDownlineObj.length(); i++) {
            NetworkData loop = new NetworkData();
            loop.setPhrase(dataDownlineObj.getString(i + ""));

            networkDataDownlineList.add(loop);
          }


          if (objDownline.has("Downline")){
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

        }
      }

      String commissionUSD = "";
      String commissionIDR = "";

      if (objectNetworkNew.has("Commission(USD)") && objectNetworkNew.has("Commission(IDR)")){
        commissionUSD = objectNetworkNew.getString("Commission(USD)");
        commissionIDR = objectNetworkNew.getString("Commission(IDR)");
      }

      Network network = new Network(
          objectNetworkNew.getString("ID"),
          objectNetworkNew.getString("UplineID"),
          objectNetworkNew.getString("Name"),
          networkDataList,
          commissionUSD,
          commissionIDR,
          downlinelist
      );

      networkResNew = new NetworkResNew(network);
      showNetworkNew(networkResNew);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showNetworkNew(NetworkResNew networkResNew) {
    hideLoading();

    if (networkResNew == null) return;
    if (networkResNew.getNetwork().getNetworkDownline() == null || networkResNew.getNetwork().getNetworkDownline().size() <= 0){
      downlineActive.set(false);
      return;
    }

    downlineAdapteradapter.setModels(networkResNew.getNetwork().getNetworkDownline());
    downlineAdapteradapter.notifyDataSetChanged();
  }

  @Override public void hideLoading() {
    loading.set(false);
  }

}
