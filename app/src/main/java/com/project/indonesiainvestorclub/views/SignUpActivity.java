package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.SignupActivityBinding;
import com.project.indonesiainvestorclub.viewModels.SignUpViewModel;

public class SignUpActivity extends BaseActivity {

    private SignupActivityBinding binding;
    private SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.signup_activity);
        viewModel = new SignUpViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
