package com.wisdom.framework.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.wisdom.framework.help.ui.SpeedScroller;

import java.lang.reflect.Field;

/**
 * Created by chejiangwei on 2017/12/28.
 * Describe:用于优化ViewPager的工具类把
 */

public class ViewOptimization {
    private ViewOptimization() {
    }


    /**
     * 通过反射来修改 ViewPager的mScroller属性
     */
    public static void optimizeViewPager(Context context, ViewPager viewPager) {
        optimizeViewPager(context, viewPager, 2000);
    }

    /**
     * 通过反射来修改 ViewPager的mScroller属性
     */
    public static void optimizeViewPager(Context context, ViewPager viewPager, int duration) {

        try {
            Class clazz = Class.forName("android.support.v4.view.ViewPager");
            Field f = clazz.getDeclaredField("mScroller");
            SpeedScroller speedScroller = new SpeedScroller(context.getApplicationContext());
            speedScroller.setDuration(duration);
            f.setAccessible(true);
            f.set(viewPager, speedScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
