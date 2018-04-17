package com.wisdom.framework.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.wisdom.framework.base.BasePresenter;
import com.wisdom.framework.base.BaseView;
import com.wisdom.framework.base.view.interfaces.LifeSubscription;
import com.wisdom.framework.interfaces.ILoading;
import com.wisdom.framework.structs_function.FunctionManager;
import com.wisdom.framework.structs_function.IFunction;
import com.wisdom.framework.utils.ArrayUtils;
import com.wisdom.framework.web.Callback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chejiangwei on 2017/6/13.
 * Describe:适用于普通fragment，而不是viewpager包裹的fragment
 */

public abstract class BaseFragment extends Fragment implements LifeSubscription, BaseView, ILoading {
    //    @Inject
//    protected P mPresenter;
    protected FunctionManager mFunctionManager;
    protected CompositeSubscription mCompositeSubscription;//RxJava订阅者集合
    protected Unbinder mUnbinder;


    protected View mViewRoot;
    private Set<BasePresenter> mBizs;

    private boolean isFirstLayout = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFunction) {
            ((IFunction) context).setFunctions(getTag());
        }
    }

    @Override
    public void onDetach() {

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
            this.mCompositeSubscription = null;
        }
        if (!ArrayUtils.isEmpty(mBizs)) {
            for (BasePresenter p : mBizs) {
                p.detachView();
            }
        }

        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
      /*  View view = getView();
        if (view != null) {
            if (hidden) {
                view.setFitsSystemWindows(false);
            } else {
                view.setFitsSystemWindows(true);
            }
//            view.requestApplyInsets();
        }*/
        //对应的子fragment也要调用
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!ArrayUtils.isEmpty(fragments)) {
            for (Fragment fragment :
                    fragments) {
                if (fragment instanceof BaseFragment) {
                    fragment.onHiddenChanged(hidden);
                }

            }
        }
        super.onHiddenChanged(hidden);
    }


    @Override
    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);
    }

    /**
     * 第一次布局完成
     */
    protected void onFirstLayout() {

    }

    public void setFunctionManager(FunctionManager functionManager) {
        this.mFunctionManager = functionManager;
    }

    /**
     * 增加业务层
     */
    public void addBiz(BasePresenter biz) {
        if (biz == null) return;
        if (mBizs == null) {
            mBizs = new HashSet<>();
        }
        biz.attachView(this);
        mBizs.add(biz);
    }

    @Override
    public void tip(CharSequence charSequence) {
        BaseView baseView = (BaseView) getActivity();
        baseView.tip(charSequence);
    }

    @Override
    public void netLoading(Callback callback) {
        ILoading iLoading = (ILoading) getActivity();
        iLoading.netLoading(callback);
    }

    @Override
    public void loading(boolean loading) {
        ILoading iLoading = (ILoading) getActivity();
        iLoading.loading(loading);
    }

    /**
     * 后退
     */
    public boolean onBackPressed() {
        return true;
    }


    public abstract int layoutId();

    /**
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void loadData();

    /**
     * dagger2注入
     */
    protected abstract void initInject();
}
