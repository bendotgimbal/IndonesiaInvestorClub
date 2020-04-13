package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.InvestFundsActivityBinding;
import com.project.indonesiainvestorclub.viewModels.InvestFundsViewModel;

public class InvestFundsActivity extends BaseActivity {

    private InvestFundsActivityBinding binding;
    private InvestFundsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("investSlot") && getIntent().hasExtra("investIDRValue") && getIntent().hasExtra("investId")) {
            viewModel.start(getIntent().getStringExtra("investSlot"), getIntent().getStringExtra("investIDRValue"), getIntent().getStringExtra("investId"));
        }
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.invest_funds_activity);
        viewModel = new InvestFundsViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

}