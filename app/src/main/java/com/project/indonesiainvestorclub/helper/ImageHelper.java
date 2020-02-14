package com.project.indonesiainvestorclub.helper;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ImageHelper {

  private static final String LOG_TAG = ImageHelper.class.getCanonicalName();

  private static GlideUrl getGlideUrl(String imageUrl) {
    return new GlideUrl(imageUrl);
  }

  public static void loadImage(final ImageView iv, final String url) {
    try {
      Glide.with(iv.getContext().getApplicationContext())
          .load(getGlideUrl(url))
          .listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                Target<Drawable> target, boolean isFirstResource) {
              Log.e(LOG_TAG, "Failed to load >> " + url);
              return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                DataSource dataSource, boolean isFirstResource) {
              Log.d(LOG_TAG, "Success to load >> " + url);
              return false;
            }
          })
          .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)
              .dontAnimate()
              .skipMemoryCache(true))
          .into(iv);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  public static void loadLocalImage(final ImageView view, final String imagePath) {
    try {
      Glide.with(view.getContext())
          .load(imagePath)
          .listener(new RequestListener<Drawable>() {
            @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
                Target<Drawable> target, boolean isFirstResource) {
              return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                DataSource dataSource, boolean isFirstResource) {
              return false;
            }
          })
          .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)
              .dontAnimate()
              .skipMemoryCache(true))
          .into(view);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }
}
