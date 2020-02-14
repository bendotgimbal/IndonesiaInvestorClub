package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;
import com.project.indonesiainvestorclub.helper.FileUtil;
import com.project.indonesiainvestorclub.helper.ImageContract;
import com.project.indonesiainvestorclub.helper.ImagePresenter;
import com.project.indonesiainvestorclub.viewModels.UpdateImageProfileViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import io.reactivex.annotations.Nullable;

public class UpdateImageProfileActivity extends BaseActivity {

  public static final int REQUEST_GALLERY_PHOTO = 102;

  private UpdateImageProfileActivityBinding binding;
  private UpdateImageProfileViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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