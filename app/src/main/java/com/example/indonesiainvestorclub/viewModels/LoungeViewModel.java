package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.databinding.ObservableBoolean;
import com.example.indonesiainvestorclub.databinding.LoungeFragmentBinding;
import com.example.indonesiainvestorclub.models.Charts;
import com.example.indonesiainvestorclub.models.Tweets;
import com.example.indonesiainvestorclub.models.response.LoungeRes;
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

public class LoungeViewModel extends BaseViewModelWithCallback {

  private LoungeFragmentBinding binding;
  public ObservableBoolean loadingState;

  public LoungeViewModel(Context context, LoungeFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);

    start();
  }

  private void start() {
    getLounge();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void getLounge() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.loungeRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getLounge) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
              loading(false);
              readLoungeJSON(jsonElementResponse.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readLoungeJSON(JsonElement response) {
    JSONObject jsonObjectTweets;
    JSONObject jsonObjectCharts;
    try {
      LoungeRes loungeRes = new LoungeRes();

      jsonObjectTweets = new JSONObject(response.toString());
      JSONObject objectTweets = jsonObjectTweets.getJSONObject("Tweets");

      List<Tweets> tweetslist = new ArrayList<>();
      for (int i = 1; i <= objectTweets.length(); i++) {
        JSONObject objTweets = objectTweets.getJSONObject(i + "");

        Tweets tweets = new Tweets(
            objTweets.getString("Author"),
            objTweets.getString("Content"),
            objTweets.getString("Date")
        );
        tweetslist.add(tweets);
      }
      loungeRes.setTweets(tweetslist);

      jsonObjectCharts = new JSONObject(response.toString());
      JSONObject objectCharts = jsonObjectCharts.getJSONObject("Charts");

      List<Charts> chartslist = new ArrayList<>();
      for (int i = 1; i <= objectCharts.length(); i++) {
        JSONObject objCharts = objectCharts.getJSONObject(i + "");

        Charts charts = new Charts(
            objCharts.getString("Name"),
            objCharts.getString("Invested"),
            objCharts.getString("Profit"),
            objCharts.getString("ROI")
        );
        chartslist.add(charts);
      }
      loungeRes.setCharts(chartslist);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
