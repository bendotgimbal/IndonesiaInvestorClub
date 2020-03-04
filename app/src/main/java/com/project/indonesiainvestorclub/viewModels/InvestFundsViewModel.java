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
                if (s.toString().equals("")){
                    Log.d("Debug", "Null");
                    binding.edtInvestSlot.append("0");
                }

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

                    //setting text after format to EditText
                    binding.edtInvestSlot.setText(formattedString);
                    binding.edtInvestSlot.setSelection(binding.edtInvestSlot.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                binding.edtInvestSlot.addTextChangedListener(this);

            }
        });
    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    public void onSlotTextChanged(CharSequence text) {
        // TODO do something with text
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
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