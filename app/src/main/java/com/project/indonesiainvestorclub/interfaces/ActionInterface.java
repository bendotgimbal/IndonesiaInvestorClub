package com.project.indonesiainvestorclub.interfaces;

public interface ActionInterface {

  interface AdapterItemListener<T> {
    void onClickAdapterItem(int index, T model);
  }

}
