package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.InvestFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.InvestViewModel;

public class InvestFragment extends Fragment {

    private InvestFragmentBinding binding;
    private InvestViewModel investViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        investViewModel = ViewModelProviders.of(this).get(InvestViewModel.class);
        View rootView = inflater.inflate(R.layout.invest_fragment, container, false);
        final TextView textView = rootView.findViewById(R.id.text_invest);
        investViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return rootView;
    }
}
