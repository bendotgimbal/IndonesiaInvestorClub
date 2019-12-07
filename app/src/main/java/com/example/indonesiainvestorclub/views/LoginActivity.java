package com.example.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.services.InvestorClubService;
import com.example.indonesiainvestorclub.services.ServiceGenerator;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        InvestorClubService service = ServiceGenerator.service;
        InvestorClubService mInterfaceService = ServiceGenerator.retrofit.create(InvestorClubService.class);
    }
}
