package com.example.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.PerformanceActivityBinding;
import com.example.indonesiainvestorclub.viewModels.PerformanceViewModel;

public class PerformanceActivity extends BaseActivity {

    private PerformanceActivityBinding binding;
    private PerformanceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.performance_activity);
        viewModel = new PerformanceViewModel(this, binding);
        binding.setViewModel(viewModel);

    }
}