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
import com.example.indonesiainvestorclub.databinding.LogoutFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.LogoutViewModel;

public class LogoutFragment extends Fragment {

    private LogoutFragmentBinding binding;
    private LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        logoutViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        View rootView = inflater.inflate(R.layout.logout_fragment, container, false);
        final TextView textView = rootView.findViewById(R.id.text_logout);
        logoutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return rootView;
    }
}
