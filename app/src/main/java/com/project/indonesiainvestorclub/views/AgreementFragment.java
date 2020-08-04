package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.AgreementFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.AgreementViewModel;

public class AgreementFragment extends BaseFragment {

  private AgreementFragmentBinding binding;
  private AgreementViewModel viewModel;

  public static AgreementFragment newInstance() {
    return new AgreementFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.agreement_fragment, container, false);
    return binding.getRoot();
  }

  @Override
  protected void initDataBinding() {
    viewModel = new AgreementViewModel(this.getContext(), binding);
    binding.setViewModel(viewModel);
  }
}
