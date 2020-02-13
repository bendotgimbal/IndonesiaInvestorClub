package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfBankActivityBinding;

public class UpdateImageProofOfBankViewModel extends BaseViewModelWithCallback {

    private UpdateImageProofOfBankActivityBinding binding;
    public ObservableBoolean loadingState;

    public UpdateImageProofOfBankViewModel(Context context, UpdateImageProofOfBankActivityBinding binding) {
        super(context);
        this.binding = binding;
        loadingState = new ObservableBoolean(false);

    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    @Override public void hideLoading() {
        loading(false);
    }
}