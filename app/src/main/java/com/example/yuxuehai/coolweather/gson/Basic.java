package com.example.yuxuehai.coolweather.gson;

import com.google.gson.annotations.SerializedName;


/**
 * Created by yuxuehai on 17-2-13.
 */
public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;

    }

}
