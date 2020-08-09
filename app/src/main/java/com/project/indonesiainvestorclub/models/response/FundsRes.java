package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Funds;
import com.project.indonesiainvestorclub.models.Meta;

import java.util.List;

public class FundsRes {
  private int page;
  private int pages;
  private List<Funds> funds;

  public FundsRes(int page, int pages, List<Funds> funds) {
    this.page = page;
    this.pages = pages;
    this.funds = funds;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public List<Funds> getFunds() {
    return funds;
  }

  public void setFunds(List<Funds> funds) {
    this.funds = funds;
  }

}
