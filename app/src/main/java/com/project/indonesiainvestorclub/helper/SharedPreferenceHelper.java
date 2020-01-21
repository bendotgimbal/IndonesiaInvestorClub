package com.project.indonesiainvestorclub.helper;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import com.project.indonesiainvestorclub.BuildConfig;
import com.project.indonesiainvestorclub.IndonesiaInvestorClub;

public class SharedPreferenceHelper {

  //PARENT
  private static final String USER = "user";
  private static final String APPLICATION = "application";

  //CHILD
  private static final String LOGIN_STATE = "login_state";
  private static final String TOKEN = "token";
  private static final String COOKIE = "cookie";
  private static final String USER_NAME = "user_name";
  private static final String USER_KEY = "user_key";
  private static final String USER_REAL_NAME = "user_real_name";
  private static final String USER_AVA = "user_ava";

  @Nullable
  private static SharedPreferences getUserPreferences() {
    if (IndonesiaInvestorClub.getApplication() == null) return null;
    return IndonesiaInvestorClub.getApplication().getSharedPreferences(USER, Context.MODE_PRIVATE);
  }

  @Nullable
  private static SharedPreferences getApplicationPreferences() {
    if (IndonesiaInvestorClub.getApplication() == null) return null;
    return IndonesiaInvestorClub.getApplication()
        .getSharedPreferences(APPLICATION, Context.MODE_PRIVATE);
  }

  public static void setToken(String token) {
    if (getUserPreferences() == null) return;
    if (token.isEmpty()) {
      token = BuildConfig.PUBLIC_TOKEN;
    }
    getUserPreferences().edit().putString(TOKEN, token).apply();
  }

  public static String getToken() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(TOKEN, BuildConfig.PUBLIC_TOKEN);
  }

  public static void setCookie(String cookie) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putString(COOKIE, cookie).apply();
  }

  public static String getCookie() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(COOKIE, "");
  }

  public static void setLogin(boolean loggedIn) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putBoolean(LOGIN_STATE, loggedIn).apply();
  }

  public static boolean getLoginState() {
    return getUserPreferences() != null && getUserPreferences().getBoolean(LOGIN_STATE, false);
  }

  //USERNAME
  public static void setUserName(String email) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putString(USER_NAME, email).apply();
  }

  public static String getUserName() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(USER_NAME, "");
  }

  //PASSWORD
  public static void setUserKey(String key) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putString(USER_KEY, key).apply();
  }

  public static String getUserKey() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(USER_KEY, "");
  }

  //REAL NAME
  public static void setUserRealName(String userRealName) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putString(USER_REAL_NAME, userRealName).apply();
  }

  public static String getUserRealName() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(USER_REAL_NAME, "");
  }

  //AVATAR
  public static void setUserAva(String userAva) {
    if (getUserPreferences() == null) return;
    getUserPreferences().edit().putString(USER_AVA, userAva).apply();
  }

  public static String getUserAva() {
    if (getUserPreferences() == null) return null;
    return getUserPreferences().getString(USER_AVA, "");
  }

}
