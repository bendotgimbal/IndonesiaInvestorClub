package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import com.example.indonesiainvestorclub.databinding.PortofolioActivityBinding;

public class PortofolioViewModel extends BaseViewModelWithCallback {

    private PortofolioActivityBinding binding;

    public PortofolioViewModel(Context context, PortofolioActivityBinding binding){
        super(context);
        this.binding = binding;
    }
}
