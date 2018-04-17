/**
 *
 */
package com.wisdom.framework.base.view;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.utils.Utils;

/**
 * @author xyu
 */
public abstract class BaseApplication extends Application {

    // 获取到主线程的上下文
    private static BaseApplication mContext;
    // 获取到主线程的handlerbasea
    private static Handler mMainThreadHandler;
    // 获取到主线程轮询器
    private static Looper mMainThreadLooper;
    // 获取到主线程的Thread
    private static Thread mMainThread;
    // 获取到主线程的id
    private static int mMainThreadId;




    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThread = Thread.currentThread();
        this.mMainThreadId = android.os.Process.myTid();
        /*-----依赖application的框架初始化*/
        Utils.init(this);//一个utils库的初始化

    }

    // 对外暴露一个上下文
    public static BaseApplication getApplication() {
        return mContext;
    }

    // 对外暴露一个主线程的handelr
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    // 对外暴露一个主线程的Looper
    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    // 对外暴露一个主线程
    public static Thread getMainThread() {
        return mMainThread;
    }

    // 对外暴露一个主线程ID
    public static int getMainThreadId() {
        return mMainThreadId;
    }



}
