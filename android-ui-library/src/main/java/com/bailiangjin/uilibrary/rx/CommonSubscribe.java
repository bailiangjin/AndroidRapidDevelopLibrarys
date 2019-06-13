package com.bailiangjin.uilibrary.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by bailiangjin on 2017/3/30.
 */

public abstract class CommonSubscribe<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }


}
