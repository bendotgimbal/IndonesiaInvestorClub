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
                if(s.length() > 1){
                    Log.d("Debug", "Empty a3");
                    if(s.toString().matches("^00")){
                        Log.d("Debug", "Empty a3a");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.length()==0){
//                    binding.edtInvestSlot.append("0");
//                }else if (s.length() > 0){
//                    if (s.toString().equals("0")){
//                        mInvestSlot.set("0");
//                    }
////                    mInvestSlot.set(String.valueOf(s));
////                    binding.edtInvestSlot.append(s);
//                }else{
//                    mInvestSlot.set(String.valueOf(s));
//                }

//                if(s.length() > 0 && s.toString().charAt(s.length()-1) == 0)
//                {
//                    final String newText = s.toString().substring(0, s.length()-1) + 0;
////                    binding.edtInvestSlot.setText(newText);
////                    mInvestSlot.set(newText);
//                    binding.edtInvestSlot.append("0");
//                }

//                if (s.length()==0){
//                    binding.edtInvestSlot.append("0");
//                } else if(s.length() > 0 && s.toString().charAt(s.length()-1) == 0)
//                {
//                    final String newText = s.toString().substring(0, s.length()-1) + 0;
////                    mInvestSlot.set(newText);
////                    binding.edtInvestSlot.append("0");
//                    binding.edtInvestSlot.append(newText);
//                }

//                if (s.length()==0){
//                    mInvestSlot.set("0");
//                } else if(s.toString().charAt(s.length()-1) == 0){
//                    final String newText = s.toString().substring(0, s.length()-1) + 0;
//                    binding.edtInvestSlot.append(newText);
//                }

//                if(s.toString().matches("^0")){
//                    Log.d("Debug", "Empty 1");
//                    mInvestSlot.set("1");
//                }else if(s.toString().matches("^0"+s)){
//                    Log.d("Debug", "Empty 2");
//                    mInvestSlot.set("2");
//                }else{
//                    Log.d("Debug", "Empty 3");
//                    mInvestSlot.set("0");
//                }

//                if(s.toString().matches("^0")){
//                    Log.d("Debug", "Empty");
//                    mInvestSlot.set("1");
//                }else if(s.toString().equals("")){
//                    Log.d("Debug", "Empty 2");
//                    mInvestSlot.set("2");
//                } else if(s.length() > 1){
//                    Log.d("Debug", "Empty 3");
//                    if(s.toString().matches("^00")){
//                        Log.d("Debug", "Empty 3a");
//                    }
//                }

                if(s.toString().matches("^0")){
                    Log.d("Debug", "Empty");
                    mInvestSlot.set("1");
                }
                if(s.toString().equals("")){
                    Log.d("Debug", "Empty 2");
                    mInvestSlot.set("2");
                }
//                if(s.length() > 1){
//                    Log.d("Debug", "Empty 3");
//                }

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