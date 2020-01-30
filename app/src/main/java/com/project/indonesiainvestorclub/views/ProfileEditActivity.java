package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ProfileEditActivityBinding;
import com.project.indonesiainvestorclub.viewModels.ProfileEditViewModel;

public class ProfileEditActivity extends BaseActivity {

    private ProfileEditActivityBinding binding;
    private ProfileEditViewModel viewModel;
    String firstNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_activity);

        Bundle extras = getIntent().getExtras();
        firstNameStr = extras.getString("firstNameStr");

        viewModel.profileEditActivity(firstNameStr);

    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.profile_edit_activity);
        viewModel = new ProfileEditViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}