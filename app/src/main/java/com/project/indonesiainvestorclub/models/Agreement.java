package com.project.indonesiainvestorclub.models;

import androidx.annotation.Nullable;
import java.util.List;

public class Agreement {
  private String parent;
  private List<Childs> childs;

  public Agreement(String parent,
      @Nullable List<Childs> childs) {
    this.parent = parent;
    this.childs = childs;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String author) {
    this.parent = parent;
  }

  public List<Childs> getChilds() {
    return childs;
  }

  public void setChilds(List<Childs> childs) {
    this.childs = childs;
  }
}
