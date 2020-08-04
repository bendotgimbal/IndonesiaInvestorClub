package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.CommissionFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.CommissionViewModel;

public class CommissionFragment extends BaseFragment {

    private CommissionFragmentBinding binding;
    private CommissionViewModel commissionViewModel;

    public static CommissionFragment newInstance() {
        return new CommissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.commission_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initDataBinding() {
        commissionViewModel = new CommissionViewModel(this.getContext(), binding);
        binding.setViewModel(commissionViewModel);
    }
}
