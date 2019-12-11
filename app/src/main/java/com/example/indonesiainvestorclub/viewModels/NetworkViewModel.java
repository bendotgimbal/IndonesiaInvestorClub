package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.NetworkFragmentBinding;

public class NetworkViewModel extends ViewModel {

    private NetworkFragmentBinding binding;
    private MutableLiveData<String> mText;

//    public NetworkViewModel(Context context, NetworkFragmentBinding binding){
//        super(context);
//        this.binding = binding;
//        mText = new MutableLiveData<>();
//        mText.setValue("This is Network fragment");
//    }

    public NetworkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Network fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
