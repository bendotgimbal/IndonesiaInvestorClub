package com.project.indonesiainvestorclub.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.HomeFragmentBinding;
import com.project.indonesiainvestorclub.viewModels.HomeViewModel;

public class HomeFragment extends BaseFragment {

  private HomeFragmentBinding binding;
  private HomeViewModel viewModel;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @SuppressLint("NewApi")
  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
//    showAboutDialog();
    return binding.getRoot();
  }

  @Override protected void initDataBinding() {
    viewModel = new HomeViewModel(this.getContext(), binding);
    binding.setViewModel(viewModel);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void showAboutDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle(R.string.about_app);
    builder.setView(R.layout.about_dialog);
    builder.setNegativeButton("Close", (dialogInterface, i) -> {
      // dismiss dialog
      dialogInterface.dismiss();
    });
    builder.show();
  }
}
