package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import com.example.indonesiainvestorclub.databinding.HomeFragmentBinding;

public class HomeViewModel extends BaseViewModelWithCallback {

  private HomeFragmentBinding binding;

  public HomeViewModel(Context context, HomeFragmentBinding binding) {
    super(context);
    this.binding = binding;
  }

  @Override public void hideLoading() {

  }
}
