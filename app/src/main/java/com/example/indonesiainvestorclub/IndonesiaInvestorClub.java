package com.example.indonesiainvestorclub;

import androidx.multidex.MultiDexApplication;

public class IndonesiaInvestorClub extends MultiDexApplication {

  private static IndonesiaInvestorClub instance;

  @Override
  public void onCreate() {
    super.onCreate();

    instance = this;
  }

  public static IndonesiaInvestorClub getApplication() {
    return instance;
  }
}
