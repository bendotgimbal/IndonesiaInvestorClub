package com.example.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.example.indonesiainvestorclub.R;
import com.example.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.example.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.example.indonesiainvestorclub.helper.ImageHelper;
import com.example.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.example.indonesiainvestorclub.views.LoginActivity;
import com.example.indonesiainvestorclub.views.MainActivity;

public class MainViewModel extends BaseViewModelWithCallback {

  private static final String TAG = MainViewModel.class.getCanonicalName();

  private ActivityMainBinding binding;
  private NavHeaderMainBinding navHeaderMainBinding;
  public ObservableBoolean loginState;
  public ObservableBoolean loadingState;
  public ObservableField<String> email;
  public ObservableField<String> name;

  public MainViewModel(Context context, ActivityMainBinding binding,
      NavHeaderMainBinding navHeaderMainBinding) {
    super(context);
    this.binding = binding;
    this.navHeaderMainBinding = navHeaderMainBinding;

    loginState = new ObservableBoolean(false);
    loadingState = new ObservableBoolean(false);
    email = new ObservableField<>("");
    name = new ObservableField<>("");
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  public void start() {
    if (SharedPreferenceHelper.getLoginState()) {
      loginState.set(true);
      email.set(SharedPreferenceHelper.getUserName());
      name.set(SharedPreferenceHelper.getUserRealName());
      ImageHelper.loadImage(navHeaderMainBinding.imageView, SharedPreferenceHelper.getUserAva());
      menuVisible(true);
    } else {
      loginState.set(false);
      email.set("");
      name.set("");
      navHeaderMainBinding.imageView.setImageResource(R.mipmap.ic_launcher_round);
      menuVisible(false);
    }
  }

  private void menuVisible(boolean visible) {
    binding.navView.getMenu().findItem(R.id.nav_profile).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_transaction).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_portfolio).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_network).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_lounge).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_agreement).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_funds).setVisible(visible);
    binding.navView.getMenu().findItem(R.id.nav_logout).setVisible(visible);
  }

  @SuppressWarnings("unused")
  public void loginScreen(View view) {
    Intent i = new Intent(context, LoginActivity.class);
    ((MainActivity)context).startActivityForResult(i, MainActivity.REQ_LOGIN);
  }

  @SuppressWarnings("unused")
  public void openDrawer() {
    binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }

  public void closeDrawer() {
    binding.drawerLayout.closeDrawer(GravityCompat.START, true);
  }

  public boolean isDrawerOpen() {
    return binding.drawerLayout.isDrawerOpen(GravityCompat.START);
  }

  @Override
  public void hideLoading() {
    loading(false);
  }
}
