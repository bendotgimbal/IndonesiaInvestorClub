package com.project.indonesiainvestorclub.custom;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.project.indonesiainvestorclub.R;

public class PopUpBox {

  public static final String TAG = "PopUpBox";

  private int color = -1;
  private Context context;
  private Handler handler;
  private boolean cancelable = false;
  private View customView;
  private String title;
  private String message;
  private long duration = 7000;
  private ButtonHolder positiveButton;
  private ButtonHolder negativeButton;
  private ButtonHolder neutralButton;
  private DialogInterface.OnDismissListener onDismissListener;
  private DialogInterface.OnCancelListener onCancelListener;

  private AlertDialog alertDialog;
  private Runnable postShowingRunnable = new Runnable() {
    @Override
    public void run() {
      try {
        alertDialog.dismiss();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  };
  private DialogInterface.OnDismissListener dismissListener = dialogInterface -> {
    if (handler != null) handler.removeCallbacks(postShowingRunnable);
    if (onDismissListener != null) onDismissListener.onDismiss(dialogInterface);
  };
  private DialogInterface.OnCancelListener cancelListener = dialogInterface -> {
    if (onCancelListener != null) onCancelListener.onCancel(dialogInterface);
  };

  public PopUpBox(Context context) {
    this.context = context;
    this.handler = new Handler();
  }

  public static PopUpBox newInstance(Context context) {
    PopUpBox instance = new PopUpBox(context);
    return instance;
  }

  public void setCancelable(boolean cancelable) {
    this.cancelable = cancelable;
  }

  public PopUpBox withCancelable(boolean cancelable) {
    setCancelable(cancelable);
    return this;
  }

  public void setCustomView(View customView) {
    this.customView = customView;
  }

  public PopUpBox withCustomView(View customView) {
    setCustomView(customView);
    return this;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PopUpBox withTitle(String title) {
    setTitle(title);
    return this;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public PopUpBox withMessage(String message) {
    setMessage(message);
    return this;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public PopUpBox withDuration(long duration) {
    setDuration(duration);
    return this;
  }

  private void setPositiveButton(ButtonHolder positiveButton) {
    this.positiveButton = positiveButton;
  }

  public void setPositiveButton(String label, AlertDialog.OnClickListener listener) {
    setPositiveButton(new ButtonHolder().withLabel(label).withListener(listener));
  }

  public PopUpBox withPositiveButton(String label, AlertDialog.OnClickListener listener) {
    setPositiveButton(label, listener);
    return this;
  }

  private void setNegativeButton(ButtonHolder negativeButton) {
    this.negativeButton = negativeButton;
  }

  public void setNegativeButton(String label, AlertDialog.OnClickListener listener) {
    setNegativeButton(new ButtonHolder().withLabel(label).withListener(listener));
  }

  public PopUpBox withNegativeButton(String label, AlertDialog.OnClickListener listener) {
    setNegativeButton(label, listener);
    return this;
  }

  private void setNeutralButton(ButtonHolder neutralButton) {
    this.neutralButton = neutralButton;
  }

  public void setNeutralButton(String label, AlertDialog.OnClickListener listener) {
    setNeutralButton(new ButtonHolder().withLabel(label).withListener(listener));
  }

  public PopUpBox withNeutralButton(String label, AlertDialog.OnClickListener listener) {
    setNeutralButton(label, listener);
    return this;
  }

  public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
    this.dismissListener = dismissListener;
  }

  public PopUpBox withDismissListener(DialogInterface.OnDismissListener dismissListener) {
    setDismissListener(dismissListener);
    return this;
  }

  public void setCancelListener(DialogInterface.OnCancelListener cancelListener) {
    this.cancelListener = cancelListener;
  }

  public PopUpBox withCancelListener(DialogInterface.OnCancelListener cancelListener) {
    setCancelListener(cancelListener);
    return this;
  }

  public AlertDialog show() throws Exception {
    if (context == null) return null;
    if (!(context instanceof Activity)) return null;
    if (color < 0) color = ContextCompat.getColor(context, R.color.colorAccent);

    if (alertDialog != null) {
      alertDialog.dismiss();
    }

    handler.removeCallbacks(postShowingRunnable);

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setCancelable(cancelable);

    if (validString(title)) builder.setTitle(title);
    if (validString(message)) builder.setMessage(message);

    if (customView != null) {
      if (customView.getParent() != null) {
        ((ViewGroup) customView.getParent()).removeView(customView);
      }
      builder.setView(customView);
    }

    if (positiveButton != null) {
      builder.setPositiveButton(positiveButton.getLabel(), positiveButton.getListener());
    }
    if (negativeButton != null) {
      builder.setNegativeButton(negativeButton.getLabel(), negativeButton.getListener());
    }
    if (neutralButton != null) {
      builder.setNeutralButton(neutralButton.getLabel(), neutralButton.getListener());
    }

    alertDialog = builder.create();
    alertDialog.show();

    Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
    if (positive != null) positive.setTextColor(color);
    Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    if (negative != null) negative.setTextColor(color);
    Button neutral = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
    if (neutral != null) neutral.setTextColor(color);

    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);

    if (duration > 50) handler.postDelayed(postShowingRunnable, duration);

    alertDialog.setOnDismissListener(dismissListener);
    alertDialog.setOnCancelListener(cancelListener);

    return alertDialog;
  }

  private boolean validString(String text) {
    if (text == null) return false;
    if (text.length() < 1) return false;
    return true;
  }

  class ButtonHolder {
    private String label;
    private AlertDialog.OnClickListener listener;

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public ButtonHolder withLabel(String label) {
      setLabel(label);
      return this;
    }

    public AlertDialog.OnClickListener getListener() {
      return listener;
    }

    public void setListener(AlertDialog.OnClickListener listener) {
      this.listener = listener;
    }

    public ButtonHolder withListener(AlertDialog.OnClickListener listener) {
      setListener(listener);
      return this;
    }
  }
}
