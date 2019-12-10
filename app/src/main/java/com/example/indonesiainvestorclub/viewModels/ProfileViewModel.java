package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import com.example.indonesiainvestorclub.databinding.ProfileActivityBinding;

public class ProfileViewModel extends BaseViewModelWithCallback {

    private ProfileActivityBinding binding;

    public ProfileViewModel(Context context, ProfileActivityBinding binding) {
        super(context);
        this.binding = binding;
    }
}
