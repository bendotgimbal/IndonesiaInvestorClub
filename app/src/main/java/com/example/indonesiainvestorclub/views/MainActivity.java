package com.example.indonesiainvestorclub.views;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.viewModels.MainViewModel;

public class MainActivity extends BaseActivity {

  private ActivityMainBinding binding;
  private MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(binding.appbar.toolbar);
    initDrawerIcon();
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
    viewModel = new MainViewModel(this, binding);
    binding.setViewModel(viewModel);
  }

  @Override public void onBackPressed() {
    if (viewModel.isDrawerOpen()) {
      viewModel.closeDrawer();
      return;
    }
    super.onBackPressed();
  }
}
