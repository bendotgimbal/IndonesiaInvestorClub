package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Funds;
import com.project.indonesiainvestorclub.models.Meta;

import java.util.List;

public class FundsRes {
  private List<Funds> funds;
  private List<Meta> meta;

  public FundsRes(List<Funds> funds, List<Meta> meta) {
    this.funds = funds;
    this.meta = meta;
  }

  public List<Funds> getFunds() {
    return funds;
  }

  public void setFunds(List<Funds> funds) {
    this.funds = funds;
  }

  public List<Meta> getMeta() {
    return meta;
  }

  public void setMeta(List<Meta> meta) {
    this.meta = meta;
  }
}
