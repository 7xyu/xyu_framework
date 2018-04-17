package com.wisdom.framework.base.view;

import android.os.Bundle;

import com.wisdom.framework.base.BasePresenter;
import com.wisdom.framework.utils.ArrayUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chejiangwei on 2017/8/21.
 * Describe:
 */

public abstract class DaggerActivity extends BaseActivity {
    //    @Inject
//    protected T mPresenter;
    private Set<BasePresenter> mBizs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
//        addBiz(mPresenter);
    }

    @Override
    protected void onDestroy() {
        if (!ArrayUtils.isEmpty(mBizs))
            for (BasePresenter p : mBizs) p.detachView();
        super.onDestroy();
    }


    /**
     * 增加业务层
     */
    public void addBiz(BasePresenter biz) {
        if (biz == null) return;
        if (mBizs == null) mBizs = new HashSet<>();
        biz.attachView(this);
        mBizs.add(biz);
    }

    protected abstract void initInject();
}
