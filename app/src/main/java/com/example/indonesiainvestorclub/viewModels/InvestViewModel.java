package com.example.indonesiainvestorclub.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.InvestFragmentBinding;

public class InvestViewModel extends ViewModel {

    private InvestFragmentBinding binding;
    private MutableLiveData<String> mText;

    public InvestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Invest fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
