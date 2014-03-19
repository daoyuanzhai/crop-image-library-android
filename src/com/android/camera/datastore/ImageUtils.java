package com.android.camera.datastore;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtils {
    private ImageUtils(){}

    public static byte[] bitmapToByteArray(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteData = baos.toByteArray();

            return byteData;
        } else
            return null;
    }

    public static Drawable byteToDrawable(Context context, byte[] data) {
        if (data == null)
            return null;
        else
            return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(data, 0, data.length));
    }
}
