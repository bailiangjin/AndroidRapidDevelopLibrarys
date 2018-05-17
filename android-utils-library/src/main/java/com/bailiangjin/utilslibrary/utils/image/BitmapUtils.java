package com.bailiangjin.utilslibrary.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.text.TextUtils;

import com.bailiangjin.javabaselib.utils.FileUtils;
import com.bailiangjin.utilslibrary.utils.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Bitmap 工具类
 *
 * @author bailiangjin
 * @date 2018/4/23
 */
public class BitmapUtils {


    /**
     * 获取图片参数 不加载图片
     *
     * @param filePath
     * @return
     */
    public static BitmapFactory.Options getBitmapOptionsNotRealLoadImage(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        if (!new File(filePath).exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return options;

    }


    /**
     * 旋转bitmap
     *
     * @param bitmap              bitmap参数
     * @param sourceImageFilePath bitmap 源文件地址 用于获取ExifInterface信息
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, String sourceImageFilePath) {
        if (null == bitmap) {
            return null;
        }

        if (!FileUtils.isFileExist(sourceImageFilePath)) {
            return bitmap;
        }
        //照片旋转
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(sourceImageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotate = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            default:
                rotate = 0;
        }
        if (rotate != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (rotateBitmap != bitmap) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            bitmap = rotateBitmap;
        }
        return bitmap;
    }

    /**
     * 截取缩略图 回收源bitmap
     *
     * @param sourceBitmap         源bitmap
     * @param targetShortSidePixel 目标缩略图 短边像素值
     * @param targetLongSidePixel  目标缩略图 长边像素数
     * @return 截取的缩略图bitmap
     */
    public static Bitmap extractThumbnailRecycleInput(Bitmap sourceBitmap, int targetShortSidePixel, int targetLongSidePixel) {
        return extractThumbnail(sourceBitmap, targetShortSidePixel, targetLongSidePixel, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

    /**
     * 截取缩略图 不回收源bitmap
     *
     * @param sourceBitmap         源bitmap
     * @param targetShortSidePixel 目标缩略图 短边像素值
     * @param targetLongSidePixel  目标缩略图 长边像素数
     * @return 截取的缩略图bitmap
     */
    public static Bitmap extractThumbnailNotRecycleInput(Bitmap sourceBitmap, int targetShortSidePixel, int targetLongSidePixel) {
        return extractThumbnail(sourceBitmap, targetShortSidePixel, targetLongSidePixel, 0);
    }

    /**
     * 截取缩略图
     *
     * @param sourceBitmap          源bitmap
     * @param targetShortSidePixel  目标缩略图 短边像素值
     * @param targetLongSidePixel   目标缩略图 长边像素数
     * @param thumbnailUtilsOptions 回收源bitmap传ThumbnailUtils.OPTIONS_RECYCLE_INPUT 不回收源bitmap传 0
     * @return 截取的缩略图bitmap
     */
    private static Bitmap extractThumbnail(Bitmap sourceBitmap, int targetShortSidePixel, int targetLongSidePixel, int thumbnailUtilsOptions) {
        if (sourceBitmap == null) {
            return null;
        }
        if (targetShortSidePixel <= 0 || targetLongSidePixel <= 0) {
            LogUtils.e("input param targetShortSidePixel or targetLongSidePixel <= 0 please check you code and logic");
            return null;
        }

        int bitmapWidth = sourceBitmap.getWidth();
        int bitmapHeight = sourceBitmap.getHeight();

        if (bitmapWidth <= 0 || bitmapWidth <= 0) {
            LogUtils.e("input param bitmap width or height <= 0 please check you code and logic");
            return null;
        }

        int bitmapShortSidePixel = bitmapWidth < bitmapHeight ? bitmapWidth : bitmapHeight;
        int bitmapLongSidePixel = bitmapWidth > bitmapHeight ? bitmapWidth : bitmapHeight;


        if (bitmapShortSidePixel <= targetShortSidePixel && bitmapLongSidePixel <= targetLongSidePixel) {
            //小于截图要求尺寸 不处理 直接返回
            return sourceBitmap;
        }

        int endWidth;
        int endHeight;

        if (bitmapWidth == bitmapLongSidePixel) {
            //bitmap 宽>=高 为横图 或正方形
            endWidth = bitmapWidth < targetLongSidePixel ? bitmapWidth : targetLongSidePixel;
            endHeight = bitmapHeight < targetShortSidePixel ? bitmapHeight : targetShortSidePixel;

        } else {
            //bitmap 宽<高 为竖图
            endWidth = bitmapWidth < targetShortSidePixel ? bitmapWidth : targetShortSidePixel;
            endHeight = bitmapHeight < targetLongSidePixel ? bitmapHeight : targetLongSidePixel;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(sourceBitmap, endWidth, endHeight, thumbnailUtilsOptions);
        return thumbnailBitmap;
    }
}
