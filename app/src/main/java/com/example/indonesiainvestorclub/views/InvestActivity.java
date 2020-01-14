package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.InvestActivityBinding;
import com.example.indonesiainvestorclub.viewModels.InvestViewModel;

public class InvestActivity extends BaseActivity {

    private InvestActivityBinding binding;
    private InvestViewModel investModel;
    String id_invest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invest_activity);

        Bundle extras = getIntent().getExtras();
        id_invest = extras.getString("investId");
//        if (id_invest == null) {
//            Toast.makeText(this, "Invest ID Kosong", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "Invest ID "+id_invest, Toast.LENGTH_LONG).show();
//        }

        investModel.investActivity(id_invest);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.invest_activity);
        investModel = new InvestViewModel(this, binding);
        binding.setViewModel(investModel);
    }
}