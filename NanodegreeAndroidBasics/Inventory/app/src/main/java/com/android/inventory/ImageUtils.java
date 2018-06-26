package com.android.inventory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageUtils {

    /**
     * Converter bytes array in bitmap
     * @param bytes Bytes Array from Photo
     * @return Bitmap
     */
    public static Bitmap byteToBitmap(byte[] bytes) {
        if (bytes != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(bytes);
            return BitmapFactory.decodeStream(imageStream);
        }
        return null;
    }

    /**
     * Converter bitmap in array bytes
     * @param bitmap Photo Bitmap
     * @return Bytes Array from Photo
     */
    public static byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }
        return null;
    }

}
