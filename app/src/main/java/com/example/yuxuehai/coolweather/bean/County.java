package com.example.yuxuehai.coolweather.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuxuehai on 17-2-13.
 */

public class County extends DataSupport {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    private int cityId;
    private int id;
    private String countyName;
    private String weatherId;



}

