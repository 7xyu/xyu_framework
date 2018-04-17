package com.wisdom.framework.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by xyu on 2017/1/25.
 * Description:
 */
public class WebViewUtils {

    public static WebSettings configWebView(WebView webView) {
        // 打开javascript，设置默认字体大小
        webView.requestFocusFromTouch();
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(18);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setSupportZoom(true); // 支持缩放
//        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        setZoom(settings);
        settings.setUseWideViewPort(true);
        return settings;
    }

    public static void setZoom(WebSettings webSettings) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) (UIUtils.getContext().getSystemService(Context.WINDOW_SERVICE));
        wm.getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        com.blankj.utilcode.utils.LogUtils.d("sanwn", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
    }

    public static void syncCookies(String url) {
//CookieSyncManager负责管理webView中的cookie
        CookieSyncManager.createInstance(UIUtils.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
      /*  try {
            List<Cookie> cookies = OkGo.getInstance().getCookieJar().getCookieStore().getCookie(HttpUrl.parse(url));//获取okhttp网络请求中持久化的cookie
            for (int i = 0; i < cookies.size(); i++) {
                Cookie cookie = cookies.get(i);
                if (cookie.name().equals("JSESSIONID")) {
// cookies是在HttpClient中获得的cookie，这里是从okhttp中获得，加入到webView的cookie中
                    cookieManager.setCookie(url, cookie.name() + "=" + cookie.value());
                }
            }
            CookieSyncManager.getInstance().sync();
        }catch(Exception e){
            e.printStackTrace();
        }*/

    }

    public static void close(WebView webView) {
        if (webView == null) return;
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webView.clearHistory();
        webView.removeAllViews();
        ((ViewGroup) webView.getParent()).removeView(webView);
//清除WebView中cookie
        CookieSyncManager.createInstance(UIUtils.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
//清除okhttp中cookie
//        OkGo.getInstance().getCookieJar().getCookieStore().removeAllCookie();
//        MyApplication.getInstance().clearCookies(this);
        webView.destroy();
    }
}
