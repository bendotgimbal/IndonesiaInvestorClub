package com.project.indonesiainvestorclub.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Fade;


public class ActivityUtils {

  private static final long FADE_DEFAULT_TIME = 250;

  public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                               @NonNull Fragment fragment, int frameId) throws Exception {

    FragmentTransaction transaction = fragmentManager.beginTransaction();

    Fade enterFade = new Fade();
    enterFade.setDuration(FADE_DEFAULT_TIME);
    fragment.setEnterTransition(enterFade);
    transaction.replace(frameId, fragment);
    transaction.commit();
  }

}
