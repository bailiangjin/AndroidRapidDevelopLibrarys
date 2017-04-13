package com.bailiangjin.utilslibrary.api;

import android.app.Application;

/**
 * Created by bailiangjin on 2017/4/10.
 */

public class UtilsLibraryConfig {

    private Application application;


    public UtilsLibraryConfig(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}
