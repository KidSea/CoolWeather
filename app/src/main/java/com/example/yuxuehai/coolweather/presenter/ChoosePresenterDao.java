package com.example.yuxuehai.coolweather.presenter;

/**
 * Created by yuxuehai on 17-2-14.
 */

public interface ChoosePresenterDao {


    public void queryProvinces();
    public void queryCities();
    public void queryCounties();

    public void clickCall(int position);

    public void pressbackCall();

}
