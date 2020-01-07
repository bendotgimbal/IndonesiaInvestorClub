package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.indonesiainvestorclub.adapter.TransactionsAdapter;
import com.example.indonesiainvestorclub.databinding.TransactionsFragmentBinding;
import com.example.indonesiainvestorclub.interfaces.ActionInterface;
import com.example.indonesiainvestorclub.models.Transactions;
import com.example.indonesiainvestorclub.models.response.TransactionsRes;
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

public class TransactionsViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<Transactions> {

    private TransactionsFragmentBinding binding;
    public ObservableBoolean loadingState;
    public ObservableField<String> pageState;
    public ObservableBoolean beforeButtonVisibility;
    public ObservableBoolean nextButtonVisibility;

    private TransactionsAdapter adapter;

    private int PAGE = 1;

    public TransactionsViewModel(Context context, TransactionsFragmentBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);
        pageState = new ObservableField<>("1/1");
        beforeButtonVisibility = new ObservableBoolean(false);
        nextButtonVisibility = new ObservableBoolean(true);

        adapter = new TransactionsAdapter();
        adapter.setListener(this);

        this.binding.transactionslist.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        this.binding.transactionslist.setAdapter(adapter);

        start();
    }

    private void start() {
        getTransactions();
    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    //API CALL
    private void getTransactions() {
        loading(true);

        Disposable disposable = ServiceGenerator.service.transactionsRequest(PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getTransactions) {
                    @Override
                    protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                        if (jsonElementResponse.body() != null) {
                            readTransactionsJSON(jsonElementResponse.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readTransactionsJSON(JsonElement response) {
        JSONObject jsonObjectTransactions;
        try {
            TransactionsRes transactionsRes;

            jsonObjectTransactions = new JSONObject(response.toString());
            JSONObject objectTransactions = jsonObjectTransactions.getJSONObject("Transactions");

            List<Transactions> transactionslist = new ArrayList<>();
            for (int t = 1; t <= objectTransactions.length(); t++) {
                JSONObject objTransactions = objectTransactions.getJSONObject(t + "");

                Transactions transactions = new Transactions(
                        objTransactions.getString("Name"),
                        objTransactions.getString("InvestDate"),
                        objTransactions.getString("InvestUSD"),
                        objTransactions.getString("InvestIDR"),
                        objTransactions.getString("DPDate"),
                        objTransactions.getString("DPAmount"),
                        objTransactions.getString("DPID"),
                        objTransactions.getString("DPStatus"),
                        objTransactions.getString("DPProof"),
                        objTransactions.getString("StartDate"),
                        objTransactions.getString("EndDate"),
                        objTransactions.getString("Status"),
                        objTransactions.getString("WDDate"),
                        objTransactions.getString("WDAmount"),
                        objTransactions.getString("WDID"),
                        objTransactions.getString("WDStatus")
                );
                transactionslist.add(transactions);
            }

            int page = jsonObjectTransactions.getInt("Page");
            int pages = jsonObjectTransactions.getInt("Pages");

            transactionsRes = new TransactionsRes(page, pages, transactionslist);
            showTransactions(transactionsRes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showTransactions(TransactionsRes transactionsRes) {
        hideLoading();

        if (transactionsRes == null) return;

        adapter.setModels(transactionsRes.getTransactions());
        adapter.notifyDataSetChanged();

        pageState.set(transactionsRes.getPage() + " / " + transactionsRes.getPages());

        toogleButton(transactionsRes.getPages());
    }

    @SuppressWarnings("unused")
    public void onButtonBeforeClick(View view) {
        PAGE--;
        getTransactions();
    }

    @SuppressWarnings("unused")
    public void onButtonNextClick(View view) {
        PAGE++;
        getTransactions();
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
        }
    }

    @Override
    public void hideLoading() {
        loading(false);
    }

    @Override public void onClickAdapterItem(int index, Transactions model) {

    }
}
