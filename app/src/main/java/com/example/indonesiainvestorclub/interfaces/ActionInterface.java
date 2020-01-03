package com.example.indonesiainvestorclub.interfaces;

public interface ActionInterface {

  interface AdapterItemListener<T> {
    void onClickAdapterItem(int index, T model);
  }

}
