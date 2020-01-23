package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.NetworkFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.NetworkViewModel;

public class NetworkFragment extends BaseFragment {

  private NetworkFragmentBinding binding;
  private NetworkViewModel networkViewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.network_fragment, container, false);
    return binding.getRoot();
  }

  @Override
  protected void initDataBinding() {
    networkViewModel = new NetworkViewModel(this.getContext(), binding);
    binding.setViewModel(networkViewModel);
  }
}
