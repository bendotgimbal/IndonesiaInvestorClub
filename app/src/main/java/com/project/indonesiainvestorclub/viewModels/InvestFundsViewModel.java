package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.InvestFundsFragmentBinding;

public class InvestFundsViewModel extends BaseViewModelWithCallback {

    private InvestFundsFragmentBinding binding;
    public ObservableBoolean loadingState;

    public InvestFundsViewModel(Context context, InvestFundsFragmentBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);

    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    @Override
    public void hideLoading() {
        loading(false);
    }
}