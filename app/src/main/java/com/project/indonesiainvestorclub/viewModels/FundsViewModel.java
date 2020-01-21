package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.project.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Funds;
import com.project.indonesiainvestorclub.models.Meta;
import com.project.indonesiainvestorclub.models.response.FundsRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.InvestActivity;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FundsViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Funds>{

    private FundsFragmentBinding binding;
    public ObservableBoolean loadingState;
    public ObservableField<String> fundsIDValueTx;
    public ObservableField<String> fundsNameLabelTx;
    public ObservableField<String> fundsTypeValueTx;
    public ObservableField<String> fundsManagerValueTx;
    public ObservableField<String> fundsEquityProgressValueTx;
    public ObservableField<String> fundsSlotsValueTx;
    public ObservableField<String> fundsRoiValueTx;
    public ObservableField<String> fundsCompoundingValueTx;
    public ObservableField<String> fundsYouInvestValueTx;
    public ObservableField<String> fundsCcNoValueTx;
    public ObservableField<String> fundsInvestorPassValueTx;
    public ObservableField<String> fundsServerValueTx;

    private String investId;

    public FundsViewModel(Context context, FundsFragmentBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);
//        fundsIDValueTx = new ObservableField<>("");
        fundsNameLabelTx = new ObservableField<>("");
        fundsTypeValueTx = new ObservableField<>("");
        fundsManagerValueTx = new ObservableField<>("");
        fundsEquityProgressValueTx = new ObservableField<>("");
        fundsSlotsValueTx = new ObservableField<>("");
        fundsRoiValueTx = new ObservableField<>("");
        fundsCompoundingValueTx = new ObservableField<>("");
        fundsYouInvestValueTx = new ObservableField<>("");
        fundsCcNoValueTx = new ObservableField<>("");
        fundsInvestorPassValueTx = new ObservableField<>("");
        fundsServerValueTx = new ObservableField<>("");

        start();
    }

    private void start() {
        getFunds();
    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    //API CALL
    private void getFunds() {
        loading(true);

        Disposable disposable = ServiceGenerator.service.fundsRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getFunds) {
                    @Override
                    protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                        if (jsonElementResponse.body() != null) {
                            loading(false);
                            readFundsJSON(jsonElementResponse.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readFundsJSON(JsonElement response) {
        JSONObject jsonObject;
        try {
            FundsRes fundsRes;

            jsonObject = new JSONObject(response.toString());
            JSONObject objectFunds = jsonObject.getJSONObject("Funds");

            List<Funds> fundsList = new ArrayList<>();
            List<Meta> metaList = new ArrayList<>();

            for (int i = 1; i <= objectFunds.length(); i++) {
                JSONObject objFunds = objectFunds.getJSONObject(i + "");
                investId = objFunds.getString("ID");
                Toast.makeText(context, "ID "+investId, Toast.LENGTH_SHORT).show();
                Funds funds;
                Meta meta;

                JSONObject metaObject = objFunds.getJSONObject("Meta");
                meta = new Meta(
                        metaObject.getString("AccNo"),
                        metaObject.getString("InvestorPass"),
                        metaObject.getString("Server")
                );

                metaList.add(meta);

                funds = new Funds(
                        objFunds.getString("ID"),
                        objFunds.getString("Name"),
                        objFunds.getString("Type"),
                        objFunds.getString("Manager"),
                        objFunds.getString("Invested"),
                        objFunds.getString("Equity"),
                        objFunds.getString("Slots"),
                        objFunds.getString("Compounding"),
                        objFunds.getString("ROI"),
                        meta
                );

                fundsList.add(funds);
            }

            fundsRes = new FundsRes(fundsList, metaList);

            showFunds(fundsRes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showFunds(FundsRes fundsRes) {
        hideLoading();
        if (fundsRes == null) return;

//        fundsIDValueTx.set(fundsRes.getFunds().get(0).getID());
        fundsNameLabelTx.set(fundsRes.getFunds().get(0).getName());
        fundsTypeValueTx.set(fundsRes.getFunds().get(0).getType());
        fundsManagerValueTx.set(fundsRes.getFunds().get(0).getManager());
        fundsEquityProgressValueTx.set(fundsRes.getFunds().get(0).getEquity());
        fundsSlotsValueTx.set(fundsRes.getFunds().get(0).getSlots());
        fundsRoiValueTx.set(fundsRes.getFunds().get(0).getROI());
        fundsCompoundingValueTx.set(fundsRes.getFunds().get(0).getCompounding());
        fundsYouInvestValueTx.set(fundsRes.getFunds().get(0).getInvested());
        fundsCcNoValueTx.set(fundsRes.getMeta().get(0).getAccNo());
        fundsInvestorPassValueTx.set(fundsRes.getMeta().get(0).getInvestorPass());
        fundsServerValueTx.set(fundsRes.getMeta().get(0).getServer());

        //TODO recyclerview
    }

    @SuppressWarnings("unused")
    public void onButtonMoreInfoClick(View view) {
//        Toast.makeText(context, "MORE INFO Click "+investId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, InvestActivity.class);
        intent.putExtra("investId", investId);
        context.startActivity(intent);
    }

    @Override
    public void hideLoading() {
        loading(false);
    }

    @Override public void onClickAdapterItem(int index, Funds model) {

    }
}