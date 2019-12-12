package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.views.AgreementActivity;
import com.example.indonesiainvestorclub.views.InvestFragment;
import com.example.indonesiainvestorclub.views.LoginActivity;
import com.example.indonesiainvestorclub.views.NetworkFragment;
import com.example.indonesiainvestorclub.views.PerformanceActivity;
import com.example.indonesiainvestorclub.views.ProfileActivity;
import com.example.indonesiainvestorclub.views.TransactionsFragment;

public class MainViewModel extends BaseViewModel {

  private ActivityMainBinding binding;

  public MainViewModel(Context context, ActivityMainBinding binding) {
    super(context);
    this.binding = binding;
    initNavMenu();
  }

//  private void initNavMenu() {
//    binding.navView.setNavigationItemSelectedListener(menuItem -> {
//      int id = menuItem.getItemId();
//      Fragment fragment = null;
//      switch (id) {
//        case R.id.nav_home:
//          goToMenu(PerformanceActivity.class);
//          break;
//        case R.id.nav_login:
//          goToMenu(LoginActivity.class);
//          break;
//        case R.id.nav_profile:
//          goToMenu(ProfileActivity.class);
//          break;
//        case R.id.menu_network:
////          goToMenu(AgreementActivity.class);
//          fragment = new NetworkFragment();
//          break;
//      }
//      return true;
//    });
//  }

  private void initNavMenu() {
    binding.navView.setNavigationItemSelectedListener(menuItem -> {
      int id = menuItem.getItemId();
      Fragment fragment = null;
      switch (id) {
        case R.id.nav_home:
          goToMenu(PerformanceActivity.class);
          Toast.makeText(context, "PerformanceActivity is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_network:
          fragment = new NetworkFragment();
          Toast.makeText(context, "NetworkFragment is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_transactions:
          fragment = new TransactionsFragment();
          Toast.makeText(context, "TransactionsFragment is clicked", Toast.LENGTH_SHORT).show();
          break;
        case R.id.menu_invest:
          fragment = new InvestFragment();
          Toast.makeText(context, "InvestmentFragment is clicked", Toast.LENGTH_SHORT).show();
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
