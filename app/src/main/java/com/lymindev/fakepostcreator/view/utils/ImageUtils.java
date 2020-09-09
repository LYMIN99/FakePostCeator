package com.lymindev.fakepostcreator.view.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.lymindev.fakepostcreator.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageUtils {

    public static Bitmap loadImageFromStorage(String path, String filename) {

        try {
            File f = new File(path, filename);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getByteFromBitmap(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmapFromByteArray(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap getBitmapFromUri(Context context,Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap =  MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromImageView(ImageView imageView ){
        imageView.setDrawingCacheEnabled(true);
        return imageView.getDrawingCache();
    }
    public void takeScreenShot(View v){
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + "screen.jpg";
// create bitmap screen capture
        Bitmap bitmap;
       // View v1 = findViewById(R.id.relScreenShot);
       // v1.setDrawingCacheEnabled(true);
// bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        bitmap = loadBitmapFromView(v, v.getWidth(), v.getHeight());
        //v1.setDrawingCacheEnabled(false);

        OutputStream fout = null;
        File imageFile = new File(mPath);

        try {
            fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Uri getImageUriFromBitmap(Context context, Bitmap inImage) {
        Uri uri = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new Random().nextInt(999));
        } else {
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
            uri =  Uri.parse(path);
        }
        return uri;
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, width, height);
//Get the view’s background
        Drawable bgDrawable =v.getBackground();
        if (bgDrawable!=null)
//has background drawable, then draw it on the canvas
            bgDrawable.draw(c);
        else
//does not have background drawable, then draw white background on the canvas
            c.drawColor(Color.WHITE);
        v.draw(c);
        return b;
    }
}
