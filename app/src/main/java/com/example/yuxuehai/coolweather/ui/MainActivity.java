package com.example.yuxuehai.coolweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.yuxuehai.coolweather.R;
import com.example.yuxuehai.coolweather.base.BaseActivity;
/**
 * Created by yuxuehai on 17-2-14.
 */

public class MainActivity extends BaseActivity {


    @Override
    protected Activity getActivityContext() {
        return super.getActivityContext();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString("weather", null)!= null){
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
