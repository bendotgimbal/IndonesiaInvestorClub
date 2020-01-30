package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.project.indonesiainvestorclub.databinding.ProfileEditActivityBinding;

public class ProfileEditViewModel extends BaseViewModelWithCallback {

    private ProfileEditActivityBinding binding;
    public ObservableBoolean loadingState;
    public ObservableField<String> firstNameValueTx;
    public ObservableField<String> lastNameValueTx;
    public ObservableField<String> dobValueTx;
    public ObservableField<String> maritalStatusValueTx;
    public ObservableField<String> addressValueTx;
    public ObservableField<String> postalCodeValueTx;
    public ObservableField<String> genderValueTx;
    public ObservableField<String> nationalityValueTx;
    public ObservableField<String> cityValueTx;
    public ObservableField<String> countryValueTx;
    public ObservableField<String> phoneNumberValueTx;
    public ObservableField<String> occupationValueTx;

    private String firstNameValueStr;

    public ProfileEditViewModel(Context context, ProfileEditActivityBinding binding) {
        super(context);
        this.binding = binding;

        firstNameValueTx = new ObservableField<>("");
        lastNameValueTx = new ObservableField<>("");
        dobValueTx = new ObservableField<>("");
        maritalStatusValueTx = new ObservableField<>("");
        addressValueTx = new ObservableField<>("");
        postalCodeValueTx = new ObservableField<>("");
        genderValueTx = new ObservableField<>("");
        nationalityValueTx = new ObservableField<>("");
        cityValueTx = new ObservableField<>("");
        countryValueTx = new ObservableField<>("");
        phoneNumberValueTx = new ObservableField<>("");
        occupationValueTx = new ObservableField<>("");

    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    public void start(String firstNameStr, String lastNameStr, String dobStr, String maritalStatusStr, String addressStr, String postalCodeStr, String genderStr, String nationalityStr, String cityStr, String countryStr, String phoneNumberStr, String occupationStr){
//        hideLoading();

        firstNameValueTx.set(firstNameStr);
        lastNameValueTx.set(lastNameStr);
        dobValueTx.set(dobStr);
        maritalStatusValueTx.set(maritalStatusStr);
        addressValueTx.set(addressStr);
        postalCodeValueTx.set(postalCodeStr);
        genderValueTx.set(genderStr);
        nationalityValueTx.set(nationalityStr);
        cityValueTx.set(cityStr);
        countryValueTx.set(countryStr);
        phoneNumberValueTx.set(phoneNumberStr);
        occupationValueTx.set(occupationStr);

        firstNameValueStr = firstNameStr;
        Toast.makeText(context, "First Name "+firstNameValueStr, Toast.LENGTH_SHORT).show();

    }

    @Override public void hideLoading() {
        loading(false);
    }
}