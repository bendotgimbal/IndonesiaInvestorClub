package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.HomeFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.HomeViewModel;

public class HomeFragment extends BaseFragment {

  private HomeFragmentBinding binding;
  private HomeViewModel viewModel;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
    return binding.getRoot();
  }

  @Override protected void initDataBinding() {
    viewModel = new HomeViewModel(this.getContext(), binding);
    binding.setViewModel(viewModel);
  }
}