package com.project.indonesiainvestorclub.models;

public class Monthly {
  String month;
  String percentage;

  public Monthly(String month, String percentage) {
    this.month = month;
    this.percentage = percentage;
  }

  public String getMonth() {
    return month;
  }

  public String getPercentage() {
    return percentage;
  }
}
