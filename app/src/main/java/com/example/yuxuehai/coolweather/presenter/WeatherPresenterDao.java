package com.example.yuxuehai.coolweather.presenter;

/**
 * Created by yuxuehai on 17-2-15.
 */

public interface WeatherPresenterDao {

    public void requestWeather(final String weatherId);
    public void loadBingPic();
}
