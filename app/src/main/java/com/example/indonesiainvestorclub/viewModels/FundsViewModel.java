package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;

public class FundsViewModel extends BaseViewModel {

  private FundsFragmentBinding binding;
  private MutableLiveData<String> mText;

  public FundsViewModel(Context context,
      FundsFragmentBinding binding) {
    super(context);
    this.binding = binding;

    mText = new MutableLiveData<>();
    mText.setValue("This is Funds fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}
