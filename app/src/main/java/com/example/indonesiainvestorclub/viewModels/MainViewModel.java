package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import androidx.core.view.GravityCompat;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.views.AgreementActivity;
import com.example.indonesiainvestorclub.views.LoginActivity;
import com.example.indonesiainvestorclub.views.PerformanceActivity;
import com.example.indonesiainvestorclub.views.ProfileActivity;

public class MainViewModel extends BaseViewModel {

  private ActivityMainBinding binding;

  public MainViewModel(Context context, ActivityMainBinding binding) {
    super(context);
    this.binding = binding;
    initNavMenu();
  }

  private void initNavMenu() {
    binding.navView.setNavigationItemSelectedListener(menuItem -> {
      int id = menuItem.getItemId();
      switch (id) {
        case R.id.nav_home:
          goToMenu(PerformanceActivity.class);
          break;
        case R.id.nav_login:
          goToMenu(LoginActivity.class);
          break;
        case R.id.nav_profile:
          goToMenu(ProfileActivity.class);
          break;
        case R.id.nav_portofolio:
          goToMenu(AgreementActivity.class);
          break;
      }
      return true;
    });
  }

  private void goToMenu(Class activity){
    closeDrawer();
    context.startActivity(new Intent(context, activity));
  }

  @SuppressWarnings("unused")
  public void openDrawer() {
    binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }

  public void closeDrawer() {
    binding.drawerLayout.closeDrawer(GravityCompat.START, true);
  }

  public boolean isDrawerOpen(){
    return binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }
}
