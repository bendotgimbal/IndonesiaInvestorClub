package com.project.indonesiainvestorclub.models;


public class Performance {
  String name;
  Datas datas;

  public Performance(String name, Datas datas) {
    this.name = name;
    this.datas = datas;
  }

  public String getNameText(){
    return "Live Performance\n"+getName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Datas getData() {
    return datas;
  }

  public void setData(Datas data) {
    this.datas = data;
  }
}
