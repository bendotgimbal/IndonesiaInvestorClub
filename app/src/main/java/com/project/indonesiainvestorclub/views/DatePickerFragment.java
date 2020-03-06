package com.project.indonesiainvestorclub.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

  private Activity activity;
  private ActionInterface.DatePickerDialog listener;

  public DatePickerFragment(Activity activity, ActionInterface.DatePickerDialog listener) {
    this.activity = activity;
    this.listener = listener;
  }

  @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(activity, this, year, month, day);
  }

  @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    listener.setDate(dayOfMonth, month+1, year);
  }
}
