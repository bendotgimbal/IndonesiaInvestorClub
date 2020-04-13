package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.project.indonesiainvestorclub.databinding.InvestFundsActivityBinding;

import com.project.indonesiainvestorclub.helper.DecimalHelper;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class InvestFundsViewModel extends BaseViewModelWithCallback {

  private InvestFundsActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> mInvestSlot;
  public ObservableField<String> strInvestUSDValue;
  public ObservableField<String> strInvestIDRValue;
  public ObservableField<String> investUSDValueTotal;
  public ObservableField<String> investIDRValueTotal;
  public ObservableField<String> strInvestValue;

  public InvestFundsViewModel(Context context, InvestFundsActivityBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    mInvestSlot = new ObservableField<>("0");
    strInvestUSDValue = new ObservableField<>("0");
    strInvestIDRValue = new ObservableField<>("0");
    investUSDValueTotal = new ObservableField<>("0");
    investIDRValueTotal = new ObservableField<>("0");
    strInvestValue = new ObservableField<>("0");

    binding.edtInvestSlot.append("0");

    binding.edtInvestSlot.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        onSlotTextChanged(s);
      }

      @Override
      public void afterTextChanged(Editable s) {
        binding.edtInvestSlot.removeTextChangedListener(this);

        try {
          String originalString = s.toString();

          Long longval;
          if (originalString.contains(",")) {
            originalString = originalString.replaceAll(",", "");
          }
          longval = Long.parseLong(originalString);

          DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
          formatter.applyPattern("#,###,###,###");
          String formattedString = formatter.format(longval);

          binding.edtInvestSlot.setText(formattedString);
          binding.edtInvestSlot.setSelection(binding.edtInvestSlot.getText().length());
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }

        Log.d("Debug", "Input Text " + s);

        binding.edtInvestSlot.addTextChangedListener(this);
        Log.d("Debug", "Input Text 2 " + binding.edtInvestSlot.getText().toString());

        int length = s.length();
        Log.d("Debug", "Text Length = " + length);
        if(length == 11){
          Toast.makeText(getContext(), "Maksimal 9 Angka", Toast.LENGTH_SHORT).show();
          return;
        }
      }
    });
  }

  public void start(String investSlot, String investIDRValue) {
    strInvestUSDValue.set(investSlot);
    strInvestIDRValue.set(investIDRValue);
    strInvestValue.set(investSlot + " / " + investIDRValue);
    Log.d("Debug", "USD " + getStrInvestUSDValue() + " / " + " IDR " + getStrInvestIDRValue());
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  public String getStrInvestUSDValue() {
    if (strInvestUSDValue.get() == null) {
      return "";
    }
    return strInvestUSDValue.get();
  }

  @SuppressWarnings("unused")
  public String getStrInvestIDRValue() {
    if (strInvestIDRValue.get() == null) {
      return "";
    }
    return strInvestIDRValue.get();
  }

  private void onSlotTextChanged(CharSequence s) {
    if (s == null || s.toString().isEmpty() || s.toString().equals("")) {
      binding.edtInvestSlot.append("0");
      this.investUSDValueTotal.set(DecimalHelper.toUsd(0));
      this.investIDRValueTotal.set(DecimalHelper.toRupiah(0));
      return;
    }

    if (s.toString().equals("0")) return;

    String strCharText = String.valueOf(s).replace(",", "");
    String strReplaceUSDValue = getStrInvestUSDValue().replaceAll("[@, USD]", "");
    String strReplaceIDRValue =
        getStrInvestIDRValue().replaceAll("[Rp,. ID]", "").replace(".", " ");

    String strInputReplace = strCharText;

    if (strCharText.substring(0, 1).equals("0")) {
      strInputReplace = strCharText.substring(1);
    }

    double investUSDValueTotal =
        Double.parseDouble(strInputReplace) * Double.parseDouble(strReplaceUSDValue);
    this.investUSDValueTotal.set(DecimalHelper.toUsd(investUSDValueTotal));

    double investIDRValueTotal =
        Double.parseDouble(strInputReplace) * Double.parseDouble(strReplaceIDRValue);
    this.investIDRValueTotal.set(DecimalHelper.toRupiah(investIDRValueTotal));
  }

  @SuppressWarnings("unused")
  public void onClickInvest(View view) {
    Toast.makeText(getContext(), "Invest Button", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}