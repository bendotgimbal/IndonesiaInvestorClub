package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.models.response.GlobalResponse;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.UpdateImageProfileActivity;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.project.indonesiainvestorclub.views.UpdateImageProfileActivity.REQUEST_GALLERY_PHOTO;

public class UpdateImageProfileViewModel extends BaseViewModelWithCallback {

  private UpdateImageProfileActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableBoolean uploadImageButtonEnable;
  private File imageFileProfile;

  public UpdateImageProfileViewModel(Context context, UpdateImageProfileActivityBinding binding) {
    super(context);
    this.binding = binding;

    loadingState = new ObservableBoolean(false);
    uploadImageButtonEnable = new ObservableBoolean(false);
  }

  public void start(File fileimage) {
    if (fileimage == null) return;

    uploadImageButtonEnable.set(true);
    ImageHelper.loadLocalImage(binding.ivPickImage, fileimage.getAbsolutePath());
    imageFileProfile = fileimage;
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void postImage(File fileimageProfile) {
    loading(true);

    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileimageProfile);
    MultipartBody.Part bodyFile =
        MultipartBody.Part.createFormData("user_avatar", fileimageProfile.getName(), requestFile);

    Disposable disposable = ServiceGenerator.service.uploadProfileRequest(bodyFile)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(
            new CallbackWrapper<Response<GlobalResponse>>(this, () -> postImage(fileimageProfile)) {
              @Override
              protected void onSuccess(Response<GlobalResponse> uploadResponse) {
                if (uploadResponse.body() != null) {
                  loading(false);
                  updateImageSuccess(uploadResponse.body());
                }
              }
            });
    compositeDisposable.add(disposable);
  }

  private void updateImageSuccess(GlobalResponse response) {
    if (response.getStatus()) {
      Toast.makeText(getContext(), response.getMessage().replaceAll("[|]", ""), Toast.LENGTH_SHORT).show();
      ((UpdateImageProfileActivity)context).setResult(RESULT_OK);
      ((UpdateImageProfileActivity)context).finish();
    } else {
      Toast.makeText(getContext(), "Terjadi kesalahan :\n" + response.getMessage(),
          Toast.LENGTH_SHORT).show();
    }
  }

  @SuppressWarnings("unused")
  public void onButtonUpdateImageClick(View view) {
    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    ((UpdateImageProfileActivity) context).startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
  }

  @SuppressWarnings("unused")
  public void onButtonUploadImageClick(View view) {
    postImage(imageFileProfile);
  }

  @Override public void hideLoading() {
    loading(false);
  }
}