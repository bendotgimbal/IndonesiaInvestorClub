package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import com.example.indonesiainvestorclub.databinding.LoginActivityBinding;

public class LoginViewModel extends BaseViewModel{

  private LoginActivityBinding binding;

  public LoginViewModel(Context context, LoginActivityBinding binding) {
    super(context);
    this.binding = binding;
  }


}
