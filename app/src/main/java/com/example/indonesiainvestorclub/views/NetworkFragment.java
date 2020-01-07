package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.NetworkFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.NetworkViewModel;

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
