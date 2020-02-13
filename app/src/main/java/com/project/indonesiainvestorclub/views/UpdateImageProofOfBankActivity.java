package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfBankActivityBinding;
import com.project.indonesiainvestorclub.viewModels.UpdateImageProofOfBankViewModel;

public class UpdateImageProofOfBankActivity extends BaseActivity {

    private UpdateImageProofOfBankActivityBinding binding;
    private UpdateImageProofOfBankViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_image_proof_of_bank_activity);
        viewModel = new UpdateImageProofOfBankViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}