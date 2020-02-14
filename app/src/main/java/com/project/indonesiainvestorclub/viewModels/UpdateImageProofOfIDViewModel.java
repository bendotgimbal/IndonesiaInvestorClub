package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;

import com.project.indonesiainvestorclub.databinding.UpdateImageProofOfIdActivityBinding;
import com.project.indonesiainvestorclub.helper.ImageHelper;
import com.project.indonesiainvestorclub.views.UpdateImageProofOfIDActivity;

import java.io.File;

public class UpdateImageProofOfIDViewModel extends BaseViewModelWithCallback {

    private UpdateImageProofOfIdActivityBinding binding;
    public ObservableBoolean loadingState;

    public UpdateImageProofOfIDViewModel(Context context, UpdateImageProofOfIdActivityBinding binding) {
        super(context);
        this.binding = binding;
        loadingState = new ObservableBoolean(false);

    }

    public void start(File fileimage) {
        if (fileimage == null) return;
        ImageHelper.loadLocalImage(binding.ivPickImage, fileimage.getAbsolutePath());
        Toast.makeText(context, "Image Name " + fileimage.getName(), Toast.LENGTH_LONG).show();
    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    @SuppressWarnings("unused")
    public void onButtonUpdateImageClick(View view) {
        ((UpdateImageProofOfIDActivity) getContext()).chooseGallery();
    }

    @Override public void hideLoading() {
        loading(false);
    }
}