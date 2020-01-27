package com.project.indonesiainvestorclub.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
//    viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
//    final TextView textView = binding.getRoot().findViewById(R.id.about_app);
//    viewModel.getText().observe(this, new Observer<String>() {
//      @Override
//      public void onChanged(String s) {
//        textView.setText(s);
//        Toast.makeText(getContext(), "Isi "+s, Toast.LENGTH_SHORT).show();
//      }
//    });
//    Toast.makeText(getContext(), "Isi "+viewModel.getText().getValue(), Toast.LENGTH_SHORT).show();
    showAboutDialog();
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
    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        // dismiss dialog
        dialogInterface.dismiss();
      }
    });
    builder.show();
  }
}
