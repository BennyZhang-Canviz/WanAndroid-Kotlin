package com.lengjiye.tools.log;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * livedata postvalue()方法不能重复调用，否则只会显示最后一条post的值
 */
public class LiveDataUtils {
    private static Handler sMainHandler;

    /**
     * 用 setValue 更新 MutableLiveData 的数据，如果在子线程，就切换到主线程
     */
    public static <T> void setValue(MutableLiveData<T> mld, T d) {
        if (mld == null) {
            return;
        }
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            mld.setValue(d);
        } else {
            postSetValue(mld, d);
        }
    }

    /**
     * 向主线程的 handler 抛 SetValueRunnable
     */
    public static <T> void postSetValue(MutableLiveData<T> mld, T d) {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        sMainHandler.post(SetValueRunnable.create(mld, d));
    }

    private static class SetValueRunnable<T> implements Runnable {
        private final MutableLiveData<T> liveData;
        private final T data;

        private SetValueRunnable(@NonNull MutableLiveData<T> liveData, T data) {
            this.liveData = liveData;
            this.data = data;
        }

        @Override
        public void run() {
            liveData.setValue(data);
        }

        public static <T> SetValueRunnable<T> create(@NonNull MutableLiveData<T> liveData, T data) {
            return new SetValueRunnable<>(liveData, data);
        }
    }
}