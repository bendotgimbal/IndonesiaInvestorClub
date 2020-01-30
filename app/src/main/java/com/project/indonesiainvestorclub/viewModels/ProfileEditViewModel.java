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

    private String firstNameValueStr;

    public ProfileEditViewModel(Context context, ProfileEditActivityBinding binding) {
        super(context);
        this.binding = binding;

    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    public void profileEditActivity(String firstNameStr){
        hideLoading();

        firstNameValueTx.set("First Name "+firstNameStr);
        firstNameValueStr = firstNameStr;
        Toast.makeText(context, "First Name "+firstNameValueStr, Toast.LENGTH_SHORT).show();

    }

    @Override public void hideLoading() {
        loading(false);
    }
}