package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.views.UpdateImageProfileActivity;

import java.io.File;

public class UpdateImageProfileViewModel extends BaseViewModelWithCallback {

  private UpdateImageProfileActivityBinding binding;
  public ObservableBoolean loadingState;

  public UpdateImageProfileViewModel(Context context, UpdateImageProfileActivityBinding binding) {
    super(context);
    this.binding = binding;
    loadingState = new ObservableBoolean(false);
  }

  public void start(File fileimage) {
    if (fileimage == null) return;
    ImageHelper.loadLocalImage(binding.ivPickImage, fileimage.getAbsolutePath());
    Toast.makeText(context, "Image Name " + fileimage.getName(), Toast.LENGTH_LONG).show();
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  @SuppressWarnings("unused")
  public void onButtonUpdateImageClick(View view) {
    ((UpdateImageProfileActivity) getContext()).chooseGallery();
  }

  @Override public void hideLoading() {
    loading(false);
  }
}