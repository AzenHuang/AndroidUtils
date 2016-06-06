package com.github.azenhuang.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    private static final String TAG = BitmapUtils.class.getSimpleName();

    /**
     * convert Bitmap to byte array
     */
    public static byte[] bitmapToByte(Bitmap b) {
        return bitmapToByte(b, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * convert Bitmap to byte array
     */
    public static byte[] bitmapToByte(Bitmap b, Bitmap.CompressFormat format, int quality) {
        if (b == null) {
            return null;
        }
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(format, quality, o);
        return o.toByteArray();
    }
    /**
     * convert byte array to Bitmap
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * 把bitmap转换成Base64编码String
     */
    public static String bitmapEncodeToString(Bitmap bitmap) {
        return Base64.encodeToString(bitmapToByte(bitmap), Base64.DEFAULT);
    }

    /**
     * convert Drawable to Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * @deprecated Use {@link #bitmapToDrawable(Resources, Bitmap)} to ensure
     * that the drawable has correctly set its target density.
     */
    @Deprecated
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(bitmap);
    }

    /**
     * convert Bitmap to Drawable
     * that the drawable has correctly set its target density.
     */
    public static Drawable bitmapToDrawable(Resources resources, Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(resources, bitmap);
    }


    /**
     * scale image
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight, boolean needRecycle) {
        return scaleImageBy(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight(),needRecycle);
    }

    /**
     * scale image
     */
    public static Bitmap scaleImageBy(Bitmap org, float scaleWidth, float scaleHeight, boolean needRecycle) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
        if (needRecycle) {
            org.recycle();
        }
        return newBitmap;
    }

    public static Bitmap toCircleBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //paint.setColor(0xff424242);
        paint.setColor(Color.TRANSPARENT);
        int radius = Math.min(width/2, height/2);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static boolean saveBitmap(Bitmap bitmap, File file) throws IOException {
        if (bitmap == null  || file == null || !file.isFile()){
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            return true;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(fos);
        }
    }

    public static boolean saveBitmap(Bitmap bitmap, String absPath) throws IOException {
        return !TextUtils.isEmpty(absPath) && saveBitmap(bitmap, new File(absPath));
    }


    public static Bitmap decodeSampledBitmapFromFile(String filePath,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            //设置inSampleSize为2的幂是因为解码器最终还是会对非2的幂的数进行向下处理，获取到最靠近2的幂的数
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

//        int h = options.outHeight;
//        int w = options.outWidth;
//        int inSampleSize = 0;
//        if (h > reqHeight || w > reqWidth) {
//            float ratioW = (float) w / reqWidth;
//            float ratioH = (float) h / reqHeight;
//            inSampleSize = (int) Math.min(ratioH, ratioW);
//        }
//        inSampleSize = Math.max(1, inSampleSize);
        return inSampleSize;
    }


}
