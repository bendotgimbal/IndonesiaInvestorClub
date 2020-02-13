package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfIdActivityBinding;
import com.project.indonesiainvestorclub.viewModels.UpdateImageProofOfIDViewModel;

public class UpdateImageProofOfIDActivity extends BaseActivity {

    private UpdateImageProofOfIdActivityBinding binding;
    private UpdateImageProofOfIDViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_image_proof_of_id_activity);
        viewModel = new UpdateImageProofOfIDViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}