package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.InvestFundsFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.InvestFundsViewModel;

public class InvestFundsFragment extends BaseFragment {

    private InvestFundsFragmentBinding binding;
    private InvestFundsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.invest_funds_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initDataBinding() {
        viewModel = new InvestFundsViewModel(this.getContext(), binding);
        binding.setViewModel(viewModel);
    }

}