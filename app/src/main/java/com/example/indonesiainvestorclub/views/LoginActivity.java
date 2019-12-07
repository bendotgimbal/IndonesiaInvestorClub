package com.example.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.LoginActivityBinding;
import com.example.indonesiainvestorclub.viewModels.LoginViewModel;

public class LoginActivity extends BaseActivity {

    private LoginActivityBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        viewModel = new LoginViewModel(this, binding);
        binding.setViewModel(viewModel);

    }
}

