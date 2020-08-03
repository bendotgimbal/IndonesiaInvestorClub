package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import com.project.indonesiainvestorclub.adapter.PerformanceAdapter;
import com.project.indonesiainvestorclub.adapter.PerformanceYearAdapter;
import com.project.indonesiainvestorclub.adapter.PortfoliosAdapter;
import com.project.indonesiainvestorclub.databinding.PortfolioFragmentBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.Month;
import com.project.indonesiainvestorclub.models.Performance;
import com.project.indonesiainvestorclub.models.Portfolios;
import com.project.indonesiainvestorclub.models.response.PerformanceRes;
import com.project.indonesiainvestorclub.models.response.PortfolioRes;
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

public class PortfolioViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Portfolios> {

  private static final String TAG = PortfolioViewModel.class.getCanonicalName();

  private PortfolioFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> pageState;
  public ObservableField<String> pageStatePerformances;

  public ObservableBoolean beforeButtonVisibility;
  public ObservableBoolean nextButtonVisibility;
  public ObservableBoolean portofolioListVisibility;

  public ObservableField<String> yearValueTv;
  public ObservableField<String> ytdValueTv;

  public ObservableField<String> percentageText;

  public ObservableField<String> yearPerformancesValueTv;
  public ObservableField<String> ytdPerformancesValueTv;

  private PortfoliosAdapter adapter;
  private PerformanceAdapter performanceAdapter;
  private PerformanceYearAdapter performanceYearAdapter;

  private int PAGE = 1;

  private int draggingView = -1;

  public PortfolioViewModel(Context context, PortfolioFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    pageState = new ObservableField<>("1/1");

    beforeButtonVisibility = new ObservableBoolean(false);
    nextButtonVisibility = new ObservableBoolean(true);

    portofolioListVisibility = new ObservableBoolean(false);

    percentageText = new ObservableField<>("YTD");

    pageStatePerformances = new ObservableField<>("1/1");

    yearValueTv = new ObservableField<>("0000");
    ytdValueTv = new ObservableField<>("0%");

    yearPerformancesValueTv = new ObservableField<>("0000");
    ytdPerformancesValueTv = new ObservableField<>("0%");

    performanceAdapter = new PerformanceAdapter();
    performanceYearAdapter = new PerformanceYearAdapter();

    adapter = new PortfoliosAdapter();
    adapter.setListener(this);

    this.binding.portfolioslist.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.portfolioslist.setAdapter(adapter);

    this.binding.tablePerformance.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.tablePerformance.setAdapter(performanceAdapter);

    this.binding.yearColumn.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.yearColumn.setAdapter(performanceYearAdapter);

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (binding.tablePerformance == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 1;
        } else if (binding.yearColumn == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 2;
        }
      }

      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (draggingView == 1 && recyclerView == binding.tablePerformance) {
          binding.yearColumn.scrollBy(dx, dy);
        } else if (draggingView == 2 && recyclerView == binding.yearColumn) {
          binding.tablePerformance.scrollBy(dx, dy);
        }
      }
    };

    this.binding.tablePerformance.addOnScrollListener(scrollListener);
    this.binding.yearColumn.addOnScrollListener(scrollListener);

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
      List<Month> monthList = new ArrayList<>();

      for (int i = 1; i <= object.length(); i++) {
        JSONObject obj = object.getJSONObject(i + "");
        Performance performance;
        Datas data;

        String name = obj.getString("Name");
        JSONObject datas = obj.getJSONObject("Datas");

        for (int o = 1; o <= datas.length(); o++) {
          JSONObject obj1 = datas.getJSONObject(o + "");

          String percentage;
          if (obj1.has("YTD")){
            percentage = obj1.getString("YTD");
            percentageText.set("YTD");
          }else {
            percentage = obj1.getString("YTO");
            percentageText.set("YTO");
          }

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
              percentage
          );
          monthList.add(month);
        }

        data = new Datas(monthList);

        performance = new Performance(name, data);
        performances.add(performance);
      }

      performanceRes.setPerformances(0, 0, performances);

      showPerformanceTable(performanceRes);
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

  private void showPerformanceTable(PerformanceRes performanceRes) {
    performanceAdapter.setModels(performanceRes.getPerformances().get(0).getData().getMonths());
    performanceYearAdapter.setModels(performanceRes.getPerformances().get(0).getData().getMonths());

    performanceAdapter.notifyDataSetChanged();
    performanceYearAdapter.notifyDataSetChanged();

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

  @SuppressWarnings("unused")
  public void onClickPerformance(View view){
    if (portofolioListVisibility.get()){
      portofolioListVisibility.set(false);
    }
  }

  @SuppressWarnings("unused")
  public void onClickOverview(View view){
    if (!portofolioListVisibility.get()){
      portofolioListVisibility.set(true);
    }
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
