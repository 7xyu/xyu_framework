package com.wisdom.framework.web;

import com.wisdom.framework.base.view.BaseActivity;
import com.wisdom.framework.base.view.interfaces.LifeSubscription;
import com.wisdom.framework.interfaces.ILoading;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


public class HttpUtils {

    public static <T> void invoke(LifeSubscription lifecycle, Observable<T> observable, final Callback<T> callback, final boolean showLoading) {
        Stateful target = null;
        if (lifecycle instanceof Stateful) {
            target = (Stateful) lifecycle;
            callback.setTarget(target);
        }
        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
       /* if (!NetworkUtils.isConnected()) {
            ToastUtils.showShortToast("网络连接已断开");
            if (target != null) {
                target.setState(Callback.STATE_ERROR);
            }
            return;
        }*/

        observable = observable.subscribeOn(Schedulers.io());
        if (showLoading) {
            callback.setLoading(true);
            observable = observable.doOnSubscribe(
                    new Action0() {
                        @Override
                        public void call() {
                            //使在请求网络期间，进行加载动画
                            BaseActivity foregroundActivity = BaseActivity.getForegroundActivity();
                            if (foregroundActivity != null && foregroundActivity instanceof ILoading)
                                ((ILoading) foregroundActivity).netLoading(callback);
                        }
                    }
            ).subscribeOn(AndroidSchedulers.mainThread());
        }
        Subscription subscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
        if (lifecycle != null)
            lifecycle.addSubscription(subscription);

    }

    public static <T> void invoke(LifeSubscription lifecycle, Observable<T> observable, Callback<T> callback) {
        invoke(lifecycle, observable, callback, false);
    }
}
