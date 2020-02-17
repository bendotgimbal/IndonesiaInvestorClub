package com.project.indonesiainvestorclub.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageTransferActivityBinding;
import com.project.indonesiainvestorclub.helper.FileUtil;
import com.project.indonesiainvestorclub.viewModels.UpdateImageTransferViewModel;

import java.io.File;
import java.io.IOException;

import io.reactivex.annotations.Nullable;

public class UpdateImageTransferActivity extends BaseActivity {

    public static final int REQUEST_GALLERY_PHOTO = 102;

    private UpdateImageTransferActivityBinding binding;
    private UpdateImageTransferViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_image_transfer_activity);
        viewModel = new UpdateImageTransferViewModel(this, binding);
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