package com.bailiangjin.uilibrary.utils;

import android.widget.ImageView;

import com.bailiangjin.uilibrary.app.SuperApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bailiangjin.uilibrary.R;
import com.bailiangjin.uilibrary.app.SuperApplication;

import java.io.File;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * 基于Universal imageLoader 封装的 图片加载工具类
 * 枚举形式 实现单例
 * Created by bailiangjin on 16/7/16.
 */
public enum ImageLoadUtils {
    INSTANCE;

    private int onLoadingImageResId;
    private int onEmptyImageResId;
    private int onFailedImageResId;

    private RequestOptions glideNormalOptions;
    private RequestOptions glideCircleOptions;
    private RequestOptions glideRoundedOptions;
    // TODO: 2018/1/12 添加glide 实现的加载圆形图  替换 UniversalImageLoader的实现
    // TODO: 2018/1/12 详细配置 Glide配置项


    /**
     * 构造方法 参数初始化 单例形式 只会初始化一次 避免不必要的资源开支
     */
    ImageLoadUtils() {
        //初始化 全局默认图片
        onLoadingImageResId = R.drawable.icon_image_load_utils_default;
        onEmptyImageResId = R.drawable.icon_image_load_utils_default;
        onFailedImageResId = R.drawable.icon_image_load_utils_default;

        glideNormalOptions= new RequestOptions().placeholder(onFailedImageResId);
        //glideCircleOptions= new RequestOptions().placeholder(onFailedImageResId);
        glideRoundedOptions= new RequestOptions().placeholder(onFailedImageResId).transform(new RoundedCornersTransformation(SuperApplication.getContext(),10,0, RoundedCornersTransformation.CornerType.ALL));

    }


    public void loadImageView(ImageView iv, File imageFile) {
        if(null==iv){
            return;
        }
        Glide.with(iv.getContext())
                .load(imageFile)
                .apply(glideNormalOptions)
                .into(iv);
    }


    public void loadImageView(ImageView iv, String url) {
        if(null==iv){
            return;
        }
        Glide.with(iv.getContext())
                .load(url)
                .apply(glideNormalOptions)
                .into(iv);
    }


    public void loadRoundedImageView(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .apply(glideRoundedOptions)
                .into(iv);
    }


}

