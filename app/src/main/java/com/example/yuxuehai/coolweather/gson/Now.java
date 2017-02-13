package com.example.yuxuehai.coolweather.gson;

import com.google.gson.annotations.SerializedName;


/**
 * Created by yuxuehai on 17-2-13.
 */
public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;

    }

}
