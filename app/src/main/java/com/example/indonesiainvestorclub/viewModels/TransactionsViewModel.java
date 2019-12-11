package com.example.indonesiainvestorclub.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.TransactionsFragmentBinding;

public class TransactionsViewModel extends ViewModel {

    private TransactionsFragmentBinding binding;
    private MutableLiveData<String> mText;

    public TransactionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Transactions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
