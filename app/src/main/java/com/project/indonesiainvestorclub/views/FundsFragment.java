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
import com.project.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.FundsViewModel;

public class FundsFragment extends BaseFragment {

  private FundsFragmentBinding binding;
  private FundsViewModel fundsviewModel;
  private ListView lv;

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
    fundsviewModel = new FundsViewModel(this.getContext(), binding);
    binding.setViewModel(fundsviewModel);
  }
}
