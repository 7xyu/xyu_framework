package com.wisdom.framework.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.utils.ToastUtils;

import static com.wisdom.framework.utils.UIUtils.startActivity;


/**
 * Created by chejiangwei on 2017/6/20.
 * Describe:
 */

public class SystemAppUtils {
    /**
     * 跳转打分
     */
    public static void openRate() {
        try {
            Uri uri = Uri.parse("market://details?id=" + UIUtils.getContext().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShortToast("您的手机没有安装Android应用市场");
            e.printStackTrace();
        }
    }
    public static void openAlbum(Activity activity, int requestCode) {
     /*   Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(inputfile), IMAGE_UNSPECIFIED);//主要问题就在这个File Uri上面  ————代码语句A
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputfile));//定义输出的File Uri，之后根据这个Uri去拿裁剪好的图片信息  ————代码B
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);*/
    }
}
