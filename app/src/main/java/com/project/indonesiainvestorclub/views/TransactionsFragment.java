package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.TransactionsFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.TransactionsViewModel;

public class TransactionsFragment extends BaseFragment {

    private TransactionsFragmentBinding binding;
    private TransactionsViewModel transactionsViewModel;
    private ListView lv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.transactions_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initDataBinding() {
        transactionsViewModel = new TransactionsViewModel(this.getContext(), binding);
        binding.setViewModel(transactionsViewModel);
    }
}
