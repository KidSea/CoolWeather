package com.example.yuxuehai.coolweather.view;

import com.example.yuxuehai.coolweather.gson.Weather;

/**
 * Created by yuxuehai on 17-2-15.
 */

public interface WeatherView {

    public void showWeatherInfo(Weather weather);

    public void showGetInfoFailed();
    public void refreshUI();
    public void loadImage(String bingPic);
}
