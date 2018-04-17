package com.wisdom.framework.help;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wisdom.framework.help.exception.FragmentNotFoundException;
import com.wisdom.framework.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chejiangwei on 2017/12/15.
 * Describe:对fragmentmanager的包装
 */

public class MyFragmentManager {
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private List<Fragment> fragments;

    private MyFragmentManager() {
    }

    public static MyFragmentManager create(FragmentManager fragmentManager, int containerId) {
        final MyFragmentManager myFragmentManager = new MyFragmentManager();
        myFragmentManager.mFragmentManager = fragmentManager;
        myFragmentManager.mContainerId = containerId;
        myFragmentManager.fragments = new ArrayList<>();
        //监听fragment的绑定和解绑
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                super.onFragmentAttached(fm, f, context);
                myFragmentManager.fragments.add(f);
            }

            @Override
            public void onFragmentDetached(FragmentManager fm, Fragment f) {
                super.onFragmentDetached(fm, f);
                myFragmentManager.fragments.remove(f);
            }


        }, false);
        return myFragmentManager;
    }

    /**
     * 该fragment是否显示
     *
     * @param tag
     * @return
     */
    public boolean isFragmentVisible(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }

    /**
     * 是否存在该Fragment
     *
     * @param tag
     * @return
     */
    public boolean isFragmentExist(String tag) {
        for (Fragment fragment : fragments) {
            if (fragment.getTag().equals(tag)) return true;
        }
        return false;
    }

    /**
     * 根据tag拿到fragment并cast
     *
     * @param tag
     * @param <T>
     * @return
     */
    public <T extends Fragment> T getFragmentByTag(String tag) {
        return (T) mFragmentManager.findFragmentByTag(tag);
    }

    /**
     * 根据clz拿到tag为clz.getName的fragment并cast
     *
     * @param clz 当tag为clz.getName即可
     * @param <T>
     * @return
     */
    public <T extends Fragment> T getFragmentByClzTag(Class<T> clz) {
        return (T) mFragmentManager.findFragmentByTag(clz.getName());
    }

    /**
     * 获取当前显示的fragment并cast
     *
     * @param <T>
     * @return
     */
    public <T extends Fragment> T getForegroundFragment() {
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) return (T) fragment;
        }
        return null;
    }

    /**
     * 替换fragment，不加入后退栈
     */
    public void replaceFragment(Fragment newFragment) {
        replaceFragment(newFragment.getClass().getName(), newFragment);
    }

    /**
     * 替换fragment，不加入后退栈
     */
    public void replaceFragment(String tag, Fragment newFragment) {
        if (TextUtils.isEmpty(tag) || newFragment == null) return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (!ArrayUtils.isEmpty(fragments))
            for (Fragment fragment : fragments)
                if (fragment.isVisible()) ft.hide(fragment);

        ft.replace(mContainerId, newFragment, tag).commitAllowingStateLoss();
    }

    /**
     * 添加一层fragment，并加入后退栈
     */
    public void addFragment(Fragment newFragment) {
        addFragment(newFragment.getClass().getName(), newFragment);
    }

    /**
     * 添加一层fragment，并加入后退栈
     */
    public void addFragment(String tag, Fragment newFragment) {
        if (TextUtils.isEmpty(tag) || newFragment == null) return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (!ArrayUtils.isEmpty(fragments))
            for (Fragment fragment : fragments)
                if (fragment.isVisible()) ft.hide(fragment);

        ft.add(mContainerId, newFragment, tag).addToBackStack(null).commitAllowingStateLoss();
    }

    /**
     * 后退栈进行后退
     */
    public void popBackStack() {
        mFragmentManager.popBackStackImmediate();
    }

    /**
     * 后退到第一个栈
     */
    public void popBackStackFull() {
        //flags为0时，弹出该id代表的栈以上的栈，如果是1，弹出该栈（包括该栈）以上的栈。
        mFragmentManager.popBackStackImmediate(0, 0);
    }


    /*-------------------------------   无后退栈切换 适用于多个fragment独立存在而不销毁------------------------------------------*/

    /**
     * @param fClass shi
     *               如果有相同类的fragment，则建议使用加tag的重载
     */
    public void changeFragment(Class<? extends Fragment> fClass) {
        changeFragment(fClass.getName(), fClass);
    }

    /**
     * @param fClass shi 只适用不需要给fragment设置bundle参数的情况
     */
    public void changeFragment(String tag, Class<? extends Fragment> fClass) {
        Fragment showFragment = mFragmentManager.findFragmentByTag(tag);
        if (showFragment != null && showFragment.isVisible()) return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (!ArrayUtils.isEmpty(fragments))
            for (Fragment fragment : fragments)
                if (fragment.isVisible()) ft.hide(fragment);

        if (showFragment != null) ft.show(showFragment);
        else {
            try {
                showFragment = fClass.newInstance();
                ft.add(mContainerId, showFragment, tag);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * @param newFragment 需要对fragment设置参数的话，用该方法把。
     */
    public void changeFragment(Fragment newFragment) {
        changeFragment(newFragment.getClass().getName(), newFragment);
    }

    /**
     * @param newFragment 需要对fragment设置参数的话，用该方法把。
     */
    public void changeFragment(String tag, Fragment newFragment) {
        Fragment showFragment = mFragmentManager.findFragmentByTag(tag);
        if (showFragment != null && showFragment.isVisible()) return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (!ArrayUtils.isEmpty(fragments))
            for (Fragment fragment : fragments)
                if (fragment.isVisible()) ft.hide(fragment);

        if (showFragment != null) ft.show(showFragment);
        else ft.add(mContainerId, newFragment, tag);
        ft.commitAllowingStateLoss();
    }

    /**
     * @param tag 务必保证已经存在已添加该tag的fragment如果fragment不存在则抛出
     */
    public void changeFragment(String tag) throws FragmentNotFoundException {
        Fragment showFragment = mFragmentManager.findFragmentByTag(tag);
        if (showFragment != null && showFragment.isVisible()) return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (!ArrayUtils.isEmpty(fragments))
            for (Fragment fragment : fragments)
                if (fragment.isVisible()) ft.hide(fragment);

        if (showFragment != null) ft.show(showFragment).commitAllowingStateLoss();
        throw new FragmentNotFoundException(tag);
    }
        /*-------------------------------   无后退栈切换  end------------------------------------------*/
}
