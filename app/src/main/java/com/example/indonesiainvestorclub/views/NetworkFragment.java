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
import com.example.indonesiainvestorclub.databinding.NetworkFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.NetworkViewModel;

public class NetworkFragment extends Fragment {

    private NetworkFragmentBinding binding;
    private NetworkViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        viewModel = ViewModelProviders.of(this).get(NetworkViewModel.class);
        View rootView = inflater.inflate(R.layout.network_fragment, container, false);
        final TextView textView = rootView.findViewById(R.id.text_network);
        viewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return rootView;
    }
}
