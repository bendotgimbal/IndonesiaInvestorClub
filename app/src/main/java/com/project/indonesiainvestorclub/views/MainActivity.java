package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.project.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.project.indonesiainvestorclub.viewModels.MainViewModel;

public class MainActivity extends BaseActivity {

  private ActivityMainBinding binding;
  private NavHeaderMainBinding navHeaderMainBinding;
  private MainViewModel viewModel;
  private AppBarConfiguration appBarConfiguration;

  public static final int SHOW_ABOUT = 1000;
  public static final int REQ_LOGIN = 1001;
  public static final int REQ_SIGNUP = 1002;
  public static final int FUND_MENU = 1003;

  private boolean show_about = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(binding.appbar.toolbar);
    initDrawerIcon();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.parseColor("#008577"));
    }

    initAppBarConfiguration();
  }

  private void initAppBarConfiguration() {
    appBarConfiguration =
        new AppBarConfiguration.Builder(
            R.id.nav_home,
            R.id.nav_profile,
            R.id.nav_transaction,
            R.id.nav_portfolio,
            R.id.nav_network,
            R.id.nav_lounge,
            R.id.nav_agreement,
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

    if (show_about) {
      viewModel.getAbout();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    show_about = true;
  }

  @Override
  public void onBackPressed() {
    if (viewModel.isDrawerOpen()) {
      viewModel.closeDrawer();
      return;
    }
    super.onBackPressed();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode > SHOW_ABOUT){
      show_about = false;
    }
  }
}
