package com.example.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ProfileActivityBinding;
import com.example.indonesiainvestorclub.viewModels.ProfileViewModel;

public class ProfileActivity extends BaseActivity {

    private ProfileActivityBinding binding;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        viewModel = new ProfileViewModel(this, binding);
        binding.setViewModel(viewModel);
    }
}
