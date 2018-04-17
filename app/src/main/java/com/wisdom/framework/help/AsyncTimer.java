package com.wisdom.framework.help;


import com.wisdom.framework.utils.UIUtils;

/**
 * Created by chejiangwei on 2017/7/6.
 * Describe:
 */

public class AsyncTimer {

    private onTimePass onTimePass;
    private int seconds;
    private int duration = 1;

    public AsyncTimer(onTimePass onTimePass) {
        this.onTimePass = onTimePass;
    }

    /**
     * 设置倒计时间隔
     *
     * @param duration
     * @return
     */
    public AsyncTimer setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置倒计时时间
     *
     * @param seconds
     */
    public void cutDown(int seconds) {
        this.seconds = seconds;
        if (seconds <= 0) {
            onTimePass.stop();
            destroy();
            return;
        }
        onTimePass.passTime(seconds);
        UIUtils.postDelayed(cutDownRunnable, duration * 1000);

    }


    private Runnable cutDownRunnable = new Runnable() {
        @Override
        public void run() {
            cutDown(seconds -= duration);
        }
    };

    public void destroy() {
        UIUtils.removeCallbacks(cutDownRunnable);
    }

    public interface onTimePass {
        void passTime(int seconds);

        void stop();
    }


}
