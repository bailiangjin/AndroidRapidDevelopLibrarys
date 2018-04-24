package com.bailiangjin.utilslibrary.utils.Image;

import android.text.TextUtils;

import com.bailiangjin.javabaselib.utils.FileUtils;
import com.bailiangjin.utilslibrary.utils.Image.model.ImageType;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图片类型工具类
 *
 * @author bailiangjin
 * @date 2018/4/23
 */
public class ImageTypeUtils {

    public static final String TYPE_JPG = "jpg";
    public static final String TYPE_JPEG = "jpeg";
    public static final String TYPE_GIF = "gif";
    public static final String TYPE_PNG = "png";
    public static final String TYPE_BMP = "bmp";
    public static final String TYPE_WEBP = "webp";
    public static final String TYPE_UNKNOWN = "unknown";


    /**
     * 根据文件后缀名 获取图片格式
     *
     * @param filePath
     * @return
     */
    public static ImageType getImageTypeByFileExtension(String filePath) {
        String postFix = FileUtils.getFileExtension(filePath);
        if (TextUtils.isEmpty(postFix)) {
            return ImageType.UNKNOWN;
        }

        switch (postFix) {
            case TYPE_JPG:
            case TYPE_JPEG:
                return ImageType.JPEG;

            case TYPE_PNG:
                return ImageType.PNG;

            case TYPE_WEBP:
                return ImageType.WEBP;

            case TYPE_GIF:
                return ImageType.GIF;

            case TYPE_BMP:
                return ImageType.BMP;


            default:
                return ImageType.UNKNOWN;

        }
    }

    public static final String FILE_HEADER_JPEG = "FFD8FF";
    public static final String FILE_HEADER_PNG = "89504E47";
    public static final String FILE_HEADER_GIF = "47494638";
    public static final String FILE_HEADER_BMP = "424D";

    /**
     * 根据文件流判断图片类型
     *
     * @param filePath
     * @return jpg/png/gif/bmp
     */
    public static ImageType getFileTypeByHeader(String filePath) {
        if (!FileUtils.isFileExist(filePath)) {
            return ImageType.UNKNOWN;
        }

        //读取文件的前几个字节来判断图片格式
        byte[] fileHeaderBytes = new byte[4];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            fis.read(fileHeaderBytes, 0, fileHeaderBytes.length);
            String type = bytesToHexString(fileHeaderBytes).toUpperCase();
            if (type.contains(FILE_HEADER_JPEG)) {
                return ImageType.JPEG;
            } else if (type.contains(FILE_HEADER_PNG)) {
                return ImageType.PNG;
            } else if (type.contains(FILE_HEADER_GIF)) {
                return ImageType.GIF;
            } else if (type.contains(FILE_HEADER_BMP)) {
                return ImageType.BMP;
            } else {
                return ImageType.UNKNOWN;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将byte字节转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String bytesToHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length <= 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        String hexString;
        for (int i = 0; i < byteArray.length; i++) {
            hexString = Integer.toHexString(byteArray[i] & 0xFF).toUpperCase();
            if (hexString.length() < 2) {
                builder.append(0);
            }
            builder.append(hexString);
        }
        return builder.toString();
    }


}
