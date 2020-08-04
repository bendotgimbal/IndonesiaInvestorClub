package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ProfileFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.ProfileViewModel;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment {

  public static final int REFRESH_PROFILE = 1003;

  private ProfileFragmentBinding binding;
  private ProfileViewModel viewModel;

  public static ProfileFragment newInstance() {
    return new ProfileFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
    return binding.getRoot();
  }

  @Override
  public void initDataBinding() {
    viewModel = new ProfileViewModel(this.getContext(), binding, this);
    binding.setViewModel(viewModel);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (resultCode == RESULT_OK) {
      if (requestCode == REFRESH_PROFILE) {
        viewModel.start();
      }
    }
  }
}
