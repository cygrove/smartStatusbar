package com.cygrove.statusbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import statusbar.cygrove.com.statusbarhelper.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();
    }
}