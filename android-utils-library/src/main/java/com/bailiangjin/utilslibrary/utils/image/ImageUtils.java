package com.bailiangjin.utilslibrary.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.bailiangjin.utilslibrary.utils.image.interfaze.IImageCompress;
import com.bailiangjin.utilslibrary.utils.image.model.ImageWidthHeightParam;
import com.bailiangjin.utilslibrary.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 图片相关工具类
 */

public class ImageUtils implements IImageCompress {


    private static Bitmap.Config BITMAP_CONFIG = Bitmap.Config.RGB_565;

    /**
     * maxCompressTimes
     */
    private static final int MAX_COMPRESS_TIMES = 10;

    /**
     * 压缩图片时 每次图片质量衰减比例
     */
    private static final float COUNTDOWN_EVERY_TIME = 0.95F;

    /**
     * 超长图 比例阈值
     */
    private static final float OVER_LENGTH_IMAGE_RATIO = 27f / 9f;

    /**
     * 超大图强制压缩控制阈值 超过8M为大图片 强制压缩
     */
    private static final long BIG_IMAGE_FILE_LENGTH = 8 * 1024 * 1024;


    @Override
    public Bitmap compressBitmapToBitmap(Bitmap sourceBitmap, int aimedLongSidePixel) {
        if (null == sourceBitmap) {
            return null;
        }

        return getBitmapFromBitmapByLongSide(sourceBitmap, aimedLongSidePixel);

    }

    @Override
    public String compressBitmapToFile(Bitmap sourceBitmap, String aimedFilePath, int aimedLongSidePixel, int aimedFileSize) {
        if (null != sourceBitmap) {
            Bitmap endBitmap = compressBitmapToBitmap(sourceBitmap, aimedLongSidePixel);
            if (null != endBitmap) {
                File file = compressAndSaveBitmapToFile(sourceBitmap, aimedFilePath, aimedFileSize);
                return null == file ? null : file.exists() ? file.getAbsolutePath() : null;
            }
        }
        return null;
    }

    @Override
    public String compressImageToFile(String sourceImageFilePath, String aimedFilePath, int aimedLongSidePixel, int aimedFileSize) {
        Bitmap bitmap = getBitmapFromFileByLongSide(sourceImageFilePath, aimedLongSidePixel);
        if (null != bitmap) {
            File file = compressAndSaveBitmapToFile(bitmap, aimedFilePath, aimedFileSize);
            return null == file ? null : file.exists() ? file.getAbsolutePath() : null;
        }
        return null;
    }

    /**
     * 获取图片的宽高参数
     *
     * @param imageFilePath
     * @return
     */
    @Override
    public ImageWidthHeightParam getImageWidthHeightParam(String imageFilePath) {

        if (TextUtils.isEmpty(imageFilePath)) {
            return null;
        }
        File file = new File(imageFilePath);
        if (!file.exists() || file.isDirectory() || 0 == file.length()) {
            return null;
        }

        BitmapFactory.Options options = BitmapUtils.getBitmapOptionsNotRealLoadImage(imageFilePath);
        if (null == options) {
            LogUtils.e("get picture options,found null please check the logic before");
            return null;
        }

        return new ImageWidthHeightParam(options.outWidth, options.outHeight);
    }


    /**
     * 检测图片是否为超长图
     *
     * @param imageFilePath 图片文件路径
     * @return
     */
    @Override
    public boolean isOverLengthImage(String imageFilePath, float overLengthRatio) {

        if (overLengthRatio <= 0) {
            return true;
        }

        BitmapFactory.Options options = BitmapUtils.getBitmapOptionsNotRealLoadImage(imageFilePath);
        if (null == options) {
            LogUtils.e("get picture options,found null please check the logic before");
            return false;
        }


        int orgHeight = options.outHeight;
        int orgWidth = options.outWidth;
        if (0 == orgWidth || 0 == orgHeight) {
            LogUtils.e("error:the picture is invalid,because its  width or height is 0");
            return false;
        }

        float heightWidthRatio = (float) orgHeight / (float) orgWidth;

        return heightWidthRatio > overLengthRatio || heightWidthRatio < (1 / overLengthRatio);
    }


