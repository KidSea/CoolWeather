package com.example.yuxuehai.coolweather.net.impl;

import com.example.yuxuehai.coolweather.net.DataDao;

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

    @Override
    public void queryFromServer(String address, final String type) {

    }
}
