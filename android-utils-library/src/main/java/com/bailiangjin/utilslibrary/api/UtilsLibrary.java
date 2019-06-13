package com.bailiangjin.utilslibrary.api;

import android.content.Context;


/**
 * Created by bailiangjin on 2017/4/10.
 */

public class UtilsLibrary {

    public static Context appContext;

    public static void init(Context context) {
        appContext = context;
        onInit(context);
    }

    private static void onInit(Context context) {

    }

    public static Context getAppContext(){
        checkInit();
        return appContext;
    }

    private static void checkInit() {
        if(null== appContext){
            throw new RuntimeException("please call UtilsLibrary.init() first");
        }
    }
}
