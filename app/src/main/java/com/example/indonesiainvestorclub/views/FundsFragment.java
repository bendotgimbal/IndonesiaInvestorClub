package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.FundsFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.FundsViewModel;

public class FundsFragment extends Fragment {

  private FundsFragmentBinding binding;
  private FundsViewModel fundsviewModel;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    fundsviewModel = ViewModelProviders.of(this).get(FundsViewModel.class);
    View rootView = inflater.inflate(R.layout.funds_fragment, container, false);
    final TextView textView = rootView.findViewById(R.id.text_funds);
    fundsviewModel.getText().observe(this, textView::setText);
    return rootView;
  }
}
