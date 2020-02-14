package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.project.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.views.LoginActivity;
import com.project.indonesiainvestorclub.views.MainActivity;
import com.project.indonesiainvestorclub.views.SignUpActivity;

public class MainViewModel extends BaseViewModelWithCallback {

  private FirebaseRemoteConfig firebaseRemoteConfig;

  private static final String TAG = MainViewModel.class.getCanonicalName();

  private ActivityMainBinding binding;
  private NavHeaderMainBinding navHeaderMainBinding;
  public ObservableBoolean loginState;
  public ObservableBoolean signupState;
  public ObservableBoolean loadingState;
  public ObservableField<String> email;
  public ObservableField<String> name;

  public MainViewModel(Context context, ActivityMainBinding binding,
      NavHeaderMainBinding navHeaderMainBinding) {
    super(context);
    this.binding = binding;
    this.navHeaderMainBinding = navHeaderMainBinding;
    //    // Get Firebase Remote Config instance.
    //    firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    //    // Create a Remote Config Setting to enable developer mode,
    //    FirebaseRemoteConfigSettings.Builder configBuilder = new FirebaseRemoteConfigSettings.Builder();

    loginState = new ObservableBoolean(false);
    signupState = new ObservableBoolean(false);
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
      signupState.set(true);
      email.set(SharedPreferenceHelper.getUserName());
      name.set(SharedPreferenceHelper.getUserRealName());
      ImageHelper.loadImage(navHeaderMainBinding.imageView, SharedPreferenceHelper.getUserAva());
      menuVisible(true);
    } else {
      loginState.set(false);
      signupState.set(false);
      email.set("");
      name.set("");
      navHeaderMainBinding.imageView.setImageResource(R.mipmap.ic_launcher_round);
      menuVisible(false);
    }
  }

  private void menuVisible(boolean visible) {
    binding.navView.getMenu().findItem(R.id.nav_about).setVisible(visible);
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
    ((MainActivity) context).startActivityForResult(i, MainActivity.REQ_LOGIN);
  }

  @SuppressWarnings("unused")
  public void signupScreen(View view) {
    Intent i = new Intent(context, SignUpActivity.class);
    ((MainActivity) context).startActivityForResult(i, MainActivity.REQ_SIGNUP);
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
