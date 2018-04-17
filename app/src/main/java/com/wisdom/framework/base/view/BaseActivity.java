package com.wisdom.framework.base.view;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.wisdom.framework.base.BaseView;
import com.wisdom.framework.base.view.interfaces.LifeSubscription;
import com.wisdom.framework.interfaces.ILoading;
import com.wisdom.framework.utils.AppUtils;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Activity基类
 *
 * @author xyu
 */
public abstract class BaseActivity extends AppCompatActivity implements LifeSubscription, ILoading, BaseView {
    /**
     * 是否强制限制应用字体大小
     */
    private static final boolean LIMIT_TEXT_SIZE = false;

    protected CompositeSubscription mCompositeSubscription;//RxJava订阅者集合

    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();

    /**
     * 正在显示交互的Activity
     */
    @SuppressLint("StaticFieldLeak")
    private static BaseActivity sForegroundActivity;//dont't worry,不会leak的
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetScaleDensity();
        decorView = getWindow().getDecorView();
        //当前activity加入集合
        synchronized (mActivities) {
            mActivities.add(this);
        }
    }

    private boolean isFirstLayout = true;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //监听layout后的事件
        AppUtils.getViewRoot(this).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (isFirstLayout) {
                    onFirstLayout();
                    isFirstLayout = false;
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resetScaleDensity();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //暂时不保存状态
        // super.onSaveInstanceState(outState);
    }


    @Override
    protected void onPause() {
        super.onPause();
        sForegroundActivity = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sForegroundActivity = this;
    }

    @Override
    public void onBackPressed() {
        KeyboardUtils.hideSoftInput(this);//关闭输入软盘
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        //清空订阅者
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        synchronized (mActivities) {
            mActivities.remove(this);
        }
//        this.setContentView(R.layout.share_empty);
        super.onDestroy();
    }

    @Override
    public Resources getResources() {
        if (!LIMIT_TEXT_SIZE) return super.getResources();
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        //防止更改系统字体大小
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 第一次布局完成
     */
    protected void onFirstLayout() {

    }

    /**
     * 子类可以直接用
     */
    protected void setToolBar(Toolbar toolbar) {
        if (getSupportActionBar() != null || toolbar == null) return;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 添加订阅者
     *
     * @param subscription
     */
    @Override
    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);
    }



    /**
     * 中途HOME出来，更改了字体大小，我们APP里显示的字号还是会改变的，
     * 所以最后我在我们项目的基类BaseActivity中的onCreate()和onRestart()方法中都添加了
     */
    private void resetScaleDensity() {
        if (!LIMIT_TEXT_SIZE) return;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
    }


    /**
     * 简化了下inflate，使用当前Activity的theme
     *
     * @param layoutId 布局id
     * @return
     */
    public View inflate(int layoutId) {
        return LayoutInflater.from(this).inflate(layoutId, null);
    }

    /**
     * 返回顶层视图
     *
     * @return
     */
    public View getDecorView() {
        return decorView;
    }

    /**
     * 获取当前activity
     */
    public static BaseActivity getForegroundActivity() {
        return sForegroundActivity;
    }

    /**
     * 清除所有activity，结束应用
     */
    public static void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
