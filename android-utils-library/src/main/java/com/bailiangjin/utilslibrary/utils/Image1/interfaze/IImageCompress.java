package com.bailiangjin.utilslibrary.utils.Image1.interfaze;

import android.graphics.Bitmap;

import com.bailiangjin.utilslibrary.utils.Image1.model.ImageWidthHeightParam;

/**
 * 图片压缩接口
 *
 * @author bailiangjin
 * @date 2018/4/23
 */
public interface IImageCompress {


    /**
     * 压缩bitmap 为长边小于某个值得bitmap
     *
     * @param sourceBitmap
     * @param aimedLongSidePixel
     * @return
     */
    Bitmap compressBitmapToBitmap(Bitmap sourceBitmap, int aimedLongSidePixel);

    /**
     * 将bitmap 压缩并保存到指定路径文件中
     *
     * @param sourceBitmap
     * @param aimedFilePath
     * @param aimedLongSidePixel
     * @param aimedFileSize      文件大小限制 单位kb
     * @return filePath
     */
    String compressBitmapToFile(Bitmap sourceBitmap, String aimedFilePath, int aimedLongSidePixel, int aimedFileSize);

    /**
     * 压缩图片文件到文件
     *
     * @param sourceImageFilePath 源文件地址
     * @param aimedFilePath       目的文件保存地址
     * @param aimedLongSidePixel  图片长边目标最大值
     * @param aimedFileSize       文件大小限制 单位kb
     * @return
     */
    String compressImageToFile(String sourceImageFilePath, String aimedFilePath, int aimedLongSidePixel, int aimedFileSize);


    /**
     * 获取图片的宽高参数
     * @param imageFilePath
     * @return
     */
    ImageWidthHeightParam getImageWidthHeightParam(String imageFilePath);



    /**
     * 判断图片是否为超长图（长边/短边 >overLengthRatio ）
     * @param imageFilePath
     * @param overLengthRatio
     * @return
     */
    boolean isOverLengthImage(String imageFilePath, float overLengthRatio);


}
