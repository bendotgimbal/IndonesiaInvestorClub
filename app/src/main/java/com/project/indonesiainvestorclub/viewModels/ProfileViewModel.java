package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.project.indonesiainvestorclub.databinding.ProfileFragmentBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.models.Bank;
import com.project.indonesiainvestorclub.models.Documents;
import com.project.indonesiainvestorclub.models.DocumentsBank;
import com.project.indonesiainvestorclub.models.DocumentsID;
import com.project.indonesiainvestorclub.models.Groups;
import com.project.indonesiainvestorclub.models.Login;
import com.project.indonesiainvestorclub.models.Profile;
import com.project.indonesiainvestorclub.models.response.ProfileRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.views.ProfileEditActivity;

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
  public final ObservableBoolean loadingState;
  public final ObservableField<String> idNumberTx;
  public final ObservableField<String> refCodeTx;
  public final ObservableField<String> nameTx;
  public final ObservableField<String> phoneNumberTx;
  public final ObservableField<String> emailTx;
  public final ObservableField<String> sponsorTx;
  public final ObservableField<String> networkTx;
  public final ObservableField<String> grupTx;

  public final ObservableField<String> firstName;
  public final ObservableField<String> lastName;
  public final ObservableField<String> dob;
  public final ObservableField<String> maritalStatus;
  public final ObservableField<String> address;
  public final ObservableField<String> postalCode;
  public final ObservableField<String> gender;
  public final ObservableField<String> nationality;
  public final ObservableField<String> city;
  public final ObservableField<String> country;
  public final ObservableField<String> occupation;

    public final ObservableField<String> bankNameTx;
    public final ObservableField<String> bankAccNameTx;
    public final ObservableField<String> bankAccNoTx;
    public final ObservableField<String> bankBranchTx;
    public final ObservableField<String> bankAddressTx;
    public final ObservableField<String> bankSwiftCodeTx;
    public final ObservableField<String> bankStatusTx;

    public final ObservableField<String> documentIdStatusTx;
    public final ObservableField<String> documentBankStatusTx;

  private String firstNameStr;
  private String lastNameStr;
  private String dobStr;
  private String maritalStatusStr;
  private String addressStr;
  private String postalCodeStr;
  private String genderStr;
  private String nationalityStr;
  private String cityStr;
  private String countryStr;
  private String phoneNumberStr;
  private String occupationStr;

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

    firstName = new ObservableField<>("");
    lastName = new ObservableField<>("");
    dob = new ObservableField<>("");
    maritalStatus = new ObservableField<>("");
    address = new ObservableField<>("");
    postalCode = new ObservableField<>("");
    gender = new ObservableField<>("");
    nationality = new ObservableField<>("");
    city = new ObservableField<>("");
    country = new ObservableField<>("");
    occupation = new ObservableField<>("");

      bankNameTx = new ObservableField<>("");
      bankAccNameTx = new ObservableField<>("");
      bankAccNoTx = new ObservableField<>("");
      bankBranchTx = new ObservableField<>("");
      bankAddressTx = new ObservableField<>("");
      bankSwiftCodeTx = new ObservableField<>("");
      bankStatusTx = new ObservableField<>("");

      documentIdStatusTx = new ObservableField<>("");
      documentBankStatusTx = new ObservableField<>("");

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

      firstNameStr = profileObj.getString("FirstName");
      lastNameStr = profileObj.getString("LastName");
      dobStr = profileObj.getString("DoB");
      genderStr = profileObj.getString("Gender");
      maritalStatusStr = profileObj.getString("MaritasStatus");
      nationalityStr = profileObj.getString("Nationality");
      addressStr = profileObj.getString("Address");
      cityStr = profileObj.getString("City");
      postalCodeStr = profileObj.getString("PostalCode");
      countryStr = profileObj.getString("Country");
      phoneNumberStr = profileObj.getString("PhoneNo");
      occupationStr = profileObj.getString("Occupation");

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


    firstName.set(profileRes.getProfile().getFirstName());
    lastName.set(profileRes.getProfile().getLastName());
    dob.set(profileRes.getProfile().getDoB());
    maritalStatus.set(profileRes.getProfile().getMaritasStatus());
    address.set(profileRes.getProfile().getAddress());
    postalCode.set(profileRes.getProfile().getPostalCode());
    phoneNumberTx.set(profileRes.getProfile().getPhoneNo());
    gender.set(profileRes.getProfile().getGender());
    nationality.set(profileRes.getProfile().getNationality());
    city.set(profileRes.getProfile().getCity());
    country.set(profileRes.getProfile().getCountry());
    occupation.set(profileRes.getProfile().getOccupation());

      bankNameTx.set(profileRes.getBank().getName());
      bankAccNameTx.set(profileRes.getBank().getAccName());
      bankAccNoTx.set(profileRes.getBank().getAccNo());
      bankBranchTx.set(profileRes.getBank().getBranch());
      bankAddressTx.set(profileRes.getBank().getAddress());
      bankSwiftCodeTx.set(profileRes.getBank().getSwiftCode());
      bankStatusTx.set(profileRes.getBank().getStatus());

      documentIdStatusTx.set(profileRes.getDocuments().getDocumentID().getStatusDocumentIdTx());
      documentBankStatusTx.set(profileRes.getDocuments().getDocumentBank().getStatusDocumentBankTx());

    ImageHelper.loadImage(binding.proofImageId, profileRes.getDocuments().getDocumentID().getImg());
    ImageHelper.loadImage(binding.proofImageBank, profileRes.getDocuments().getDocumentBank().getImg());

  }

  @SuppressWarnings("unused")
  public void onButtonEditInfoPersonalClick(View view) {
    Toast.makeText(context, "Edit Button Click", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(context, ProfileEditActivity.class);
    intent.putExtra("firstNameStr", firstNameStr);
    intent.putExtra("lastNameStr", lastNameStr);
    intent.putExtra("dobStr", dobStr);
    intent.putExtra("genderStr", genderStr);
    intent.putExtra("maritalStatusStr", maritalStatusStr);
    intent.putExtra("nationalityStr", nationalityStr);
    intent.putExtra("addressStr", addressStr);
    intent.putExtra("cityStr", cityStr);
    intent.putExtra("postalCodeStr", postalCodeStr);
    intent.putExtra("countryStr", countryStr);
    intent.putExtra("phoneNumberStr", phoneNumberStr);
    intent.putExtra("occupationStr", occupationStr);
      context.startActivity(intent);
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
