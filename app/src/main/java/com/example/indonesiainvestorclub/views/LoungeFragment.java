package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.LoungeFragmentBinding;
import com.example.indonesiainvestorclub.viewModels.LoungeViewModel;

public class LoungeFragment extends Fragment {

    private LoungeFragmentBinding binding;
    private LoungeViewModel loungeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lounge_fragment, container, false);
        return rootView;
    }
}
