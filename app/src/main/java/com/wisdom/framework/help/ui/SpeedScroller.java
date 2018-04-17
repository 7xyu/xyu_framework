package com.wisdom.framework.help.ui;

import android.content.Context;
import android.widget.Scroller;

/**
 * Created by chejiangwei on 2017/12/28.
 * Describe:目前先用在Viewpager，切换滑动太快体验差
 */

public class SpeedScroller extends Scroller {

    private int mDuration;

    public SpeedScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        //直接使用我们的时间
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public SpeedScroller setDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

}
