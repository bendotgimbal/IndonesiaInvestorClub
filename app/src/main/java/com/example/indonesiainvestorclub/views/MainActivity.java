package com.example.indonesiainvestorclub.views;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.example.indonesiainvestorclub.viewModels.MainViewModel;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private NavHeaderMainBinding navHeaderMainBinding;
    private MainViewModel viewModel;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.appbar.toolbar);
        initDrawerIcon();
        initAppBarConfiguration();
    }

    private void initAppBarConfiguration() {
        appBarConfiguration =
                new AppBarConfiguration.Builder(
                        R.id.nav_home,
                        R.id.nav_profile,
                        R.id.nav_lounge,
                        R.id.nav_agreement,
                        R.id.nav_network,
                        R.id.nav_transaction,
                        R.id.nav_funds,
                        R.id.nav_logout).setDrawerLayout(binding.drawerLayout).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.start();
    }

    @Override
    public void onBackPressed() {
        if (viewModel.isDrawerOpen()) {
            viewModel.closeDrawer();
            return;
        }
        super.onBackPressed();
    }
}
