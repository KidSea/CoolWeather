package com.example.yuxuehai.coolweather.view;

/**
 * Created by yuxuehai on 17-2-14.
 */

public interface ChooseAreaView {


    public void showProgressDialog();
    public void closeProgressDialog();

    public void setProviceTitle();
    public void setCityTitle();
    public void setCountyTitle();

    public void gotoWeatherMain(String weatherId);
}
