package com.example.yuxuehai.coolweather.dao;

/**
 * Created by yuxuehai on 17-2-14.
 */

public interface DataCallback {

    public void getDataSucceed(String data,String type);
    public void getDataFailed();
}
