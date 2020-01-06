package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.indonesiainvestorclub.adapter.PortfoliosAdapter;
import com.example.indonesiainvestorclub.databinding.PortfolioFragmentBinding;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Portfolios;
import com.example.indonesiainvestorclub.models.response.PortfolioRes;
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

public class PortfolioViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Portfolios>{

  private PortfolioFragmentBinding binding;
  public ObservableBoolean loadingState;

  private PortfoliosAdapter adapter;

  public PortfolioViewModel(Context context, PortfolioFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);

    adapter = new PortfoliosAdapter();
    adapter.setListener(this);

    this.binding.portfolioslist.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.portfolioslist.setAdapter(adapter);

    start();
  }

  private void start() {
    getPortfolio();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getPortfolio() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.portfolioRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getPortfolio) {
              @Override
              protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                if (jsonElementResponse.body() != null) {
                  loading(false);
                  readPortfolioJSON(jsonElementResponse.body());
                }
              }
            });
    compositeDisposable.add(disposable);
  }

  private void readPortfolioJSON(JsonElement response) {
    JSONObject jsonObjectPortofolio;
    try {
      PortfolioRes portfolioRes;

      jsonObjectPortofolio = new JSONObject(response.toString());
      JSONObject objectPortofolio = jsonObjectPortofolio.getJSONObject("Portfolios");

      List<Portfolios> portfoliolist = new ArrayList<>();
      for (int t = 1; t <= objectPortofolio.length(); t++) {
        JSONObject objPortofolio = objectPortofolio.getJSONObject(t + "");
        Portfolios portfolios;

        portfolios = new Portfolios(
                objPortofolio.getString("Date"),
                objPortofolio.getString("PerLots(USD)"),
                objPortofolio.getString("Invest(USD)"),
                objPortofolio.getString("Commission(USD)"),
                objPortofolio.getString("Invest(IDR)"),
                objPortofolio.getString("Commission(IDR)"),
                objPortofolio.getString("USDIDR")
        );
        portfoliolist.add(portfolios);
        portfolioRes = new PortfolioRes(portfoliolist);
        showPortfolio(portfolioRes);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showPortfolio(PortfolioRes portfolioRes) {
    hideLoading();

    if (portfolioRes == null) return;

    adapter.setModels(portfolioRes.getPorfolios());
    adapter.notifyDataSetChanged();

    adapter.getItemCount();
  }

  @Override
  public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Portfolios model) {

  }
}
