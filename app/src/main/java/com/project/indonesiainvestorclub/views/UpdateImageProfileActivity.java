package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;
import com.project.indonesiainvestorclub.helper.FileUtil;
import com.project.indonesiainvestorclub.viewModels.UpdateImageProfileViewModel;

import java.io.File;
import java.io.IOException;

import io.reactivex.annotations.Nullable;

public class UpdateImageProfileActivity extends BaseActivity {

  public static final int REQUEST_GALLERY_PHOTO = 102;
  private static final String UPLOAD_TITLE = "Upload Avatar";

  private UpdateImageProfileActivityBinding binding;
  private UpdateImageProfileViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setSupportActionBar(binding.toolbar);
    if (getSupportActionBar() != null){
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setTitle(UPLOAD_TITLE);
      binding.toolbar.setTitleTextColor(Color.WHITE);
    }

  }

  @Override
  public void initDataBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.update_image_profile_activity);
    viewModel = new UpdateImageProfileViewModel(this, binding);
    binding.setViewModel(viewModel);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == REQUEST_GALLERY_PHOTO) {
        try {
          File mPhotoFile = FileUtil.from(this, data.getData());
          viewModel.start(mPhotoFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
}