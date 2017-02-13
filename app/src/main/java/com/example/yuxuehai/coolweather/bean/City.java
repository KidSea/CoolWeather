package com.example.yuxuehai.coolweather.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuxuehai on 17-2-13.
 */

public class City extends DataSupport {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;


}