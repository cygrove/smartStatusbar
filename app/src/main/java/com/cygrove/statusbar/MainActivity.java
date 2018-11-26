package com.cygrove.statusbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import statusbar.cygrove.com.statusbarhelper.StatusBarHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarHelper.darkMode(this);
    }
}