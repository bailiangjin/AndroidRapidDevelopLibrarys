package com.bailiangjin.uilibrary.rx;


/**
 * Created by bailiangjin on 2017/3/30.
 */

public abstract class CommonObserver<T> extends BaseRxObserver<T> {
    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }


}
