package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.ObservableBoolean;

import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.databinding.UpdateImageProfileActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.models.response.UpdateImageProfileRes;
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

import static com.project.indonesiainvestorclub.views.UpdateImageProfileActivity.REQUEST_GALLERY_PHOTO;

public class UpdateImageProfileViewModel extends BaseViewModelWithCallback {

  private UpdateImageProfileActivityBinding binding;
  public ObservableBoolean loadingState;
  public ObservableBoolean uploadImageButtonEnable;
  public File imageFileProfile;

  public UpdateImageProfileViewModel(Context context, UpdateImageProfileActivityBinding binding) {
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
      imageFileProfile = fileimage;
    }
  }

  private void loading(boolean load) {
    loadingState.set(load);
  }

  //API CALL
  private void postImage(File fileimageProfile) {
//    loading(true);

    Toast.makeText(context, "Image Name " + fileimageProfile.getName(), Toast.LENGTH_LONG).show();
    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),fileimageProfile);
    MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("user_avatar",fileimageProfile.getName(),requestFile);

    Disposable disposable = ServiceGenerator.service.uploadProfileRequest(bodyFile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, () -> postImage(fileimageProfile)) {
              @Override
              protected void onSuccess(Response<JsonElement> jsonElementResponse) {
                if (jsonElementResponse.body() != null) {
                  loading(false);
                  readUpdateImageJSON(jsonElementResponse.body());
                }
              }
            });
    compositeDisposable.add(disposable);
  }

  private void readUpdateImageJSON(JsonElement response){
//    Toast.makeText(getContext(), "Selamat Berhasil Upload", Toast.LENGTH_SHORT).show();
    Toast.makeText(getContext(), "Response "+response, Toast.LENGTH_SHORT).show();
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
    Toast.makeText(context, "Button Upload ", Toast.LENGTH_LONG).show();
//    Toast.makeText(context, "Button Upload "+imageFileProfile.getName(), Toast.LENGTH_LONG).show();
    postImage(imageFileProfile);
  }

  @Override public void hideLoading() {
    loading(false);
  }
}