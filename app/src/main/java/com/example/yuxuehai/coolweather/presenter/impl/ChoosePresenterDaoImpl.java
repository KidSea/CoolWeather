package com.example.yuxuehai.coolweather.presenter.impl;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yuxuehai.coolweather.base.BasePresenter;
import com.example.yuxuehai.coolweather.bean.City;
import com.example.yuxuehai.coolweather.bean.County;
import com.example.yuxuehai.coolweather.bean.Province;
import com.example.yuxuehai.coolweather.dao.DataCallback;
import com.example.yuxuehai.coolweather.net.impl.DataDaoImpl;
import com.example.yuxuehai.coolweather.presenter.ChoosePresenterDao;
import com.example.yuxuehai.coolweather.ui.MainActivity;
import com.example.yuxuehai.coolweather.ui.WeatherActivity;
import com.example.yuxuehai.coolweather.utils.Utility;
import com.example.yuxuehai.coolweather.view.ChooseAreaView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.example.yuxuehai.coolweather.fragment.ChooseAreaFragment.LEVEL_CITY;
import static com.example.yuxuehai.coolweather.fragment.ChooseAreaFragment.LEVEL_COUNTY;
import static com.example.yuxuehai.coolweather.fragment.ChooseAreaFragment.LEVEL_PROVINCE;

/**
 * Created by yuxuehai on 17-2-14.
 */

public class ChoosePresenterDaoImpl extends BasePresenter implements ChoosePresenterDao {





    private Context mContext;

    /**
     * 省列表
     */
    private List<Province> provinceList;

    /**
     * 市列表
     */
    private List<City> cityList;

    /**
     * 县列表
     */

    public Province getSelectedProvince() {
        return selectedProvince;
    }

    /**
     * 选中的省份
     */
    private Province selectedProvince;

    public City getSelectedCity() {
        return selectedCity;
    }

    /**
     * 选中的城市
     */
    private City selectedCity;

    /**
     * 当前选中的级别
     */
    private int currentLevel;


    private List<County> countyList;

    private List<String> mDataList;

    private ChooseAreaView mAreaView;
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;


    private MainActivity mActivity = new MainActivity();

    public ChoosePresenterDaoImpl(Context context,
                                  ChooseAreaView view,
                                  List<String> data,
                                  ArrayAdapter<String> Adapter,
                                  ListView listView) {
        super(context);
        this.mContext  = context;
        this.mAreaView = view;
        this.mDataList = data;
        this.mAdapter = Adapter;
        this.mListView = listView;
    }



    private DataCallback mDataCallback = new DataCallback() {
        @Override
        public void getDataSucceed(String data,final String type) {
            boolean result = false;
            if ("province".equals(type)) {
                result = Utility.handleProvinceResponse(data);
            } else if ("city".equals(type)) {
                result = Utility.handleCityResponse(data, selectedProvince.getId());
            } else if ("county".equals(type)) {
                result = Utility.handleCountyResponse(data, selectedCity.getId());
            }
            if (result) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAreaView.closeProgressDialog();
                        if ("province".equals(type)) {
                            queryProvinces();
                        } else if ("city".equals(type)) {
                            queryCities();
                        } else if ("county".equals(type)) {
                            queryCounties();
                        }
                    }
                });
            }

        }

        @Override
        public void getDataFailed() {
            // 通过runOnUiThread()方法回到主线程处理逻辑
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAreaView.closeProgressDialog();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    };


    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    public void queryProvinces() {
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            mDataList.clear();
            for (Province province : provinceList) {
                mDataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            mAreaView.showProgressDialog();
            String address = "http://guolin.tech/api/china";
            DataDaoImpl.getInstance().queryFromServer(address, "province", mDataCallback);
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    public void queryCities() {
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            mDataList.clear();
            for (City city : cityList) {
                mDataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            mAreaView.showProgressDialog();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            DataDaoImpl.getInstance().queryFromServer(address, "city", mDataCallback);
        }
    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    public void queryCounties() {
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            mDataList.clear();
            for (County county : countyList) {
                mDataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            mAreaView.showProgressDialog();
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            DataDaoImpl.getInstance().queryFromServer(address, "county", mDataCallback);
        }
    }

    @Override
    public void clickCall(int position) {
        if (currentLevel == LEVEL_PROVINCE) {
            selectedProvince = provinceList.get(position);
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            selectedCity = cityList.get(position);
            queryCounties();
        } else if (currentLevel == LEVEL_COUNTY) {
            String weatherId = countyList.get(position).getWeatherId();
            if (mContext instanceof MainActivity) {
                mAreaView.gotoWeatherMain(weatherId);
            } else if (mContext instanceof WeatherActivity) {
                WeatherActivity activity = (WeatherActivity) mContext;
                activity.drawerLayout.closeDrawers();
                activity.swipeRefresh.setRefreshing(true);
                activity.getPresenterDao().requestWeather(weatherId);
            }
        }
    }

    @Override
    public void pressbackCall() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        }
    }

}
