package com.wisdom.framework.model;

import java.util.List;

/**
 * Created by chejiangwei on 2017/8/18.
 * Describe:分页数据
 */

public interface IPageData<T> {
    /*是否是最后一页*/
    boolean isLastPage();

    /*当前页数*/
    int getCurrentPage();

    /*单页请求多少条*/
    int getLimit();

    /*总页数*/
    int totalPages();

    /*数据总数*/
    long total();

    /*当前请求了多少条*/
    int getCurrentNums();

    public List<T> getDatas();

}
