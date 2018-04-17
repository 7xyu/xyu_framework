package com.wisdom.framework.help.exception;

/**
 * Created by chejiangwei on 2017/12/15.
 * Describe:
 */

public class FragmentNotFoundException extends Exception {
    public FragmentNotFoundException(String fragmentTag) {
        super(String.format("没找到tag为%1s的Fragement", fragmentTag));
    }

    public FragmentNotFoundException() {

    }
}
