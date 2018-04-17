package com.wisdom.framework.utils;

import android.util.Log;

/**
 * Log统一管理类
 *
 * @author other
 */
public class LogUtils {

    private static final String TAG = "wisdom";
    public static final int DEBUG_V = 1;
    public static final int DEBUG_I = 2;
    public static final int DEBUG_D = 3;
    public static final int DEBUG_W = 4;
    public static final int DEBUG_E = 5;
    public static final int DEBUG_NO = 6;

    private static int sDebugLevel;

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    //下面是传入类名打印log
    public static void i(Class<?> mClass, String msg) {
        i(mClass.getName(), msg);
    }

    public static void e(Class<?> mClass, String msg) {
        e(mClass.getName(), msg);
    }

    public static void d(Class<?> mClass, String msg) {
        d(mClass.getName(), msg);
    }

    public static void w(Class<?> mClass, String msg) {
        w(mClass.getName(), msg);
    }

    public static void v(Class<?> mClass, String msg) {
        v(mClass.getName(), msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG_V >= sDebugLevel) {
            Log.v(tag, msg);
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (DEBUG_I >= sDebugLevel) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG_D >= sDebugLevel) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG_W >= sDebugLevel) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG_E >= sDebugLevel) {
            Log.e(tag, msg);
        }
    }


    public static void setDebugLevel(int debugLevel) {
        sDebugLevel = debugLevel;
    }

}
