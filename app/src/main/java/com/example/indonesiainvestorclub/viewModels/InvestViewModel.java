package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.InvestActivityBinding;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Datas;
import com.example.indonesiainvestorclub.models.Invest;
import com.example.indonesiainvestorclub.models.Month;
import com.example.indonesiainvestorclub.models.response.InvestRes;
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

public class InvestViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Invest> {

    private InvestActivityBinding binding;
    public ObservableBoolean loadingState;
    public ObservableField<String> investIDValueTx;
    public ObservableField<String> investNameTx;
    public ObservableField<String> investYearTx;
    private MutableLiveData<String> mText;
    private ObservableField<String> mInvestID;
    private String ID;

    public InvestViewModel(Context context, InvestActivityBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);
        investIDValueTx = new ObservableField<>("");
        investNameTx = new ObservableField<>("");
        investYearTx = new ObservableField<>("");
    }

    private void start() {
        getInvest();
    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    //API CALL
    private void getInvest() {
        loading(true);

//        Toast.makeText(context, "Invest ID "+ID, Toast.LENGTH_SHORT).show();

        Disposable disposable = ServiceGenerator.service.investRequest(ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getInvest) {
                    @Override
                    protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                        if (jsonElementResponse.body() != null) {
                            loading(false);
                            readInvestJSON(jsonElementResponse.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readInvestJSON(JsonElement response) {
        JSONObject jsonObject;
        try {
            InvestRes investRes = new InvestRes();
            Invest invest;
            Datas data;

            jsonObject = new JSONObject(response.toString());
            JSONObject objectInvest = jsonObject.getJSONObject("Performance");

            String name = jsonObject.getString("Name");
            JSONObject datasObj = objectInvest.getJSONObject("Datas");

            List<Invest> investList = new ArrayList<>();
            List<Month> monthList = new ArrayList<>();

            for (int i = 1; i <= datasObj.length(); i++) {
                JSONObject objInvest = datasObj.getJSONObject(i + "");

                Month month = new Month(
                        objInvest.getString("YEAR"),
                        objInvest.getString("Jan"),
                        objInvest.getString("Feb"),
                        objInvest.getString("Mar"),
                        objInvest.getString("Apr"),
                        objInvest.getString("May"),
                        objInvest.getString("Jun"),
                        objInvest.getString("Jul"),
                        objInvest.getString("Aug"),
                        objInvest.getString("Sep"),
                        objInvest.getString("Oct"),
                        objInvest.getString("Nov"),
                        objInvest.getString("Dec"),
                        objInvest.getString("YTD")
                );
                monthList.add(month);
            }
            data = new Datas(monthList);

            invest = new Invest(name, data);
            investList.add(invest);

//            investRes = new InvestRes(investList);
            investRes.setInvest(investList);
            showInvest(investRes);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showInvest(InvestRes investRes) {
        hideLoading();
        if (investRes == null) return;
        investNameTx.set(investRes.getInvest().get(0).getName());
        investYearTx.set(investRes.getInvest().get(0).getData().getMonths().get(0).getYear());

        Toast.makeText(context, "Name Invest "+investNameTx, Toast.LENGTH_SHORT).show();

        //TODO recyclerview
    }

//    public InvestViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is Invest fragment");
//    }

    public LiveData<String> getText() {
        return mText;
    }

    public void investActivity(String id_invest){
//        mText = new MutableLiveData<>();
//        mText.setValue("ID "+id_invest);
        investIDValueTx.set("ID "+id_invest);
        ID = id_invest;
        Toast.makeText(context, "ID Invest "+ID, Toast.LENGTH_SHORT).show();
        getInvest();
    }

    @Override
    public void hideLoading() {
        loading(false);
    }

    @Override public void onClickAdapterItem(int index, Invest model) {

    }
}
