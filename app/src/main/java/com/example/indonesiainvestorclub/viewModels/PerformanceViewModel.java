package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.indonesiainvestorclub.databinding.PerformanceActivityBinding;
import com.example.indonesiainvestorclub.models.response.Performance;

public class PerformanceViewModel extends BaseViewModelWithCallback {

    private PerformanceActivityBinding binding;
    private LiveData<Performance> performanceResponseLiveData;

    public PerformanceViewModel(Context context, PerformanceActivityBinding binding) {
        super(context);
        this.binding = binding;
    }

    public LiveData<Performance> getPerformanceResponseLiveData() {
        return performanceResponseLiveData;
    }
}
