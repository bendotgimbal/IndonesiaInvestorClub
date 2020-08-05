package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.adapter.ExpandableListAdapter;
import com.project.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.project.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.project.indonesiainvestorclub.helper.ActivityUtils;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.Commissions;
import com.project.indonesiainvestorclub.models.MenuModel;
import com.project.indonesiainvestorclub.views.AboutFragment;
import com.project.indonesiainvestorclub.views.AgreementFragment;
import com.project.indonesiainvestorclub.views.CommissionFragment;
import com.project.indonesiainvestorclub.views.FundsFragment;
import com.project.indonesiainvestorclub.views.HomeFragment;
import com.project.indonesiainvestorclub.views.LoginActivity;
import com.project.indonesiainvestorclub.views.LogoutActivity;
import com.project.indonesiainvestorclub.views.LoungeFragment;
import com.project.indonesiainvestorclub.views.MainActivity;
import com.project.indonesiainvestorclub.views.NetworkFragment;
import com.project.indonesiainvestorclub.views.PortfolioFragment;
import com.project.indonesiainvestorclub.views.ProfileFragment;
import com.project.indonesiainvestorclub.views.SignUpActivity;
import com.project.indonesiainvestorclub.views.TransactionsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

  ExpandableListAdapter expandableListAdapter;
  List<MenuModel> headerList = new ArrayList<>();
  HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

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

  public void start() {
    if (SharedPreferenceHelper.getLoginState()) {
      loginState.set(true);
      signupState.set(true);
      email.set(SharedPreferenceHelper.getUserName());
      name.set(SharedPreferenceHelper.getUserRealName());
      ImageHelper.loadImage(navHeaderMainBinding.imageView, SharedPreferenceHelper.getUserAva());
    } else {
      loginState.set(false);
      signupState.set(false);
      email.set("");
      name.set("");
      navHeaderMainBinding.imageView.setImageResource(R.mipmap.ic_launcher_round);
    }

    prepareMenuData();
    populateExpandableList();

    loadHome();
  }

  private void prepareMenuData() {
    headerList = new ArrayList<>();
    childList = new HashMap<>();

    MenuModel home = new MenuModel(R.id.nav_home, R.drawable.ic_investment, getContext().getString(R.string.menu_home), true, false);
    headerList.add(home);
    childList.put(home, null);

    MenuModel about = new MenuModel(R.id.nav_about, R.drawable.ic_about, getContext().getString(R.string.menu_about), true, false);
    headerList.add(about);
    childList.put(about, null);

    if (SharedPreferenceHelper.getLoginState()) {
      MenuModel lounge = new MenuModel(R.id.nav_lounge, R.drawable.ic_lounge, getContext().getString(R.string.menu_lounge), true, false);
      headerList.add(lounge);
      childList.put(lounge, null);

      MenuModel funds = new MenuModel(R.id.nav_funds, R.drawable.ic_funds, getContext().getString(R.string.menu_funds), true, false);
      headerList.add(funds);
      childList.put(funds, null);

      MenuModel user = new MenuModel(1, 0,"User Menu", true, true);
      headerList.add(user);

      List<MenuModel> userChildList = new ArrayList<>();
      MenuModel profile = new MenuModel(R.id.nav_profile, R.drawable.ic_profile, getContext().getString(R.string.menu_profile), false, false);
      userChildList.add(profile);

      MenuModel transaction = new MenuModel(R.id.nav_transaction, R.drawable.ic_transactions, getContext().getString(R.string.menu_transactions), false, false);
      userChildList.add(transaction);

      MenuModel portfolio = new MenuModel(R.id.nav_portfolio, R.drawable.ic_portfolio, getContext().getString(R.string.menu_portfolio), false, false);
      userChildList.add(portfolio);

      childList.put(user, userChildList);

      if (!SharedPreferenceHelper.isUserMarketing()) {
        MenuModel network = new MenuModel(R.id.nav_network, R.drawable.ic_network, getContext().getString(R.string.menu_network), false, false);
        headerList.add(network);

      } else {
        MenuModel marketing = new MenuModel(0, 0, "Marketing", true, true);
        headerList.add(marketing);

        List<MenuModel> marketingChildList = new ArrayList<>();
        MenuModel network = new MenuModel(R.id.nav_network, R.drawable.ic_network, getContext().getString(R.string.menu_network), false, false);
        marketingChildList.add(network);

        MenuModel commission = new MenuModel(R.id.nav_commission, R.drawable.ic_network, getContext().getString(R.string.menu_commission), false, false);
        marketingChildList.add(commission);

        childList.put(marketing, marketingChildList);
      }

      MenuModel agreement = new MenuModel(R.id.nav_agreement, R.drawable.ic_agreement, getContext().getString(R.string.menu_agreement), true, false);
      headerList.add(agreement);
      childList.put(agreement, null);

      MenuModel logout = new MenuModel(R.id.nav_logout, R.drawable.ic_logout, getContext().getString(R.string.menu_logout), true, false);
      headerList.add(logout);
      childList.put(logout, null);
    }
  }

  private void populateExpandableList() {
    expandableListAdapter = new ExpandableListAdapter(context, headerList, childList);
    binding.expandableListView.setAdapter(expandableListAdapter);

    binding.expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
      MenuModel menuModel = headerList.get(groupPosition);
      if (menuModel.isGroup) {
        if (!menuModel.hasChildren) {
          navigation(menuModel.menu);
          ((MainActivity)context).onBackPressed();
        }
      }
      return false;
    });

    binding.expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
      MenuModel menuModel = headerList.get(groupPosition);
      if (childList.get(menuModel) != null) {
        navigation(childList.get(menuModel).get(childPosition).menu);
        ((MainActivity)context).onBackPressed();
      }
      return false;
    });
  }

  private void loadHome() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_home));
    transactFragment(HomeFragment.newInstance());
  }

  private void loadAbout() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_about));
    transactFragment(AboutFragment.newInstance());
  }

  private void loadLounge() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_lounge));
    transactFragment(LoungeFragment.newInstance());
  }

  private void loadFunds() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_funds));
    transactFragment(FundsFragment.newInstance());
  }

  private void loadNetwork() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_network));
    transactFragment(NetworkFragment.newInstance());
  }

  private void loadAgreement() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_agreement));
    transactFragment(AgreementFragment.newInstance());
  }

  private void loadProfile() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_profile));
    transactFragment(ProfileFragment.newInstance());
  }

  private void loadTransaction() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_transactions));
    transactFragment(TransactionsFragment.newInstance());
  }

  private void loadPortfolio() {
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_portfolio));
    transactFragment(PortfolioFragment.newInstance());
  }

  private void loadCommission(){
    binding.appbar.toolbar.setTitle(getContext().getString(R.string.menu_commission));
    transactFragment(CommissionFragment.newInstance());
  }

  private void logout(){
    Intent logout = new Intent(getContext(), LogoutActivity.class);
    context.startActivity(logout);
  }

  private void transactFragment(Fragment fragment) {
    try {
      ActivityUtils.replaceFragmentInActivity(((MainActivity)context).getSupportFragmentManager(), fragment,
              R.id.content_wrapper);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void navigation(int menu){
    switch (menu){
      case R.id.nav_home : loadHome(); break;
      case R.id.nav_about : loadAbout(); break;
      case R.id.nav_profile : loadProfile(); break;
      case R.id.nav_transaction : loadTransaction(); break;
      case R.id.nav_portfolio : loadPortfolio(); break;
      case R.id.nav_network : loadNetwork(); break;
      case R.id.nav_agreement : loadAgreement(); break;
      case R.id.nav_funds : loadFunds(); break;
      case R.id.nav_lounge : loadLounge(); break;
      case R.id.nav_commission : loadCommission(); break;
      case R.id.nav_logout : logout();
    }
  }

  private void loading(boolean load) {
    loadingState.set(load);
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
