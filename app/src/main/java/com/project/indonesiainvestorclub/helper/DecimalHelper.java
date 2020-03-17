package com.project.indonesiainvestorclub.helper;

import androidx.annotation.NonNull;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DecimalHelper {

  private static final char DOT_SEPARATOR = '.';
  private static final char COMMA_SEPARATOR = ',';

  @NonNull
  public static String toRupiah(double amount) {
    String prefix = "Rp ";
    if (amount < 0) {
      prefix = "-Rp ";
      amount = amount * -1;
    }
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
    symbols.setGroupingSeparator(DOT_SEPARATOR);
    formatter.setDecimalFormatSymbols(symbols);

    return String.format(Locale.getDefault(), prefix + "%s", formatter.format(amount));
  }

  @NonNull
  public static String toUsd(double amount) {
    String prefix = "$ ";
    if (amount < 0) {
      prefix = "-$ ";
      amount = amount * -1;
    }
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
    symbols.setGroupingSeparator(DOT_SEPARATOR);
    formatter.setDecimalFormatSymbols(symbols);

    return String.format(Locale.getDefault(), prefix + "%s", formatter.format(amount));
  }

  @NonNull
  public static String toText(double amount) {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
    symbols.setGroupingSeparator(DOT_SEPARATOR);
    formatter.setDecimalFormatSymbols(symbols);

    return formatter.format(amount);
  }

  @NonNull
  public static List<Double> breakDown(double limitMin, double limitMax) {
    double step = ((limitMax - limitMin) / 100);
    List<Double> result = new ArrayList<>();
    double n = limitMin;

    for (double i = limitMin; i <= limitMax; i++) {
      if (n <= limitMax) {
        result.add(n);
        n += step;
      }
    }

    return result;
  }
}
