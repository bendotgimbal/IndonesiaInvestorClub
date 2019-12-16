package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.LoungeFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.LoungeViewModel;

public class LoungeFragment extends Fragment {

    private LoungeFragmentBinding binding;
    private LoungeViewModel loungeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loungeViewModel = ViewModelProviders.of(this).get(LoungeViewModel.class);
        View rootView = inflater.inflate(R.layout.lounge_fragment, container, false);
        final TextView textView = rootView.findViewById(R.id.text_lounge);
        loungeViewModel.getText().observe(this, textView::setText);
        return rootView;
    }
}
