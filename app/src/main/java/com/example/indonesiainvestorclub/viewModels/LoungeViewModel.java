package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

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

    public LoungeViewModel(Context context, LoungeFragmentBinding binding) {
        super(context);
        this.binding = binding;
    }

    //API CALL
    private void getLounge() {
        Disposable disposable = ServiceGenerator.service.loungeRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getLounge) {
                    @Override
                    protected void onSuccess(Response<JsonElement> response) {
                        if (response.body() != null) {
                            readLoungeJSON(response.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readLoungeJSON(JsonElement response){
        JSONObject jsonObject;
        try {
            LoungeRes loungeRes = new LoungeRes();

            jsonObject = new JSONObject(response.toString());
            JSONObject objectTweets = jsonObject.getJSONObject("Tweets");

            List<Tweets> tweetslist = new ArrayList<>();
            for (int i = 1; i <= objectTweets.length(); i++) {
                JSONObject obj = objectTweets.getJSONObject(i + "");

                Tweets tweets = new Tweets (
                        obj.getString("Author"),
                        obj.getString("Content"),
                        obj.getString("Date")
                );
                tweetslist.add(tweets);

            }
            loungeRes.setTweets(tweetslist);

            jsonObject = new JSONObject(response.toString());
            JSONObject objectCharts = jsonObject.getJSONObject("Charts");

            List<Charts> chartslist = new ArrayList<>();
            for (int i = 1; i <= objectCharts.length(); i++) {
                JSONObject obj = objectCharts.getJSONObject(i + "");

                Charts charts = new Charts (
                        obj.getString("Name"),
                        obj.getString("Invested"),
                        obj.getString("Profit"),
                        obj.getString("ROI")
                );
                chartslist.add(charts);

            }
            loungeRes.setCharts(chartslist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
