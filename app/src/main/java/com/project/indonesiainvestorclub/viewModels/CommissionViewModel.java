package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.adapter.CommissionsAdapter;
import com.project.indonesiainvestorclub.databinding.CommissionFragmentBinding;
import com.project.indonesiainvestorclub.models.Commissions;
import com.project.indonesiainvestorclub.models.response.CommissionsRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CommissionViewModel extends BaseViewModelWithCallback {

    private CommissionFragmentBinding binding;

    public ObservableBoolean loadingState;

    public ObservableField<String> pageState;
    public ObservableInt page;
    public ObservableInt pages;
    public ObservableBoolean beforeButtonVisibility;
    public ObservableBoolean nextButtonVisibility;

    private CommissionsAdapter adapter;

    public CommissionViewModel(Context context, CommissionFragmentBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);

        pageState = new ObservableField<>("1/1");

        page = new ObservableInt(1);
        pages = new ObservableInt(1);

        beforeButtonVisibility = new ObservableBoolean(false);
        nextButtonVisibility = new ObservableBoolean(true);

        adapter = new CommissionsAdapter();

        this.binding.commissionsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        this.binding.commissionsRv.setAdapter(adapter);

        start();
    }

    private void start(){
        getCommissions();
    }

    private void getCommissions(){
        loading(true);

        Disposable disposable = ServiceGenerator.service.commissionsRequest(page.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getCommissions) {
                    @Override
                    protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                        if (jsonElementResponse.body() != null) {
                            loading(false);
                            readCommissionsJSON(jsonElementResponse.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readCommissionsJSON(JsonElement response) {
        JSONObject jsonObjectCommissions;
        try{
            CommissionsRes commissionsRes;

            jsonObjectCommissions = new JSONObject(response.toString());

            int page = jsonObjectCommissions.getInt("Page");
            int pages = jsonObjectCommissions.getInt("Pages");

            JSONObject objectCommissions = jsonObjectCommissions.getJSONObject("Commissions");
            List<Commissions> commissionsList = new ArrayList<>();

            for (int i = 1; i <= objectCommissions.length(); i++) {
                JSONObject dataCommissionsObj = objectCommissions.getJSONObject(i+"");
                Commissions commissions = new Commissions(
                        dataCommissionsObj.getString("Date"),
                        dataCommissionsObj.getString("Client"),
                        dataCommissionsObj.getString("PerLots(USD)"),
                        dataCommissionsObj.getString("Invest(USD)"),
                        dataCommissionsObj.getString("Commission(USD)"),
                        dataCommissionsObj.getString("Invest(IDR)"),
                        dataCommissionsObj.getString("Commission(IDR)"),
                        dataCommissionsObj.getString("USDIDR")
                );
                commissionsList.add(commissions);
            }

            commissionsRes = new CommissionsRes(page, pages, commissionsList);
            showData(commissionsRes);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showData(CommissionsRes response){
        hideLoading();

        page.set(response.getPage());
        pages.set(response.getPages());

        pageState.set(page.get()+"/"+pages.get());

        if (pages.get() > page.get()) nextButtonVisibility.set(true); else nextButtonVisibility.set(false);
        if (page.get() == 1) beforeButtonVisibility.set(false); else beforeButtonVisibility.set(true);

        adapter.setModels(response.getCommissionsList());
        adapter.notifyDataSetChanged();
    }

    public void onClickNext(View v){
        if (page.get() == pages.get()) return;
        page.set(page.get() + 1);
        getCommissions();
    }

    public void onClickBefore(View v){
        if (page.get() == 1) return;
        page.set(page.get() - 1);
        getCommissions();

    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    @Override
    public void hideLoading() {
        loading(false);
    }
}
