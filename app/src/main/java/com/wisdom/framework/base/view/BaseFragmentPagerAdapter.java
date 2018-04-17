package com.wisdom.framework.base.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wisdom.framework.utils.ArrayUtils;

import java.util.List;

/**
 * Created by chejiangwei on 2017/7/5.
 * Describe:
 */

public class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {


    List<? extends Fragment> fragments;
    List<String> titles;

    // 标题数组
    public BaseFragmentPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
        this(fm, fragments, null);
    }

    // 标题数组
    public BaseFragmentPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (ArrayUtils.isEmpty(titles)) return super.getPageTitle(position);
        return titles.get(position);
    }

}
