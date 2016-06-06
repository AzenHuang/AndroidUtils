package com.github.azenhuang.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

/**
 * @author MaTianyu
 * @date 2014-11-19
 */
public class NotificationUtil {
    private static int LedID = 0;

    public static void lightLed(Context context, int colorOx, int durationMS) {
        lightLed(context, colorOx, 0, durationMS);
    }

    public static void lightLed(Context context, int colorOx, int startOffMS, int durationMS) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.ledARGB = colorOx;
        notification.ledOffMS = startOffMS;
        notification.ledOnMS = durationMS;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        LedID++;
        nm.notify(LedID, notification);
        nm.cancel(LedID);
    }

    public static void lightLed(final Context context, final int colorOx, final int startOffMS, final int durationMS,
                                int repeat) {
        if (repeat < 1) {
            repeat = 1;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        for (int i = 0; i < repeat; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lightLed(context, colorOx, startOffMS, durationMS);
                }
            }, (startOffMS + durationMS) * i);
        }
    }

    public static void lightLed(Context context, ArrayList<LightPattern> patterns) {
        if (patterns == null) {
            return;
        }
        for (LightPattern lp : patterns) {
            lightLed(context, lp.argb, lp.startOffMS, lp.durationMS);
        }
    }

    public static class LightPattern {
        public int argb = 0;
        public int startOffMS = 0;
        public int durationMS = 0;

        public LightPattern(int argb, int startOffMS, int durationMS) {
            this.argb = argb;
            this.startOffMS = startOffMS;
            this.durationMS = durationMS;
        }
    }

}
