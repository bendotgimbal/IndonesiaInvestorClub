package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.adapter.PerformanceAdapter;
import com.project.indonesiainvestorclub.adapter.PerformanceYearAdapter;
import com.project.indonesiainvestorclub.databinding.HomeFragmentBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.Month;
import com.project.indonesiainvestorclub.models.Performance;
import com.project.indonesiainvestorclub.models.response.PerformanceRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModelWithCallback
    implements ActionInterface.AdapterItemListener<Month> {

  private static final String TAG = HomeViewModel.class.getCanonicalName();

  private HomeFragmentBinding binding;
  public ObservableBoolean loadingState;

  public ObservableInt pageVisibility;
  public ObservableField<String> pageText;
  public ObservableInt page;
  public ObservableInt pages;
  public ObservableField<String> percentageText;

  public ObservableField<String> titlePerformance;
  private PerformanceAdapter performanceAdapter;
  private PerformanceYearAdapter performanceYearAdapter;

  private int draggingView = -1;

  public HomeViewModel(Context context, HomeFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    titlePerformance = new ObservableField<>("");

    pageVisibility = new ObservableInt(View.GONE);
    pageText = new ObservableField<>("");
    page = new ObservableInt(1);
    pages = new ObservableInt(1);
    percentageText = new ObservableField<>("YTD");

    performanceAdapter = new PerformanceAdapter();
    performanceYearAdapter = new PerformanceYearAdapter();

    this.binding.tablePerformance.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.tablePerformance.setAdapter(performanceAdapter);

    this.binding.tablePerformanceYear.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    this.binding.tablePerformanceYear.setAdapter(performanceYearAdapter);

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (binding.tablePerformance == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 1;
        } else if (binding.tablePerformanceYear == recyclerView
            && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          draggingView = 2;
        }
      }

      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (draggingView == 1 && recyclerView == binding.tablePerformance) {
          binding.tablePerformanceYear.scrollBy(dx, dy);
        } else if (draggingView == 2 && recyclerView == binding.tablePerformanceYear) {
          binding.tablePerformance.scrollBy(dx, dy);
        }
      }
    };

    this.binding.tablePerformance.addOnScrollListener(scrollListener);
    this.binding.tablePerformanceYear.addOnScrollListener(scrollListener);

    start();
  }

  private void start() {
    getPerformance();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getPerformance() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.performanceRequest(page.get())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getPerformance) {
          @Override
          protected void onSuccess(Response<JsonElement> response) {
            if (response.body() != null) {
              readPerformancesJSON(response.body());
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

      int page = jsonObject.getInt("Page");
      int pages = jsonObject.getInt("Pages");
      JSONObject object = jsonObject.getJSONObject("Performances");

      List<Performance> performances = new ArrayList<>();
      List<Month> monthList = new ArrayList<>();

//      for (int i = 1; i <= object.length(); i++) {
//        JSONObject obj = object.getJSONObject(i + "");
      JSONObject obj = object.getJSONObject(page +"");
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
//      }

      performanceRes.setPerformances(page, pages, performances);

      showPerformanceTable(performanceRes);

      hideLoading();
    } catch (JSONException e) {
      Log.e(TAG, e.toString());
      hideLoading();
    }
  }

  private void showPerformanceTable(PerformanceRes performanceRes) {
    if (performanceRes.getPages() > 1) {
      pageVisibility.set(View.VISIBLE);
      pageText.set(performanceRes.getPage() +"/"+ performanceRes.getPages());
      page.set(performanceRes.getPage());
      pages.set(performanceRes.getPages());
    }
    titlePerformance.set(performanceRes.getPerformances().get(0).getNameText());
    performanceAdapter.setModels(performanceRes.getPerformances().get(0).getData().getMonths());
    performanceYearAdapter.setModels(performanceRes.getPerformances().get(0).getData().getMonths());
    performanceAdapter.notifyDataSetChanged();
    performanceYearAdapter.notifyDataSetChanged();
  }

  @Override public void hideLoading() {
    loading(false);
  }

  @Override public void onClickAdapterItem(int index, Month model) {

  }

  public void onClickPrevious(View view){
    if (page.get() == 1) return;
    page.set(page.get()-1);
    getPerformance();
  }

  public void onClickNext(View view){
    if (page.get() == pages.get()) return;
    page.set(page.get()+1);
    getPerformance();
  }
}
