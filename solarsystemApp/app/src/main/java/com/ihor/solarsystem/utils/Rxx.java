package com.ihor.solarsystem.utils;


import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Rxx {

    private Rxx() {
    }

    public static <T> FlowableTransformer<T, T> ioAndMain() {
        return observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

}
