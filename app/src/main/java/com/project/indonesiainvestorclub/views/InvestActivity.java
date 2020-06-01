package com.project.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.InvestActivityBinding;
import com.project.indonesiainvestorclub.viewModels.InvestViewModel;

public class InvestActivity extends BaseActivity {

  private InvestActivityBinding binding;
  private InvestViewModel investModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    if (getIntent().hasExtra("investId")) {
//      investModel.start(getIntent().getStringExtra("investId"));
//    }
    if (getIntent().hasExtra("investSlot") && getIntent().hasExtra("investIDRValue") && getIntent().hasExtra("investId")) {
      investModel.start(getIntent().getStringExtra("investSlot"), getIntent().getStringExtra("investIDRValue"), getIntent().getStringExtra("investId"));
    }
  }

  @Override
  public void initDataBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.invest_activity);
    investModel = new InvestViewModel(this, binding);
    binding.setViewModel(investModel);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    setResult(RESULT_CANCELED);
  }
}