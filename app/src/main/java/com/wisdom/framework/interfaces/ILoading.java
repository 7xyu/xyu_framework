package com.wisdom.framework.interfaces;

import com.wisdom.framework.web.Callback;

/**
 * Created by chejiangwei on 2017/6/21.
 * Describe:
 */

public interface ILoading {
//    void toast(CharSequence msg);
    void loading(boolean loading);
    void netLoading(Callback callback);
}
