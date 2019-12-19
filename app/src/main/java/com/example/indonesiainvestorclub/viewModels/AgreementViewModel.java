package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import com.example.indonesiainvestorclub.databinding.AgreementFragmentBinding;
import com.example.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.example.indonesiainvestorclub.models.Agreement;
import com.example.indonesiainvestorclub.models.response.AgreementRes;
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

public class AgreementViewModel extends BaseViewModelWithCallback {

    private AgreementFragmentBinding binding;

    public AgreementViewModel(Context context, AgreementFragmentBinding binding) {
        super(context);
        this.binding = binding;

        getAgreement();
    }

    //API CALL
    private void getAgreement() {
        Disposable disposable = ServiceGenerator.service.agreementRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getAgreement) {
                    @Override
                    protected void onSuccess(Response<JsonElement> response) {
                        if (response.body() != null) {
                            readAgreementJSON(response.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readAgreementJSON(JsonElement response) {
        JSONObject jsonObject;
        try {
            AgreementRes agreementRes = new AgreementRes();

            jsonObject = new JSONObject(response.toString());
            JSONObject objectAgreement = jsonObject.getJSONObject("IIC_MUTUAL_FUND_AGREEMENT");

            List<Agreement> agreement = new ArrayList<>();

            for (int i = 1; i <= objectAgreement.length(); i++) {
                JSONObject objAgreement = objectAgreement.getJSONObject(i + "");

                String parent = objAgreement.getString("Parent");
                JSONObject childsObject = objAgreement.getJSONObject("Childs");
                for (int o = 1; o <= childsObject.length(); o++) {
                    JSONObject objChilds = childsObject.getJSONObject(o + "");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override public void hideLoading() {

    }
}
