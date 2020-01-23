package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.project.indonesiainvestorclub.databinding.AgreementFragmentBinding;
import com.project.indonesiainvestorclub.models.Agreement;
import com.project.indonesiainvestorclub.models.Childs;
import com.project.indonesiainvestorclub.models.response.AgreementRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
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

  public ObservableBoolean loadingState;
  public ObservableField<String> agreementTx;

  public AgreementViewModel(Context context, AgreementFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    agreementTx = new ObservableField<>("");

    start();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  private void start() {
    getAgreement();
  }

  //API CALL
  private void getAgreement() {
    loading(true);

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
      AgreementRes agreementRes;

      jsonObject = new JSONObject(response.toString());
      JSONObject objectAgreement = jsonObject.getJSONObject("IIC_MUTUAL_FUND_AGREEMENT");

      List<Agreement> agreement = new ArrayList<>();

      for (int i = 1; i <= objectAgreement.length(); i++) {
        JSONObject objAgreement = objectAgreement.getJSONObject(i + "");

        String parent = objAgreement.getString("Parent");
        List<Childs> childsList = new ArrayList<>();

        if (objAgreement.has("Childs")) {
          JSONObject childsObject = objAgreement.getJSONObject("Childs");

          for (int o = 1; o <= childsObject.length(); o++) {
            Childs childs = new Childs(childsObject.getString(o + ""));
            childsList.add(childs);
          }
        } else {
          Childs childs = new Childs("");
          childsList.add(childs);
        }

        agreement.add(new Agreement(parent, childsList));
      }

      agreementRes = new AgreementRes(agreement);

      showAgreement(agreementRes);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showAgreement(AgreementRes response) {
    hideLoading();
    if (response == null) return;
    if (response.getAgreement() == null) return;
    StringBuilder agreement = new StringBuilder();

    for (int i = 0; i < response.getAgreement().size(); i++) {
      StringBuilder temp;
      temp = new StringBuilder(response.getAgreement().get(i).getParent() + "\n\n");

      if (response.getAgreement().get(i).getChilds() != null) {
        for (int o = 0; o < response.getAgreement().get(i).getChilds().size(); o++) {
          temp.append(response.getAgreement().get(i).getChilds().get(o).getPhrase()).append("\n\n");
        }
      }

      agreement.append(temp);
    }

    agreementTx.set(agreement.toString());
  }

  @Override public void hideLoading() {
    loading(false);
  }
}
