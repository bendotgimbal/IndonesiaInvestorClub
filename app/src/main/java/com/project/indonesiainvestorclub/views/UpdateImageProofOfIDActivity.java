package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfIdActivityBinding;
import com.project.indonesiainvestorclub.helper.FileUtil;
import com.project.indonesiainvestorclub.viewModels.UpdateImageProofOfIDViewModel;

import java.io.File;
import java.io.IOException;

import io.reactivex.annotations.Nullable;

public class UpdateImageProofOfIDActivity extends BaseActivity {

    private UpdateImageProofOfIdActivityBinding binding;
    private UpdateImageProofOfIDViewModel viewModel;
    static final int REQUEST_GALLERY_PHOTO = 102;
    File mPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_image_proof_of_id_activity);
        viewModel = new UpdateImageProofOfIDViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    public void chooseGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                try {
                    mPhotoFile = FileUtil.from(this, data.getData());
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