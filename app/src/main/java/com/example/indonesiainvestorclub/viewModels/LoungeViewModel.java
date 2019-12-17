package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.LoungeFragmentBinding;
import com.example.indonesiainvestorclub.helper.SharedPreferenceHelper;
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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.example.indonesiainvestorclub.helper.SharedPreferenceHelper.getToken;
import static com.example.indonesiainvestorclub.helper.SharedPreferenceHelper.getUserKey;

public class LoungeViewModel extends ViewModel {

    private LoungeFragmentBinding binding;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<String> mText;
    String token;

    public LoungeViewModel() {
        compositeDisposable = new CompositeDisposable();
        mText = new MutableLiveData<>();
//        SharedPreferenceHelper.setLogin(true);
//        SharedPreferenceHelper.setToken(getToken());
        token = getUserKey();
//        mText.setValue("This is Lounge fragment");
        mText.setValue(token);
    }

    public LiveData<String> getText() {
        getLounge();
        return mText;
    }

    //API CALL
    private void getLounge() {
//        Disposable disposable = ServiceGenerator.service.loungeRequest(token)
        Disposable disposable = ServiceGenerator.service.loungeRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess);
        compositeDisposable.add(disposable);
    }

    protected void onSuccess(Response<JsonElement> response) {
        if (response.body() != null) {
            readLoungeJSON(response.body());
        }
    }

    private void readLoungeJSON(JsonElement response){
        JSONObject jsonObjectTweets;
        JSONObject jsonObjectCharts;
        try {
            LoungeRes loungeRes = new LoungeRes();

            jsonObjectTweets = new JSONObject(response.toString());
            JSONObject objectTweets = jsonObjectTweets.getJSONObject("Tweets");

            List<Tweets> tweetslist = new ArrayList<>();
            for (int i = 1; i <= objectTweets.length(); i++) {
                JSONObject objTweets = objectTweets.getJSONObject(i + "");

                Tweets tweets = new Tweets (
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

                Charts charts = new Charts (
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
}
