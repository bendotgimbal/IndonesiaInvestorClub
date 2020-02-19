package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfBankActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.models.response.UpdateImageProofOfBankRes;
import com.project.indonesiainvestorclub.views.ProfileEditActivity;
import com.project.indonesiainvestorclub.views.UpdateImageProofOfBankActivity;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.project.indonesiainvestorclub.views.UpdateImageProofOfIDActivity.REQUEST_GALLERY_PHOTO;

public class UpdateImageProofOfBankViewModel extends BaseViewModelWithCallback {

    private final static String UPLOAD_MESSAGE = "File Upload Successfull";

    private UpdateImageProofOfBankActivityBinding binding;
    public ObservableBoolean loadingState;
    public ObservableBoolean uploadImageButtonEnable;
    public File imageFileProofOfBank;

    public UpdateImageProofOfBankViewModel(Context context, UpdateImageProofOfBankActivityBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);
        uploadImageButtonEnable = new ObservableBoolean(false);
        binding.ivPickImage.setImageResource(R.drawable.ic_camera);

        this.binding.btnUploadImage.setBackgroundColor(Color.rgb(239, 220, 238));
    }

    public void start(File fileimage) {
        if (fileimage == null) return;
        if (fileimage != null) {
            this.binding.btnUploadImage.setBackgroundColor(Color.rgb(175, 76, 168));
            uploadImageButtonEnable.set(true);
            ImageHelper.loadLocalImage(binding.ivPickImage, fileimage.getAbsolutePath());
            Toast.makeText(context, "Image Name " + fileimage.getName(), Toast.LENGTH_LONG).show();
        }
    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    private void onSuccessUpdate(UpdateImageProofOfBankRes updateImageProofOfBankRes){
        if (updateImageProofOfBankRes != null && updateImageProofOfBankRes.getUpload().equalsIgnoreCase(UPLOAD_MESSAGE)) {
            Toast.makeText(getContext(), "Selamat Anda Berhasil Update", Toast.LENGTH_SHORT).show();
            ((ProfileEditActivity) context).setResult(RESULT_OK);
            ((ProfileEditActivity) context).finish();
        } else {
            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onButtonUpdateImageClick(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ((UpdateImageProofOfBankActivity) context).startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    @SuppressWarnings("unused")
    public void onButtonUploadImageClick(View view) {
        Toast.makeText(context, "Button Upload ", Toast.LENGTH_LONG).show();
    }

    @Override public void hideLoading() {
        loading(false);
    }
}