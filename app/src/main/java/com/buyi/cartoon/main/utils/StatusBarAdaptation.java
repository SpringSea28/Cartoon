package com.buyi.cartoon.main.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.tencent.mmkv.MMKV;

public class StatusBarAdaptation {

    public static void initStatusBar(Activity context,View rootView){
        if (hasKitKat() && !hasLollipop()) {
            //透明状态栏
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (hasLollipop()) {
            Window window = context.getWindow();
            if ("HUAWEI".equals(Build.MANUFACTURER) && isLollipop()) {
                setHalfTransparentStatusBar(window);
            } else {
                setFullTransparentStatusBar(window);
            }
            ViewGroup mContentView = (ViewGroup) context.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                mChildView.setFitsSystemWindows(false);
            }
        }
        int mStatusBarHeight = getStatusBarHeight();
//        View mStatusBarView = new View(this);
//        bindView.getRoot().addView(mStatusBarView, 0,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mStatusBarHeight));
        int paddingStart = rootView.getPaddingStart();
        int paddingEnd = rootView.getPaddingEnd();
        mStatusBarHeight = 0;
        rootView.setPadding(paddingStart, mStatusBarHeight, paddingEnd, 0);
    }

    private static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    private static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private static boolean isLollipop() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setHalfTransparentStatusBar(Window window) {
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setFullTransparentStatusBar(Window window) {
        //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 计算状态栏高度高度
     * getStatusBarHeight
     *
     * @return
     */
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static boolean isNightMode(Context context) {
        try {
            return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        } catch (Exception ex) {

        }
        return false;
    }
}
