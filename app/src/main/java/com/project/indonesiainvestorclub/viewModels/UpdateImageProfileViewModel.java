package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;

public class UpdateImageProfileViewModel extends BaseViewModelWithCallback {

    private UpdateImageProfileActivityBinding binding;
    public ObservableBoolean loadingState;

    public UpdateImageProfileViewModel(Context context, UpdateImageProfileActivityBinding binding) {
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