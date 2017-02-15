package com.example.yuxuehai.coolweather.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.yuxuehai.coolweather.base.BasePresenter;
import com.example.yuxuehai.coolweather.gson.Weather;
import com.example.yuxuehai.coolweather.net.impl.DataDaoImpl;
import com.example.yuxuehai.coolweather.presenter.WeatherPresenterDao;
import com.example.yuxuehai.coolweather.ui.WeatherActivity;
import com.example.yuxuehai.coolweather.utils.Utility;
import com.example.yuxuehai.coolweather.view.WeatherView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yuxuehai on 17-2-15.
 */

public class WeatherPresenterDaoImpl extends BasePresenter implements WeatherPresenterDao {

    private WeatherView mWeatherView;
    private WeatherActivity mActivity;
    private Context mContext;
    private String mWeatherId;

    private DataDaoImpl mDao;

    public WeatherPresenterDaoImpl(Context context, WeatherActivity activity, WeatherView view, String id) {
        super(context);
        mContext = context;
        mActivity = activity;
        mWeatherView = view;
        mWeatherId = id;
        mDao = DataDaoImpl.getInstance();
    }

    /**
     * 根据天气id请求城市天气信息。
     */
    @Override
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";

        mDao.requestWeather(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWeatherView.showGetInfoFailed();
                        mWeatherView.refreshUI();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            mWeatherView.showWeatherInfo(weather);
                        } else {
                            mWeatherView.showGetInfoFailed();
                        }
                        mWeatherView.refreshUI();
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 加载必应每日一图
     */
    @Override
    public void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";

        mDao.requestBingPic(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWeatherView.loadImage(bingPic);
                    }
                });
            }
        });
    }
}
