package com.example.indonesiainvestorclub.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indonesiainvestorclub.databinding.NetworkFragmentBinding;

public class NetworkViewModel extends ViewModel {

  private NetworkFragmentBinding binding;
  private MutableLiveData<String> mText;

  public NetworkViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is Network fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}
