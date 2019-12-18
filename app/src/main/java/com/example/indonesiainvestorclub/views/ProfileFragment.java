package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ProfileFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.ProfileViewModel;

public class ProfileFragment extends BaseFragment {

    private ProfileFragmentBinding binding;
    private ProfileViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void initDataBinding() {
        viewModel = new ProfileViewModel(this.getContext(), binding);
        binding.setViewModel(viewModel);
    }
}
