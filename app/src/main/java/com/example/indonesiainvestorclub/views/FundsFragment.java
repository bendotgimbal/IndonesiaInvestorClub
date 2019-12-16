package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.FundsViewModel;

public class FundsFragment extends BaseFragment {

  private FundsFragmentBinding binding;
  private FundsViewModel viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.funds_fragment, container, false);
    return binding.getRoot();
  }

  @Override
  protected void initDataBinding() {
    viewModel = new FundsViewModel(this.getContext(), binding);
    binding.setViewModel(viewModel);
  }
}
