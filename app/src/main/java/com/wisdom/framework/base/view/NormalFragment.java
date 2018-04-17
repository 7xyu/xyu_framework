package com.wisdom.framework.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by chejiangwei on 2017/10/10.
 * Describe:
 */

public abstract  class NormalFragment extends BaseFragment {
    private boolean isFirst = true; //只加载一次界面
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isFirst) {
            NormalFragment.this.mViewRoot = inflater.inflate(layoutId(), container, false);
            mUnbinder = ButterKnife.bind(NormalFragment.this, mViewRoot);
        }
        return mViewRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
        loadData();//根据获取的数据来调用showView()切换界面
    }
}
