package com.wisdom.framework.base;

import android.support.annotation.CallSuper;

import com.wisdom.framework.web.Callback;
import com.wisdom.framework.base.view.interfaces.LifeSubscription;
import com.wisdom.framework.web.HttpUtils;
import com.wisdom.framework.web.Stateful;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

import rx.Observable;


/**
 * Created by chejiangwei on 2017/10/10.
 * Describe: p层基类
 */
public class BasePresenter<T extends BaseView> {

    protected Reference<T> mReferenceView;//指的是界面，也就是BaseFragment或者BaseActivity

    protected T mView;
    private Callback callback;

    public void attachView(LifeSubscription mLifeSubscription) {
        if (mLifeSubscription == null || !(mLifeSubscription instanceof BaseView)) return;
        this.mReferenceView = new WeakReference<>((T) mLifeSubscription);
        mView = mReferenceView.get();
    }

    protected <T> void invoke(Observable<T> observable, Callback<T> callback, boolean loading) {
        this.callback = callback;
        HttpUtils.invoke((LifeSubscription) mView, observable, callback, loading);
    }

    protected <T> void invoke(Observable<T> observable, Callback<T> callback) {
        this.callback = callback;
        HttpUtils.invoke((LifeSubscription) mView, observable, callback);
    }

    /**
     * 给子类检查返回集合是否为空
     * 这样子做虽然耦合度高，但是接口都不是统一定的，暂时没有什么更好的办法
     *
     * @param list
     */
    public void checkState(List list) {
        if (list.size() == 0) {
            if (mView instanceof Stateful)
                ((Stateful) mView).setState(Callback.STATE_EMPTY);
            return;
        }
    }

    @CallSuper
    public void detachView() {
        if (mReferenceView != null)
            mReferenceView.clear();
        mReferenceView = null;
        if (mView != null)
            mView = null;
        if (callback != null) {
            callback.detachView();
        }
    }

}
