package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfIdActivityBinding;

public class UpdateImageProofOfIDViewModel extends BaseViewModelWithCallback {

    private UpdateImageProofOfIdActivityBinding binding;
    public ObservableBoolean loadingState;

    public UpdateImageProofOfIDViewModel(Context context, UpdateImageProofOfIdActivityBinding binding) {
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