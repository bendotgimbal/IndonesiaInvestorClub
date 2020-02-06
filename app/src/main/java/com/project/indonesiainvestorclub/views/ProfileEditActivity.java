package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ProfileEditActivityBinding;
import com.project.indonesiainvestorclub.viewModels.ProfileEditViewModel;

public class ProfileEditActivity extends BaseActivity {

  private ProfileEditActivityBinding binding;
  private ProfileEditViewModel viewModel;

  String firstNameStr;
  String lastNameStr;
  String dobStr;
  String maritalStatusStr;
  String addressStr;
  String postalCodeStr;
  String genderStr;
  String nationalityStr;
  String cityStr;
  String countryStr;
  String phoneNumberStr;
  String occupationStr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null) {
      firstNameStr = extras.getString("firstNameStr");
      lastNameStr = extras.getString("lastNameStr");
      dobStr = extras.getString("dobStr");
      maritalStatusStr = extras.getString("maritalStatusStr");
      addressStr = extras.getString("addressStr");
      postalCodeStr = extras.getString("postalCodeStr");
      genderStr = extras.getString("genderStr");
      nationalityStr = extras.getString("nationalityStr");
      cityStr = extras.getString("cityStr");
      countryStr = extras.getString("countryStr");
      phoneNumberStr = extras.getString("phoneNumberStr");
      occupationStr = extras.getString("occupationStr");

      viewModel.start(firstNameStr, lastNameStr, dobStr, maritalStatusStr, addressStr,
          postalCodeStr,
          genderStr, nationalityStr, cityStr, countryStr, phoneNumberStr, occupationStr);
    }
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
    setResult(RESULT_CANCELED);
    finish();
  }
}