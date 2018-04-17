package com.wisdom.framework.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by chejiangwei on 2017/6/13.
 * Describe:只适用于Viewpager组合下，带有懒加载功能
 */

public abstract class LazyFragment extends BaseFragment {

    private boolean mIsVisible = false;     // fragment是否显示了

    private boolean isPrepared = false;//是否准备好了加载数据（直接理解为，重新）

    private boolean isFirst = true; //第一次加载界面

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (LazyFragment.this.mViewRoot == null) {
            LazyFragment.this.mViewRoot = inflater.inflate(layoutId(), container, false);
        }
        if (mUnbinder == null) mUnbinder = ButterKnife.bind(LazyFragment.this, mViewRoot);
        initView();
        isPrepared = true;
        loadBaseData();
        return mViewRoot;
    }


    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//fragment可见
            mIsVisible = true;
            onVisible();
        } else if (mIsVisible) {//fragment不可见
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void onVisible() {
        if (isFirst) {
            initInject();
//            if (mPresenter!=null){
//                mPresenter.attachView(this);}
        }
        loadBaseData();//根据获取的数据来调用showView()切换界面
    }

    public void loadBaseData() {
        if (!mIsVisible || !isPrepared) {
            return;
        }
        loadData();
        if (isFirst) isFirst = false;
        if (isPrepared) isPrepared = false;
    }


    protected void onInvisible() {
    }

    protected abstract void initView();
}
