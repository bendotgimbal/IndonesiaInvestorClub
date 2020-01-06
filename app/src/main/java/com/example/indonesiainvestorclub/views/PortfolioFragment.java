package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.PortfolioFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.PortfolioViewModel;

public class PortfolioFragment extends BaseFragment {

    private PortfolioFragmentBinding binding;
    private PortfolioViewModel portfolioViewModel;
    private ListView lv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.portfolio_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initDataBinding() {
        portfolioViewModel = new PortfolioViewModel(this.getContext(), binding);
        binding.setViewModel(portfolioViewModel);
    }
}
