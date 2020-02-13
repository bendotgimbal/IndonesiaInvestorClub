package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;
import com.project.indonesiainvestorclub.viewModels.UpdateImageProfileViewModel;

public class UpdateImageProfileActivity extends BaseActivity {

    private UpdateImageProfileActivityBinding binding;
    private UpdateImageProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_image_profile_activity);
        viewModel = new UpdateImageProfileViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}