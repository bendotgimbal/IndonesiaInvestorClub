package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageTransferActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.views.UpdateImageTransferActivity;

import java.io.File;

import static com.project.indonesiainvestorclub.views.UpdateImageProfileActivity.REQUEST_GALLERY_PHOTO;

public class UpdateImageTransferViewModel extends BaseViewModelWithCallback {

    private UpdateImageTransferActivityBinding binding;
    public ObservableBoolean loadingState;

    public UpdateImageTransferViewModel(Context context, UpdateImageTransferActivityBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);
        binding.ivPickImage.setImageResource(R.drawable.ic_camera);

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
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ((UpdateImageTransferActivity) context).startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    @Override public void hideLoading() {
        loading(false);
    }
}