    /**
     * 从已有bitmap压缩出 长边长度不大于targetShortSidePixel的bitmap
     *
     * @param sourceBitmap
     * @param targetLongSidePixel
     * @return
     */
    private Bitmap getBitmapFromBitmapByLongSide(Bitmap sourceBitmap, final int targetLongSidePixel) {
        if (null == sourceBitmap) {
            return null;
        }

        int tempWidth = sourceBitmap.getWidth();
        int tempHeight = sourceBitmap.getHeight();


        Bitmap tempBitmap = sourceBitmap;

        float heightWidthRatio = (float) tempHeight / (float) tempWidth;

        int endWidth;
        int endHeight;
        //普通图 再次压缩处理
        if (heightWidthRatio >= 1 && tempHeight <= targetLongSidePixel
                || heightWidthRatio < 1 && tempWidth <= targetLongSidePixel) {
            //图片窄边已经小于等于控制值  不必再压缩 直接返回现有的bitmap
            return tempBitmap;
        } else {
            //图片窄边大于窄边像素期望值

            if (heightWidthRatio >= 1) {
                //竖图
                float secondCompressRatio = (float) targetLongSidePixel / (float) tempHeight;
                endWidth = (int) (tempWidth * secondCompressRatio);
                endHeight = targetLongSidePixel;
            } else {
                //横图
                float secondCompressRatio = (float) targetLongSidePixel / (float) tempWidth;
                endWidth = targetLongSidePixel;
                endHeight = (int) (tempHeight * secondCompressRatio);
            }
        }
        //最后一个参数解释 当进行的不只是平移变换时，filter参数为true可以进行滤波处理，有助于改善新图像质量;flase时，计算机不做过滤处理。
        Bitmap endBitmap = Bitmap.createScaledBitmap(tempBitmap, endWidth, endHeight, true);
        if (null == endBitmap) {
            return tempBitmap;
        }
        //释放中间的bitmap
        if (tempBitmap != endBitmap) {
            if (!tempBitmap.isRecycled()) {
                tempBitmap.recycle();
            }
            tempBitmap = null;
        }
        return endBitmap;
    }

    /**
     * 从文件读取长边长度不大于 targetLongSidePixel 的bitmap
     *
     * @param imageFilePath       文件路径
     * @param targetLongSidePixel 长边像素限制值
     * @return 读取的bitmap
     */
    private Bitmap getBitmapFromFileByLongSide(String imageFilePath, final int targetLongSidePixel) {

        if (TextUtils.isEmpty(imageFilePath)) {
            return null;
        }
        File file = new File(imageFilePath);
        if (!file.exists() || file.isDirectory() || 0 == file.length()) {
            return null;
        }

        BitmapFactory.Options options = BitmapUtils.getBitmapOptionsNotRealLoadImage(imageFilePath);
        if (null == options) {
            LogUtils.e("get picture options,found null please check the logic before");
            return null;
        }

        int orgHeight = options.outHeight;
        int orgWidth = options.outWidth;
        // 设置只读取参数配置为false 为下一步真正读取bitmap做准备
        options.inJustDecodeBounds = false;

        int pixelCompressRatio = getPixelCompressRatio(orgWidth, orgHeight, targetLongSidePixel);
        if (1 == pixelCompressRatio) {
            long fileLength = file.length();
            if (fileLength > BIG_IMAGE_FILE_LENGTH) {
                //窄边大文件强制压缩
                pixelCompressRatio = (int) Math.ceil((double) fileLength / (double) BIG_IMAGE_FILE_LENGTH);
            }
        }

        options.inSampleSize = pixelCompressRatio;
        options.inPreferredConfig = BITMAP_CONFIG;

        //真正读取bitmap到内存当中
        Bitmap tempBitmap = BitmapFactory.decodeFile(imageFilePath, options);
        if (null == tempBitmap) {
            return null;
        }
        int tempWidth = options.outWidth;
        int tempHeight = options.outHeight;


        float heightWidthRatio = (float) tempHeight / (float) tempWidth;

        tempBitmap = BitmapUtils.rotateBitmap(tempBitmap, imageFilePath);
        int endWidth;
        int endHeight;
        //普通图 再次压缩处理
        if (heightWidthRatio >= 1 && tempHeight <= targetLongSidePixel
                || heightWidthRatio < 1 && tempWidth <= targetLongSidePixel) {
            //图片窄边已经小于等于控制值  不必再压缩 直接返回现有的bitmap
            return tempBitmap;
        } else {
            //图片窄边大于窄边像素期望值

            if (heightWidthRatio >= 1) {
                //竖图
                float secondCompressRatio = (float) targetLongSidePixel / (float) tempHeight;
                endWidth = (int) (tempWidth * secondCompressRatio);
                endHeight = targetLongSidePixel;
            } else {
                //横图
                float secondCompressRatio = (float) targetLongSidePixel / (float) tempWidth;
                endWidth = targetLongSidePixel;
                endHeight = (int) (tempHeight * secondCompressRatio);
            }
        }
        //最后一个参数解释 当进行的不只是平移变换时，filter参数为true可以进行滤波处理，有助于改善新图像质量;flase时，计算机不做过滤处理。
        Bitmap endBitmap = Bitmap.createScaledBitmap(tempBitmap, endWidth, endHeight, true);
        if (null == endBitmap) {
            return tempBitmap;
        }
        //释放中间的bitmap
        if (tempBitmap != endBitmap) {
            if (!tempBitmap.isRecycled()) {
                tempBitmap.recycle();
            }
            tempBitmap = null;
        }
        return endBitmap;
    }


