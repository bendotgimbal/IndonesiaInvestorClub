package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import com.example.indonesiainvestorclub.databinding.AgreementFragmentBinding;
import com.example.indonesiainvestorclub.models.response.AgreementRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AgreementViewModel extends BaseViewModelWithCallback {

    private AgreementFragmentBinding binding;

    public AgreementViewModel(Context context, AgreementFragmentBinding binding) {
        super(context);
        this.binding = binding;
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
            JSONObject object = jsonObject.getJSONObject("IIC MUTUAL FUND AGREEMENT");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
