package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.LoungeFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.LoungeViewModel;

public class LoungeFragment extends BaseFragment {

  private LoungeFragmentBinding binding;
  private LoungeViewModel viewModel;

  public static LoungeFragment newInstance() {
    return new LoungeFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.lounge_fragment, container, false);
    return binding.getRoot();
  }

  @Override
  protected void initDataBinding() {
    viewModel = new LoungeViewModel(this.getContext(), binding);
    binding.setViewModel(viewModel);
  }
}