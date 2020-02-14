package com.project.indonesiainvestorclub.helper;

import android.net.Uri;

import java.io.File;

public class ImagePresenter implements ImageContract.Presenter {
  private final ImageContract.View view;

  public ImagePresenter(ImageContract.View view) {
    this.view = view;
  }

  @Override
  public void cameraClick() {
    if (!view.checkPermission()) {
      view.showPermissionDialog();
      return;
    }
    if (view.availableDisk() <= 5) {
      view.showNoSpaceDialog();
      return;
    }
    File file = view.newFile();
    if (file == null) {
      view.showErrorDialog();
      return;
    }
    view.startCamera(file);
  }

  @Override
  public void ChooseGalleryClick() {
    if (!view.checkPermission()) {
      view.showPermissionDialog();
      return;
    }
    view.chooseGallery();
  }

  @Override
  public void saveImage(Uri uri) { }

  @Override
  public void permissionDenied() {
    view.showPermissionDialog();
  }

  @Override
  public void showPreview(String mFilePath) {
    view.displayImagePreview(mFilePath);
  }

  @Override
  public void showPreview(Uri mFileUri) {
    view.displayImagePreview(mFileUri);
  }
}