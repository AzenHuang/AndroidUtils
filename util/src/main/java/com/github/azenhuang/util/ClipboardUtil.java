package com.github.azenhuang.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author MaTianyu @http://litesuits.com
 * @date 2015-08-25
 */
public class ClipboardUtil {


    public static void copyToClipboard(@NonNull Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    public static int getItemCount(@NonNull Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = clipboard.getPrimaryClip();
        return data.getItemCount();
    }

    public static String getText(@NonNull Context context, int index) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > index) {
            return String.valueOf(clip.getItemAt(index).coerceToText(context));
        }
        return null;
    }

    public static String getLatestText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return String.valueOf(clip.getItemAt(0).coerceToText(context));
        }
        return null;
    }
}
