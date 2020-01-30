package com.project.indonesiainvestorclub.viewModels;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import com.project.indonesiainvestorclub.R;
import com.project.indonesiainvestorclub.custom.PopUpBox;
import com.project.indonesiainvestorclub.databinding.SubSimpleDialogBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.views.BaseActivity;

public class SimpleMessageDialogViewModel extends BaseViewModel {

  public final ObservableInt titleVisibility;
  public final ObservableInt messageVisibility;
  public final ObservableField<String> titleText;
  public final ObservableField<String> messageText;

  private SubSimpleDialogBinding binding;
  private View view;
  private AlertDialog alertDialog;
  private boolean finish = false;
  private boolean interupable = true;

  private ActionInterface.CustomDialogActionButton listener;

  public SimpleMessageDialogViewModel(Context context, ActionInterface.CustomDialogActionButton listener) {
    super(context);

    this.titleVisibility = new ObservableInt(View.GONE);
    this.titleText = new ObservableField<>("");

    this.messageVisibility = new ObservableInt(View.GONE);
    this.messageText = new ObservableField<>("");

    this.listener = listener;

    if (!(getContext() instanceof Activity)) return;
    Activity activity = (Activity) getContext();

    try {
      view = activity.getLayoutInflater().inflate(R.layout.sub_simple_dialog, null, false);
    } catch (OutOfMemoryError e) {
      e.printStackTrace();
      Toast.makeText(context, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
      return;
    }

    binding = DataBindingUtil.bind(view);
    if (binding != null) {
      try {
        binding.setViewModel(this);
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }
  }

  public void show(@Nullable String title, @Nullable String message) {
    titleVisibility.set(View.GONE);
    titleText.set("");

    messageVisibility.set(View.GONE);
    messageText.set("");

    if (title != null) {
      titleText.set(title);
      titleVisibility.set(View.VISIBLE);
    }

    if (message != null) {
      messageText.set(message);
      messageVisibility.set(View.VISIBLE);
    }

    PopUpBox popUpBox = PopUpBox.newInstance(getContext())
        .withDuration(0)
        .withCustomView(view)
        .withCancelable(interupable)
        .withPositiveButton(getContext().getString(R.string.label_ok), (dialog, which) -> {
          dialog.dismiss();
          if (finish) ((BaseActivity) getContext()).finish();
        });
    if (alertDialog != null) alertDialog.dismiss();
    try {
      alertDialog = popUpBox.show();
    } catch (Exception e) {
      e.printStackTrace();
      ((BaseActivity) context).showToast("Terjadi kesalahan", Toast.LENGTH_LONG);
    }
  }

  public void show(@Nullable String title, @Nullable String message, @Nullable ActionInterface.CustomDialogActionButton listener) {
    titleVisibility.set(View.GONE);
    titleText.set("");

    messageVisibility.set(View.GONE);
    messageText.set("");

    if (title != null) {
      titleText.set(title);
      titleVisibility.set(View.VISIBLE);
    }

    if (message != null) {
      messageText.set(message);
      messageVisibility.set(View.VISIBLE);
    }

    PopUpBox popUpBox = PopUpBox.newInstance(getContext())
        .withDuration(0)
        .withCustomView(view)
        .withCancelable(interupable)
        .withPositiveButton(getContext().getString(R.string.label_ok), (dialog, which) -> {
          if (listener != null){
            listener.onClickPositiveButton();
          }
          dialog.dismiss();
          if (finish) ((BaseActivity) getContext()).finish();
        });
    if (alertDialog != null) alertDialog.dismiss();
    try {
      alertDialog = popUpBox.show();
    } catch (Exception e) {
      e.printStackTrace();
      ((BaseActivity) context).showToast("Terjadi kesalahan", Toast.LENGTH_LONG);
    }
  }

}
