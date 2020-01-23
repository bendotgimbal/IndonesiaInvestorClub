package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Performance;
import java.util.List;

public class PerformanceRes {
  private List<Performance> performances;

  public List<Performance> getPerformances() {
    return performances;
  }

  public void setPerformances(List<Performance> performances) {
    this.performances = performances;
  }
}
