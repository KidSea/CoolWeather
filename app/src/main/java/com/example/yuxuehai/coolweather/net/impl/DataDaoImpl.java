package com.example.yuxuehai.coolweather.net.impl;

import com.example.yuxuehai.coolweather.dao.DataCallback;
import com.example.yuxuehai.coolweather.net.DataDao;
import com.example.yuxuehai.coolweather.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yuxuehai on 17-2-14.
 */

public class DataDaoImpl implements DataDao {


    private static DataDaoImpl mDataDao;


    public static synchronized DataDaoImpl getInstance(){

        if(mDataDao == null){
            mDataDao = new DataDaoImpl();
        }

        return mDataDao;
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据。
     */
    public void queryFromServer(String address, final String type, final DataCallback callback) {

        HttpUtils.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                callback.getDataSucceed(responseText,type);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.getDataFailed();
            }
        });
    }

    @Override
    public void requestWeather(String weatherUrl, Callback callback) {
        HttpUtils.sendOkHttpRequest(weatherUrl, callback);
    }

    @Override
    public void requestBingPic(String bingpicUrl, Callback callback) {
        HttpUtils.sendOkHttpRequest(bingpicUrl, callback);
    }


}
