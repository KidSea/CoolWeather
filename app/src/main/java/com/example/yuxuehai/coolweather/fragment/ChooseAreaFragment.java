package com.example.yuxuehai.coolweather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuxuehai.coolweather.R;
import com.example.yuxuehai.coolweather.base.BaseFragment;
import com.example.yuxuehai.coolweather.presenter.impl.ChoosePresenterDaoImpl;
import com.example.yuxuehai.coolweather.ui.WeatherActivity;
import com.example.yuxuehai.coolweather.view.ChooseAreaView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuxuehai on 17-2-13.
 */

public class ChooseAreaFragment extends BaseFragment implements ChooseAreaView {

    private static final String TAG = "ChooseAreaFragment";

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView mTitileText;
    private Button mBackButton;
    private ListView mListView;

    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    private ChoosePresenterDaoImpl mPresenter;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area, null);
        return view;
    }


    @Override
    protected void initView(View rootView) {
        mTitileText = (TextView) rootView.findViewById(R.id.title_text);
        mBackButton = (Button) rootView.findViewById(R.id.back_button);
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, dataList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new ChoosePresenterDaoImpl(getContext(), this, dataList, mAdapter, mListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.clickCall(position);
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pressbackCall();
            }
        });
        mPresenter.queryProvinces();
    }

    @Override
    protected void load() {

    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 显示进度对话框
     */
    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    @Override
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setProviceTitle() {
        mTitileText.setText("中国");
        mBackButton.setVisibility(View.GONE);
    }

    @Override
    public void setCityTitle() {
        mTitileText.setText(mPresenter.getSelectedProvince().getProvinceName());
        mBackButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCountyTitle() {
        mTitileText.setText(mPresenter.getSelectedCity().getCityName());
        mBackButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void gotoWeatherMain(String weatherId) {
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.putExtra("weather_id", weatherId);
        startActivity(intent);
        getActivity().finish();
    }


}
