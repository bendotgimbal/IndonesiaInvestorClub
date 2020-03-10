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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class InvestFundsViewModel extends BaseViewModelWithCallback {

  private InvestFundsActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableField<String> mInvestSlot;
  private String strInvestUSDValue;
  private String strInvestIDRValue;
  private String strInputReplace;
  private Double dblInvestUSDValueTotal;
  private Double dblInvestIDRValueTotal;
  private int InvestUSDValueTotal;

  public InvestFundsViewModel(Context context, InvestFundsActivityBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    mInvestSlot = new ObservableField<>("0");
    binding.edtInvestSlot.append("0");

    binding.edtInvestSlot.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

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
      }
    });
  }

  public void start(String investSlot, String investIDRValue) {
    Log.d("Debug", "InvestSlot " + investSlot + " || " + " InvestIDRValue " + investIDRValue);
    strInvestUSDValue = investSlot;
    strInvestIDRValue = investIDRValue;
    Log.d("Debug",
        "InvestSlot 2 " + strInvestUSDValue + " || " + " InvestIDRValue 2 " + strInvestIDRValue);
  }

  private void calculate(String edtInvestUSDValue) {
    //        dblInvestUSDValueTotal = Double.valueOf(edtInvestUSDValue.toString()) * Double.valueOf(strInvestUSDValue);
    ////        Toast.makeText(getContext(), String.valueOf(dblInvestUSDValueTotal), Toast.LENGTH_SHORT).show();
    //        Log.d("Debug", "USD Value Total "+String.valueOf(dblInvestUSDValueTotal));
    //        Log.d("Debug", "Input Total "+edtInvestUSDValue);
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  public void onSlotTextChanged(CharSequence text) {
    // TODO do something with text
    if (text == null || text.toString().isEmpty() || text.toString().equals("")) {
      Log.d("Debug", "Null");
      binding.edtInvestSlot.append("0");
      return;
    }

    if (text.toString().equals("0")) return;

    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    Log.d("Debug", "Input " + text);
    String strCharText = String.valueOf(text);
    String strReplaceUSDValue = strInvestUSDValue.replaceAll("[@, USD]", "");
    Log.d("Debug", "Replace " + strReplaceUSDValue);
    //        String strReplaceUSDValue2 = strReplaceUSDValue.replaceAll("[USD]","");
    //        Log.d("Debug", "Replace 2 "+strReplaceUSDValue2);
    if (strCharText.substring(0, 1).equals("0")) {
      Log.d("Debug", "Input Number = " + strCharText.substring(1, strCharText.length()));
      strInputReplace = strCharText.substring(1, strCharText.length());
      Log.d("Debug", "Input TextWatcher = " + strInputReplace);
      //            dblInvestUSDValueTotal = Double.valueOf(strInputReplace) * Double.valueOf(strReplaceUSDValue2);
      //            String strInvestUSDValueTottal = String.valueOf(dblInvestUSDValueTotal);
      //            Log.d("Debug", "Invest USD Total "+strInvestUSDValueTottal);
    }
    //        Log.d("Debug", "Input Number 2 = "+strInputReplace);
    ////        Log.d("Debug", "Input Number 2 = "+strCharText);
    //        dblInvestUSDValueTotal = Double.valueOf(String.valueOf(strInputReplace)) * Double.valueOf(strReplaceUSDValue2);
    //        dblInvestUSDValueTotal = Double.valueOf(strInputReplace) * Double.valueOf(strReplaceUSDValue2);
    //        String strInvestUSDValueTottal = String.valueOf(dblInvestUSDValueTotal);
    //        Log.d("Debug", "Invest USD Total "+strInvestUSDValueTottal);
    ////        Integer InvestUSDValueTotal = Integer.valueOf(String.valueOf(strCharText)) * Integer.valueOf(strReplaceUSDValue);
    //        InvestUSDValueTotal = Integer.valueOf(strInputReplace) * Integer.valueOf(strReplaceUSDValue2);
    //        Log.d("Debug", "Invest USD Total "+InvestUSDValueTotal);
    InvestUSDValueTotal = Integer.parseInt(strInputReplace) * Integer.parseInt(strReplaceUSDValue);
    Log.d("Debug", "Invest USD Total " + String.valueOf(InvestUSDValueTotal));
    //        Toast.makeText(getContext(), strCharText, Toast.LENGTH_SHORT).show();
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