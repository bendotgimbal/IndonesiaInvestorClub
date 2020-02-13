package com.project.indonesiainvestorclub.helper;

import android.net.Uri;

import java.io.File;

public class ImageContract {
    public interface View {
        boolean checkPermission();
        void showPermissionDialog();
        File getFilePath();
        void openSettings();
        void startCamera(File file);
        void chooseGallery();
        void showNoSpaceDialog();
        int availableDisk();
        File newFile();
        void showErrorDialog();
        void displayImagePreview(String mFilePath);
        void displayImagePreview(Uri mFileUri);
        String getRealPathFromUri(Uri contentUri);
    }
    interface Presenter {
        void cameraClick();
        void ChooseGalleryClick();
        void saveImage(Uri uri);
        void permissionDenied();
        void showPreview(String mFilePath);
        void showPreview(Uri mFileUri);
    }
}