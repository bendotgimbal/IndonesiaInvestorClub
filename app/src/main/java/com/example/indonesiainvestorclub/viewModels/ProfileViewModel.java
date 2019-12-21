package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.example.indonesiainvestorclub.databinding.ProfileFragmentBinding;
import com.example.indonesiainvestorclub.helper.ImageHelper;
import com.example.indonesiainvestorclub.models.Bank;
import com.example.indonesiainvestorclub.models.Documents;
import com.example.indonesiainvestorclub.models.DocumentsBank;
import com.example.indonesiainvestorclub.models.DocumentsID;
import com.example.indonesiainvestorclub.models.Groups;
import com.example.indonesiainvestorclub.models.Login;
import com.example.indonesiainvestorclub.models.Profile;
import com.example.indonesiainvestorclub.models.response.ProfileRes;
import com.example.indonesiainvestorclub.services.CallbackWrapper;
import com.example.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModelWithCallback {

  private ProfileFragmentBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> idNumberTx;
  public ObservableField<String> refCodeTx;
  public ObservableField<String> nameTx;
  public ObservableField<String> phoneNumberTx;
  public ObservableField<String> emailTx;
  public ObservableField<String> sponsorTx;
  public ObservableField<String> networkTx;
  public ObservableField<String> grupTx;

  public ProfileViewModel(Context context, ProfileFragmentBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    idNumberTx = new ObservableField<>("");
    refCodeTx = new ObservableField<>("");
    nameTx = new ObservableField<>("");
    phoneNumberTx = new ObservableField<>("");
    emailTx = new ObservableField<>("");
    sponsorTx = new ObservableField<>("");
    networkTx = new ObservableField<>("");
    grupTx = new ObservableField<>("");

    start();
  }

  private void start() {
    getProfile();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  private void getProfile() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.profileRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getProfile) {
          @Override
          protected void onSuccess(Response<JsonElement> jsonElementResponse) {
            if (jsonElementResponse.body() != null) {
              loading(false);
              readProfileJSON(jsonElementResponse.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readProfileJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      ProfileRes profileRes;

      jsonObject = new JSONObject(response.toString());

      //LOGIN
      JSONObject loginObj = jsonObject.getJSONObject("Login");
      List<Groups> groupsList = new ArrayList<>();

      JSONObject groupObj = loginObj.getJSONObject("Groups");
      for (int i = 1; i <= groupObj.length(); i++) {
        Groups loop = new Groups();
        loop.setDepartment(groupObj.getString(i + ""));

        groupsList.add(loop);
      }

      Login login = new Login(
          loginObj.getString("ID"),
          loginObj.getString("RefCode"),
          loginObj.getString("e-Mail"),
          loginObj.getString("Sponsor"),
          loginObj.getString("Network"),
          loginObj.getString("Avatar"),
          groupsList
      );

      //PROFILE
      JSONObject profileObj = jsonObject.getJSONObject("Profile");
      Profile profile = new Profile(
          profileObj.getString("FirstName"),
          profileObj.getString("LastName"),
          profileObj.getString("DoB"),
          profileObj.getString("Gender"),
          profileObj.getString("MaritasStatus"),
          profileObj.getString("Nationality"),
          profileObj.getString("Address"),
          profileObj.getString("City"),
          profileObj.getString("PostalCode"),
          profileObj.getString("Country"),
          profileObj.getString("PhoneNo"),
          profileObj.getString("Occupation")
      );

      //BANK
      JSONObject bankObj = jsonObject.getJSONObject("Bank");
      Bank bank = new Bank(
          bankObj.getString("Name"),
          bankObj.getString("AccName"),
          bankObj.getString("AccNo."),
          bankObj.getString("Branch"),
          bankObj.getString("Address"),
          bankObj.getString("SwiftCode"),
          bankObj.getString("Status")
      );

      //DOCUMENTS
      JSONObject documentsObj = jsonObject.getJSONObject("Documents");

      JSONObject documentIdObj = documentsObj.getJSONObject("ID");
      String documentIdStatus = documentIdObj.getString("Status");
      String documentIdImg = documentIdObj.getString("Img");
      DocumentsID documentsID = new DocumentsID(documentIdStatus, documentIdImg);

      JSONObject documentBankObj = documentsObj.getJSONObject("Bank");
      String documentBankStatus = documentBankObj.getString("Status");
      String documentBankImg = documentBankObj.getString("Img");
      DocumentsBank documentsBank = new DocumentsBank(documentBankStatus, documentBankImg);

      Documents documents = new Documents(documentsID, documentsBank);

      //PROFILE RESPONSE
      profileRes = new ProfileRes(login, profile, bank, documents);

      //INIT PROFILE
      showProfile(profileRes);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void showProfile(ProfileRes profileRes) {
    hideLoading();

    if (profileRes == null) return;

    ImageHelper.loadImage(binding.profileImage, profileRes.getLogin().getAvatar());

    idNumberTx.set(profileRes.getLogin().getID());
    refCodeTx.set(profileRes.getLogin().getRefCode());
    nameTx.set(profileRes.getProfile().getFirstName() + " " +profileRes.getProfile().getLastName());
    phoneNumberTx.set(profileRes.getProfile().getPhoneNo());
    emailTx.set(profileRes.getLogin().getEmail());
    sponsorTx.set(profileRes.getLogin().getSponsor());
    networkTx.set(profileRes.getLogin().getNetwork());
    grupTx.set(profileRes.getLogin().getGroups().get(0).getDepartment() + "\n"
        + profileRes.getLogin().getGroups().get(1).getDepartment());
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
