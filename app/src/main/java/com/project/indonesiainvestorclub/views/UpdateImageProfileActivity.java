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

    private UpdateImageProfileActivityBinding binding;
    private UpdateImageProfileViewModel viewModel;
    static final int REQUEST_GALLERY_PHOTO = 102;
    private ImagePresenter mPresenter;
    Uri photoURI;
    File mPhotoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_image_profile_activity);
        viewModel = new UpdateImageProfileViewModel(this, binding);
        binding.setViewModel(viewModel);
//        mPresenter = new ImagePresenter((ImageContract.View) this);
    }

    public void chooseGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_GALLERY_PHOTO) {
//                Uri selectedImage = data.getData();
//                String mPhotoPath = getRealPathFromUri(selectedImage);
////                mPresenter.showPreview(mPhotoPath);
////                binding.setImagePick(mPhotoPath);
////                binding.ivPickImage.setImageURI(selectedImage);
////                binding.ivPickImage.setImageBitmap(BitmapFactory.decodeFile(mPhotoPath));
//                viewModel.start(mPhotoPath);
//                Toast.makeText(this, "Image Size" +getReadableFileSize(mPhotoPath.length()),Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                try {
                    mPhotoFile = FileUtil.from(this, data.getData());
//                    Bitmap mBitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
//                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bao);
//                    binding.ivPickImage.setImageBitmap(mBitmap);
//                    binding.setImagePick(String.valueOf(mBitmap));
//                    viewModel.start(String.valueOf(mPhotoFile));
                    viewModel.start(mPhotoFile);
//                    Toast.makeText(this, "Image Size " +getReadableFileSize(mPhotoFile.length()),Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, "Image Size " +getReadableFileSize(mPhotoFile.length())+" || Path Folder "+mPhotoFile.getPath(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, "Image Size " +getReadableFileSize(mPhotoFile.length())+" || Name Image "+mPhotoFile.getName(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, "Image Size " +getReadableFileSize(mPhotoFile.length())+" || Image Resize "+getReadableFileSize(mBitmap.getByteCount())+" || Path Folder "+mPhotoFile.getPath(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_GALLERY_PHOTO) {
//                Uri selectedImage = data.getData();
//                try {
//                    mPhotoFile = FileUtil.from(this, selectedImage);
////                    binding.ivPickImage.setImageURI(Uri.fromFile(mPhotoFile));
////                    binding.ivPickImage.setImageBitmap(BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()));
//                    Bitmap mBitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
//                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bao);
//                    binding.ivPickImage.setImageBitmap(mBitmap);
//                    Toast.makeText(this, "Image Size" +getReadableFileSize(mPhotoFile.length()),Toast.LENGTH_LONG).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.0#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}