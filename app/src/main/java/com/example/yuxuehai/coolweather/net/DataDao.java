package com.example.yuxuehai.coolweather.net;

/**
 * Created by yuxuehai on 17-2-14.
 */

public interface DataDao {

    public  void queryFromServer(String address, final String type);
}
