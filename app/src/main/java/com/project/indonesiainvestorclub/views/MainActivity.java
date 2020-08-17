package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.project.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.project.indonesiainvestorclub.viewModels.MainViewModel;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private NavHeaderMainBinding navHeaderMainBinding;
    private MainViewModel viewModel;

    public static final int REQ_LOGIN = 1001;
    public static final int REQ_SIGNUP = 1002;
    public static final int FUND_MENU = 1003;
    public static final int REQ_LOGOUT = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appbar.toolbar);
        setTitle(this.getString(R.string.menu_home));

        initDrawerIcon();

        viewModel.start();
    }

    private void initDrawerIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    binding.drawerLayout,
                    binding.appbar.toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            binding.drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHeaderMainBinding = DataBindingUtil.bind(binding.navView.getHeaderView(0));
        viewModel = new MainViewModel(this, binding, navHeaderMainBinding);
        binding.setViewModel(viewModel);
        navHeaderMainBinding.setViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (viewModel.isDrawerOpen()) {
            viewModel.closeDrawer();
            return;
        } else if (viewModel.getCurrentPage() != R.id.nav_home){
            viewModel.gotoHome();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQ_LOGOUT || requestCode == REQ_LOGIN || requestCode == REQ_SIGNUP){
                viewModel.start();
            }
        }

    }
}
