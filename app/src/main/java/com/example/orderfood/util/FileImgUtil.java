package com.example.orderfood.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 实现图片保存与加载
 */
public class FileImgUtil {

//    /**
//     * 将bitmap类型保存成文件并返回对应路径
//     * @param uri
//     * @return
//     */
//    public static String saveImageUriToFile(Uri uri, Context context) {
//        Glide.with(context).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//
//            }
//        });
//
//        return "";
//    }
    /**
     * 将bitmap类型保存成文件
     * @param uri
     * @return
     */
    public static void saveImageUriToFile(Uri uri, Context context, String path) {
        Log.d("mine", "image path: " + path);
        Glide.with(context).asBitmap().load(uri).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                File file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    resource.compress(Bitmap.CompressFormat.PNG, 100, fos);
                } catch (IOException e) {
                    Log.e("mine", "Failed to save image", e);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {}
        });
    }

    /**
     * 获取相册路径
     * @return
     */
    public static String getPicAbsPath() {
        String picName = UUID.randomUUID().toString().replace("-", "") + ".png";
        String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        return dirPath + "/" + picName;  // 图片绝对路径
    }
}
