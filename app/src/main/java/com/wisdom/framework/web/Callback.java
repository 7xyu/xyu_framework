package com.wisdom.framework.web;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.ToastUtils;

import rx.Subscriber;

public abstract class Callback<T> extends Subscriber<T> {


    private boolean isLoading;

    public static final int STATE_EMPTY = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_ERROR = -1;
    private Stateful target;

    public void setTarget(Stateful target) {
        this.target = target;
    }

    public void detachView() {
        target = null;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        LogUtils.d("RetrofitLog", "是否主动取消请求" + isUnsubscribed());
        if (isUnsubscribed()) return;//主动取消的话，不需要走错误流程
        onEnd();
        onfail(e);
    }

    protected void onEnd() {

    }

    @Override
    public void onNext(T data) {
        //// TODO 这边网络请求成功返回都不一样所以不能在这里统一写了（如果是自己公司需要规定一套返回方案）
        /// TODO 这里先统一处理为成功   我们要是想检查返回结果的集合是否是空，只能去子类回掉中完成了。
        if (target != null)
            target.setState(STATE_SUCCESS);
        onResponse();
        onResponse(data);
    }

    protected void onResponse(T data) {
        /**
         * 如果喜欢统一处理成功回掉也是可以的。
         * 不过获取到的数据都是不规则的，理论上来说需要判断该数据是否为null或者list.size()是否为0
         * 只有不成立的情况下，才能调用成功方法refreshView/()。如果统一处理就放在每个refreshView中处理。
         */
//        ((BaseView) target).refreshView(data);
    }

    protected void onResponse() {
    }

    protected void onfail(Throwable e) {
        if (!NetworkUtils.isAvailableByPing()) {
            ToastUtils.showShortToast("你连接的网络有问题，请检查路由器");
            if (target != null) {
                target.setState(STATE_ERROR);
            }
            return;
        }
        ToastUtils.showShortToast(e.toString());
        if (target != null) {
            target.setState(STATE_EMPTY);
        }
    }
}
