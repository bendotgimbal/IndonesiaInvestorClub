package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Performance;
import java.util.List;

public class PerformanceRes {
  private int page;
  private int pages;
  private List<Performance> performances;

  public void setPerformances(int page, int pages, List<Performance> performances) {
    this.page = page;
    this.pages = pages;
    this.performances = performances;
  }

  public List<Performance> getPerformances() {
    return performances;
  }

  public int getPage() {
    return page;
  }

  public int getPages() {
    return pages;
  }

}
