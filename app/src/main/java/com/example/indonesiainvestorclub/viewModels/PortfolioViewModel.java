package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.indonesiainvestorclub.adapter.PortfoliosAdapter;
import com.example.indonesiainvestorclub.databinding.PortfolioFragmentBinding;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Month;
import com.example.indonesiainvestorclub.models.Performance;
import com.example.indonesiainvestorclub.models.Portfolios;
import com.example.indonesiainvestorclub.models.response.PerformanceRes;
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
    implements ActionInterface.AdapterItemListener<Portfolios> {

  private static final String TAG = PortfolioViewModel.class.getCanonicalName();

  private PortfolioFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;

  private PortfoliosAdapter adapter;

  private int PAGE = 1;

  public PortfolioViewModel(Context context, PortfolioFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");
    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

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

    Disposable disposable = ServiceGenerator.service.portfolioRequest(PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getPortfolio) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
              readPerformancesJSON(jsonElementResponse.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readPerformancesJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      PerformanceRes performanceRes = new PerformanceRes();

      jsonObject = new JSONObject(response.toString());
      JSONObject object = jsonObject.getJSONObject("Performances");

      List<Performance> performances = new ArrayList<>();
      List<Datas> datasList = new ArrayList<>();
      List<Month> monthList = new ArrayList<>();

      for (int i = 1; i <= object.length(); i++) {
        JSONObject obj = object.getJSONObject(i + "");
        Performance performance;
        Datas data;

        String name = obj.getString("Name");
        JSONObject datas = obj.getJSONObject("Datas");

        for (int o = 1; o <= datas.length(); o++) {
          JSONObject obj1 = datas.getJSONObject(o + "");

          Month month = new Month(
              obj1.getString("YEAR"),
              obj1.getString("Jan"),
              obj1.getString("Feb"),
              obj1.getString("Mar"),
              obj1.getString("Apr"),
              obj1.getString("May"),
              obj1.getString("Jun"),
              obj1.getString("Jul"),
              obj1.getString("Aug"),
              obj1.getString("Sep"),
              obj1.getString("Oct"),
              obj1.getString("Nov"),
              obj1.getString("Dec"),
              obj1.getString("YTD")
          );
          monthList.add(month);
        }

        data = new Datas(monthList);
        datasList.add(data);

        performance = new Performance(name, datasList);
        performances.add(performance);
      }

      performanceRes.setPerformances(performances);
    } catch (JSONException e) {
      Log.e(TAG, e.toString());
    }

    readPortfolioJSON(response);
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

        Portfolios portfolios = new Portfolios(
            objPortofolio.getString("Date"),
            objPortofolio.getString("Invest(USD)"),
            objPortofolio.getString("Profit(USD)"),
            objPortofolio.getString("Invest(IDR)"),
            objPortofolio.getString("Profit(IDR)"),
            objPortofolio.getString("USDIDR")
        );
        portfoliolist.add(portfolios);
      }

      int page = jsonObjectPortofolio.getInt("Page");
      int pages = jsonObjectPortofolio.getInt("Pages");

      portfolioRes = new PortfolioRes(page, pages, portfoliolist);
      showPortfolio(portfolioRes);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showPortfolio(PortfolioRes portfolioRes) {
    hideLoading();

    if (portfolioRes == null) return;

    adapter.setModels(portfolioRes.getPorfolios());
    adapter.notifyDataSetChanged();

    pageState.set(portfolioRes.getPage() + " / " + portfolioRes.getPages());

    toogleButton(portfolioRes.getPages());
  }

  @SuppressWarnings("unused")
  public void onButtonBeforeClick(View view) {
    PAGE--;
    getPortfolio();
  }

  @SuppressWarnings("unused")
  public void onButtonNextClick(View view) {
    PAGE++;
    getPortfolio();
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

  @Override public void onClickAdapterItem(int index, Portfolios model) {

  }
}
