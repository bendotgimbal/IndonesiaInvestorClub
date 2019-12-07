package com.example.indonesiainvestorclub.helper;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import com.example.indonesiainvestorclub.BuildConfig;
import com.example.indonesiainvestorclub.IndonesiaInvestorClub;

public class SharedPreferenceHelper {

  //PARENT
  private static final String USER = "user";
  private static final String APPLICATION = "application";

  //CHILD
  private static final String LOGIN_STATE = "login_state";
  private static final String TOKEN = "token";

  @Nullable
  private static SharedPreferences getUserPreferences() {
    if (IndonesiaInvestorClub.getApplication() == null) return null;
    return IndonesiaInvestorClub.getApplication().getSharedPreferences(USER, Context.MODE_PRIVATE);
  }

  @Nullable
  private static SharedPreferences getApplicationPreferences() {
    if (IndonesiaInvestorClub.getApplication() == null) return null;
    return IndonesiaInvestorClub.getApplication().getSharedPreferences(APPLICATION, Context.MODE_PRIVATE);
  }

  public static void setToken(String token) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putString(TOKEN, token).apply();
  }

  public static String getToken() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(TOKEN, BuildConfig.PUBLIC_TOKEN);
  }

  public static void setLogin(boolean loggedIn) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putBoolean(LOGIN_STATE, loggedIn).apply();
  }

  public static boolean getLoginState() {
    return getUserPreferences() != null && getUserPreferences().getBoolean(LOGIN_STATE, false);
  }


}
