package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfIdActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.helper.StringHelper;
import com.project.indonesiainvestorclub.models.response.UpdateImageProofOfIDRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.ProfileEditActivity;
import com.project.indonesiainvestorclub.views.UpdateImageProofOfIDActivity;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.project.indonesiainvestorclub.views.UpdateImageProofOfIDActivity.REQUEST_GALLERY_PHOTO;

public class UpdateImageProofOfIDViewModel extends BaseViewModelWithCallback {

  private final static String UPLOAD_MESSAGE = "File Upload Successfull";

  private UpdateImageProofOfIdActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableBoolean uploadImageButtonEnable;
  public File imageFileProofOfID;

  public UpdateImageProofOfIDViewModel(Context context,
      UpdateImageProofOfIdActivityBinding binding) {
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
      imageFileProofOfID = fileimage;
    }
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void postImage(File fileimageProofOfID) {
    loading(true);

    Toast.makeText(context, "Image Name " + fileimageProofOfID.getName(), Toast.LENGTH_LONG).show();
    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileimageProofOfID);
    MultipartBody.Part bodyFile =
        MultipartBody.Part.createFormData("user_id", fileimageProofOfID.getName(), requestFile);

    Disposable disposable =
        ServiceGenerator.service.uploadProofIDRequest(bodyFile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<UpdateImageProofOfIDRes>>(this,
                () -> postImage(fileimageProofOfID)) {
              @Override
              protected void onSuccess(Response<UpdateImageProofOfIDRes> updateResponse) {
                loading(false);
                onSuccessUpdate(updateResponse.body());
              }

              @Override public void onNext(Response<UpdateImageProofOfIDRes> updateResResponse) {
                super.onNext(updateResResponse);
                loading(false);
              }
            });
    compositeDisposable.add(disposable);
  }

  private void onSuccessUpdate(UpdateImageProofOfIDRes updateImageProofOfIDRes) {
    if (updateImageProofOfIDRes != null && updateImageProofOfIDRes.getUpload()
        .equalsIgnoreCase(UPLOAD_MESSAGE)) {
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
    ((UpdateImageProofOfIDActivity) context).startActivityForResult(pickPhoto,
        REQUEST_GALLERY_PHOTO);
  }

  @SuppressWarnings("unused")
  public void onButtonUploadImageClick(View view) {
    Toast.makeText(context, "Button Upload ", Toast.LENGTH_LONG).show();
    postImage(imageFileProofOfID);
  }

  @Override public void hideLoading() {
    loading(false);
  }
}