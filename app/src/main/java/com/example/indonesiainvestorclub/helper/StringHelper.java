package com.example.indonesiainvestorclub.helper;

import android.util.Log;
import com.example.indonesiainvestorclub.viewModels.LoginViewModel;

public class StringHelper {
  public static void getCookie(String cookie){
    if (cookie != null){
      String[] output = cookie.split(";");
      Log.d(LoginViewModel.class.getCanonicalName(), output[0]);

      SharedPreferenceHelper.setCookie(output[0]);
    }
  }
}
