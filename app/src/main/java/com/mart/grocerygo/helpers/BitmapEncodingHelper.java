package com.mart.grocerygo.helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapEncodingHelper {


    public static String EncodeBitmap(Bitmap bitmap) {

        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();

        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bytes,Base64.DEFAULT);


    }



    public static Bitmap DecodeImage(String image) {
        byte[] bytes = Base64.decode(image,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    public static Drawable decodeBase64ToDrawable(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return new BitmapDrawable(Resources.getSystem(), bitmap);
    }

}
