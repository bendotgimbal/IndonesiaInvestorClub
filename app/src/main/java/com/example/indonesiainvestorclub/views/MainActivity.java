package com.example.indonesiainvestorclub.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.indonesiainvestorclub.R;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.indonesiainvestorclub.viewModels.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity {

  private AppBarConfiguration mAppBarConfiguration;
  //private MainActivityBinding binding;
  private MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_home, R.id.nav_gallery
    )
        .setDrawerLayout(drawer)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);

    navigationView.setNavigationItemSelectedListener(menuItem -> {
      int id = menuItem.getItemId();
      switch (id) {
        case R.id.nav_home:
          Toast.makeText(MainActivity.this, "MainActivity", Toast.LENGTH_SHORT).show();
        case R.id.nav_gallery:
          Toast.makeText(MainActivity.this, "LoginActivity", Toast.LENGTH_SHORT).show();
          startActivity(new Intent(MainActivity.this, LoginActivity.class));
      }
      return true;
    });
  }

  @Override
  public void initDataBinding() {

  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }
}
