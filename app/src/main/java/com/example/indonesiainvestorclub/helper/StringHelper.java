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

  public static Float setPieValue(String data){
    if (data.contains("-"))return 0F;
    return Float.parseFloat(data.replaceAll(" %","").replace(",","."));
  }

  public static String setYTDValue(String data){
    if (data.contains("-"))return "0%";
    return data.replace(" ", "").replace(",",".");
  }
}
