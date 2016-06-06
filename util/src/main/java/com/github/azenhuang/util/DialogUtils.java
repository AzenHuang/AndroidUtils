package com.github.azenhuang.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class DialogUtils {

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }



    public static AlertDialog.Builder dialogBuilder(Context context, int titleId, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (titleId > 0) {
            builder.setTitle(titleId);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int titleResId, int msgResId) {
        String title = titleResId > 0 ? context.getResources().getString(titleResId) : null;
        String msg = msgResId > 0 ? context.getResources().getString(msgResId) : null;
        return dialogBuilder(context, title, msg);
    }

    public static Dialog showTips(Context context, String title, String msg) {
        return showTips(context, title, msg, null, null);
    }

    public static Dialog showTips(Context context, int titleResId, int msgResId) {
        return showTips(context, context.getString(titleResId), context.getString(msgResId));
    }

    public static Dialog showTips(Context context, int titleResId, int msgResId, int btn, DialogInterface.OnDismissListener dismissListener) {
        return showTips(context, context.getString(titleResId), context.getString(msgResId), context.getString(btn), dismissListener);
    }

    public static Dialog showTips(Context context, String title, String msg, String btn, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, msg);
        builder.setCancelable(true);
        builder.setPositiveButton(btn, null);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }
}
