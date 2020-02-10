package com.project.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.AboutFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.AboutViewModel;

public class AboutFragment extends BaseFragment{

    private AboutFragmentBinding binding;
    private AboutViewModel aboutviewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initDataBinding() {
        aboutviewModel = new AboutViewModel(this.getContext(), binding);
        binding.setViewModel(aboutviewModel);
    }
}