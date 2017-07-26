package com.bailiangjin.utilslibrary.api;

import android.app.Application;
import android.content.Context;

import com.bailiangjin.utilslibrary.utils.ui.ImageLoadUtils;

/**
 * Created by bailiangjin on 2017/4/10.
 */

public class UtilsLibrary {

    public static UtilsLibraryConfig utilsLibraryConfig;

    public static void init(Application application) {
        utilsLibraryConfig = new UtilsLibraryConfig(application);
        onInit(application);
    }

    public static Context getAppContext(){
        checkInit();
        return utilsLibraryConfig.getApplication().getApplicationContext();
    }

    private static void onInit(Application application) {
        ImageLoadUtils.INSTANCE.init(application);
    }

    private static void checkInit() {
        if(null== utilsLibraryConfig){
            throw new RuntimeException("please call UtilsLibrary.init() first");
        }
    }
}
