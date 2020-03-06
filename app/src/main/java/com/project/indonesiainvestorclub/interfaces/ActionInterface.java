package com.project.indonesiainvestorclub.interfaces;

public interface ActionInterface {

  interface AdapterItemListener<T> {
    void onClickAdapterItem(int index, T model);
  }

  interface CustomDialogActionButton{
    void onClickPositiveButton();

    void onClickNegativeButton();
  }

  interface DatePickerDialog{
    void setDate(int day, int month, int year);
  }
}
