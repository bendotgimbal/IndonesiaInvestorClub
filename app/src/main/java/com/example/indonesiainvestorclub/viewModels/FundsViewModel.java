package com.example.indonesiainvestorclub.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;

public class FundsViewModel extends ViewModel {

    private FundsFragmentBinding binding;
    private MutableLiveData<String> mText;

    public FundsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Funds fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
