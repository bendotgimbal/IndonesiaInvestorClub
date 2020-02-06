package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Funds;
import com.project.indonesiainvestorclub.models.Meta;

import java.util.List;

public class FundsRes {
  private List<Funds> funds;

  public FundsRes(List<Funds> funds) {
    this.funds = funds;
  }

  public List<Funds> getFunds() {
    return funds;
  }

  public void setFunds(List<Funds> funds) {
    this.funds = funds;
  }

}
