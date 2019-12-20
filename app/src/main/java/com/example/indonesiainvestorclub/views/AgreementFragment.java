package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.AgreementFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.AgreementViewModel;

public class AgreementFragment extends BaseFragment {

  private AgreementFragmentBinding agreementFragmentBinding;
  private AgreementViewModel viewModel;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    agreementFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.agreement_fragment, container, false);
    return agreementFragmentBinding.getRoot();
  }

  @Override
  protected void initDataBinding() {
    viewModel = new AgreementViewModel(this.getContext(), agreementFragmentBinding);
    agreementFragmentBinding.setViewModel(viewModel);
  }
}