    /**
     * 获取超长图的压缩比率
     *
     * @param imageFilePath
     * @return
     */
    private static float getOverLengthImageMultiplyingPower(String imageFilePath, float overLengthRatio) {
        if (overLengthRatio <= 0) {
            return 1;
        }

        BitmapFactory.Options options = BitmapUtils.getBitmapOptionsNotRealLoadImage(imageFilePath);
        if (null == options) {
            LogUtils.e("get picture options,found null,please check the logic before");
            return 1F;
        }

        int orgHeight = options.outHeight;
        int orgWidth = options.outWidth;
        if (0 == orgWidth || 0 == orgHeight) {
            LogUtils.e("error:the picture is invalid,because its  width or height is 0");
            return 1F;
        }
        float heightWidthRatio = (float) orgHeight / (float) orgWidth;

        heightWidthRatio = heightWidthRatio > 1 ? heightWidthRatio : 1 / heightWidthRatio;

        float multiplyingPower = heightWidthRatio / overLengthRatio;
        return multiplyingPower;

    }


    /**
     * 计算像素压缩比率 （参数单位为像素）
     *
     * @param orgWidth            原宽度
     * @param orgHeight           原高度
     * @param targetLongSidePixel 目标短边像素值
     * @return 像素压缩比 值越大 压缩越狠
     */
    private static int getPixelCompressRatio(int orgWidth, int orgHeight, int targetLongSidePixel) {
        //关键指标 像素压缩比率
        int pixelCompressRatio;

        //实际图片短边像素值
        int imageLongSidePixel = orgWidth > orgHeight ? orgWidth : orgHeight;
        //需要压缩的比率
        pixelCompressRatio = Math.round((float) imageLongSidePixel / (float) targetLongSidePixel);
        return pixelCompressRatio < 1 ? 1 : pixelCompressRatio;
    }

    /**
     * 压缩bitmap 并转为Bytes
     *
     * @param bitmap
     * @param maxKbSize
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap, int maxKbSize) {
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        maxKbSize = maxKbSize * 1024; // convert to Bytes
        if (maxKbSize == 0) {
            bitmap.compress(compressFormat, quality, baos);
        } else {
            baos.reset();
            bitmap.compress(compressFormat, quality, baos);
            int compressTimes = 0;
            while (baos.size() > maxKbSize && compressTimes <= MAX_COMPRESS_TIMES) {
                float countDownEveryTime = COUNTDOWN_EVERY_TIME;
                compressTimes++;
                quality = (int) ((float) quality * countDownEveryTime);
                baos.reset();
                bitmap.compress(compressFormat, quality, baos);
            }
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    /**
     * 将bitmap 压缩到目标体积以内 并保存到目标路径
     *
     * @param bitmap         要压缩的bitmap
     * @param fileSavePath   目标路径
     * @param aimedMaxKbSize 目标体积
     * @return
     */
    public static File compressAndSaveBitmapToFile(Bitmap bitmap, String fileSavePath, int aimedMaxKbSize) {
        byte[] bytes = bitmapToBytes(bitmap, aimedMaxKbSize);
        if (null != bytes && bytes.length > 0) {
            try {
                File file = new File(fileSavePath);
                if (!file.exists()) {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                }

                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
                return file;
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.e("save bytes to file io exception :" + e.getMessage());
                return null;
            }
        } else {
            LogUtils.e(" bytes to save to file is empty");
            return null;
        }
    }


}
