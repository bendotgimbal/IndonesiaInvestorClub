package com.example.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.PortofolioActivityBinding;
import com.example.indonesiainvestorclub.viewModels.PortofolioViewModel;

public class PortofolioActivity extends BaseActivity {

    private PortofolioActivityBinding binding;
    private PortofolioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.portofolio_activity);
        viewModel = new PortofolioViewModel(this, binding);
        binding.setViewModel(viewModel);

    }
}
