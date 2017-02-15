package com.example.yuxuehai.coolweather.net;

import com.example.yuxuehai.coolweather.dao.DataCallback;

import okhttp3.Callback;

/**
 * Created by yuxuehai on 17-2-14.
 */

public interface DataDao {

    public  void queryFromServer(String address, final String type, DataCallback callback);

    public void requestWeather(String weatherUrl, Callback callback);

    public void requestBingPic(String bingpicUrl, Callback callback);


}
