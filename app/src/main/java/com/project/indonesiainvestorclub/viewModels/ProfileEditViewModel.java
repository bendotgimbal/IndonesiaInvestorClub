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

    private String firstNameValueStr;

    public ProfileEditViewModel(Context context, ProfileEditActivityBinding binding) {
        super(context);
        this.binding = binding;

        firstNameValueTx = new ObservableField<>("");
        lastNameValueTx = new ObservableField<>("");

    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    public void start(String firstNameStr, String lastNameStr, String dobStr, String maritalStatusStr, String addressStr, String postalCodeStr, String genderStr, String nationalityStr, String cityStr, String countryStr, String phoneNumberStr, String occupationStr){
//        hideLoading();

        firstNameValueTx.set(firstNameStr);
        lastNameValueTx.set(lastNameStr);
        firstNameValueStr = firstNameStr;
        Toast.makeText(context, "First Name "+firstNameValueStr, Toast.LENGTH_SHORT).show();

    }

    @Override public void hideLoading() {
        loading(false);
    }
}