package com.project.indonesiainvestorclub.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class ViewBindings {

    @BindingAdapter(value = {"alertTitle", "alertMessage",
            "alertPositiveButtonText", "alertPositiveButtonOnClick",
            "alertNeutralButtonText", "alertNeutralButtonOnClick",
            "alertNegativeButtonText", "alertNegativeButtonOnClick",
            "alertMessageAttrChanged"}, requireAll = false)
    public static void showAlertDialog(View view, @Nullable CharSequence title,
                                       @Nullable CharSequence message,
                                       CharSequence positiveButtonText,
                                       DialogInterface.OnClickListener positiveButtonOnClick,
                                       CharSequence neutralButtonText,
                                       DialogInterface.OnClickListener neutralButtonOnClick,
                                       CharSequence negativeButtonText,
                                       DialogInterface.OnClickListener negativeButtonOnClick,
                                       @Nullable InverseBindingListener inverseBindingListener) {
        if (message != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setMessage(message);
            if (title != null) {
                alertDialog.setTitle(title);
            }
            if (positiveButtonOnClick != null) {
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                        (positiveButtonText == null) ? view.getContext().getString(android.R.string.ok) : positiveButtonText,
                        positiveButtonOnClick);
            }
            if (neutralButtonOnClick != null && neutralButtonText != null) {
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, neutralButtonText, neutralButtonOnClick);
            }
            if (negativeButtonOnClick != null) {
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                        (negativeButtonText == null) ? view.getContext().getString(android.R.string.cancel) : negativeButtonText,
                        negativeButtonOnClick);
            }
            if (inverseBindingListener != null) {
                alertDialog.setOnDismissListener(dialogInterface -> inverseBindingListener.onChange());
            }
            alertDialog.show();
        }
    }

    @InverseBindingAdapter(attribute = "alertMessage")
    public static String getAlertMessage(View view) {
        return null; // clear message
    }

}