package com.example.indonesiainvestorclub.models;

import java.util.List;

public class Performance {
  String name;
  List<Datas> data;

  public Performance(String name, List<Datas> data) {
    this.name = name;
    this.data = data;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Datas> getData() {
    return data;
  }

  public void setData(List<Datas> data) {
    this.data = data;
  }
}
