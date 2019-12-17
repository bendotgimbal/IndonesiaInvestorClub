package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.LogoutActivityBinding;
import com.example.indonesiainvestorclub.viewModels.LogoutViewModel;

public class LogoutActivity extends BaseActivity {

    private LogoutActivityBinding binding;
    private LogoutViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.logout_activity);
        viewModel = new LogoutViewModel(this, binding);
        binding.setViewModel(viewModel);
    }
}
