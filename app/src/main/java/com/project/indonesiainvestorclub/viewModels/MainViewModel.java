package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.ActivityMainBinding;
import com.project.indonesiainvestorclub.databinding.NavHeaderMainBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.helper.SharedPreferenceHelper;
import com.project.indonesiainvestorclub.models.About;
import com.project.indonesiainvestorclub.models.Childs;
import com.project.indonesiainvestorclub.models.response.AboutRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.LoginActivity;
import com.project.indonesiainvestorclub.views.MainActivity;
import com.project.indonesiainvestorclub.views.SignUpActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

public class MainViewModel extends BaseViewModelWithCallback {

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

    getAbout();
  }

  //API CALL
  private void getAbout() {
    loading(true);

    Disposable disposable = ServiceGenerator.service.aboutRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getAbout) {
          @Override
          protected void onSuccess(Response<JsonElement> response) {
            if (response.body() != null) {
              readAboutJSON(response.body());
            }
          }
        });
    compositeDisposable.add(disposable);
  }

  private void readAboutJSON(JsonElement response) {
    JSONObject jsonObject;
    try {
      AboutRes aboutRes;

      jsonObject = new JSONObject(response.toString());
      JSONObject objectAbout = jsonObject.getJSONObject("ABOUT_IIC");

      List<About> about = new ArrayList<>();

      for (int i = 1; i <= objectAbout.length(); i++) {
        JSONObject objAbout = objectAbout.getJSONObject(i + "");

        String parent = objAbout.getString("Parent");
        List<Childs> childsList = new ArrayList<>();

        if (objAbout.has("Childs")) {

          //Correction childObject get value from objAbout not from objectAbout
          JSONObject childsObject = objAbout.getJSONObject("Childs");

          for (int o = 1; o <= childsObject.length(); o++) {
            Childs childs = new Childs(childsObject.getString(o + ""));
            childsList.add(childs);
          }
        } else {
          Childs childs = new Childs("");
          childsList.add(childs);
        }

        about.add(new About(parent, childsList));
      }

      aboutRes = new AboutRes(about);

      showAbout(aboutRes);
      hideLoading();
    } catch (JSONException e) {
      e.printStackTrace();
      hideLoading();
    }
  }

  private void showAbout(AboutRes response) {
    hideLoading();
    if (response == null) return;
    if (response.getAbout() == null) return;


    StringBuilder about = new StringBuilder();

    for (int i = 0; i < response.getAbout().size(); i++) {
      StringBuilder temp;
      temp = new StringBuilder(response.getAbout().get(i).getParent() + "\n\n");

      if (response.getAbout().get(i).getChilds() != null) {
        for (int o = 0; o < response.getAbout().get(i).getChilds().size(); o++) {
          temp.append(response.getAbout().get(i).getChilds().get(o).getPhrase()).append("\n\n");
        }
      }

      about.append(temp);
    }

    ((MainActivity)getContext()).showAlertDialog("Warnings", about.toString());

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
  public void signupScreen(View view) {
    Intent i = new Intent(context, SignUpActivity.class);
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
