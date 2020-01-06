package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
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
  private MutableLiveData<String> mText;
  private int currentPage = 1;
  private int totalPages = 10;

  private CommissionsAdapter adapter;

  public NetworkViewModel(Context context, NetworkFragmentBinding binding) {
    super(context);
    this.binding = binding;

    binding.prevBtn.setEnabled(false);

    loadingState = new ObservableBoolean(false);

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
    JSONObject jsonObjectNetwork;
    try {
      NetworkRes networkRes;

      jsonObjectNetwork = new JSONObject(response.toString());
      JSONObject objectNetwork = jsonObjectNetwork.getJSONObject("Commissions");

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
        networkRes = new NetworkRes(commissionslist);
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
  }

    public void previousButton(View view) {
      currentPage -= 1;
        Toast.makeText(getContext(), "Pervious Button "+currentPage, Toast.LENGTH_SHORT).show();
      toggleButtons();
    }

    public void nextButton(View view) {
      currentPage += 1;
        Toast.makeText(getContext(), "Next Button "+currentPage, Toast.LENGTH_SHORT).show();
      toggleButtons();
    }

  private void toggleButtons() {
    //SINGLE PAGE DATA
    if (totalPages <= 1) {
      binding.prevBtn.setEnabled(false);
      binding.nextBtn.setEnabled(true);
    }
    //LAST PAGE
    else if (currentPage == totalPages) {
      binding.prevBtn.setEnabled(true);
      binding.nextBtn.setEnabled(false);
    }
    //FIRST PAGE
    else if (currentPage == 1) {
      binding.prevBtn.setEnabled(false);
      binding.nextBtn.setEnabled(true);
    }
    //SOMEWHERE IN BETWEEN
    else if (currentPage >= 1 && currentPage <= totalPages) {
      binding.prevBtn.setEnabled(true);
      binding.nextBtn.setEnabled(true);
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Commissions model) {

  }
}
