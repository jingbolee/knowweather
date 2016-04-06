package com.lijingbo.knowweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lijingbo.knowweather.R;
import com.lijingbo.knowweather.utils.EncodeUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ShowWeatherActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_choose_city, rl_refresh_weather;
    private SharedPreferences mPref;
    private String mCityName;
    private TextView tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityName = getIntent().getStringExtra("cityName");
        mPref = getSharedPreferences("cityInfo", MODE_PRIVATE);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        long currentTime=System.currentTimeMillis();
        long lastRefushTime = mPref.getLong("lastRefushTime", currentTime);
        //判断当前时间和上次刷新时间间隔是否超过3个小时，超过三个小时就重新获取天气信息
        if ( currentTime-lastRefushTime>=10800000 ){
            getWeatherInfo();
        }

        super.onResume();
    }

    private void initView() {
        setContentView(R.layout.activity_show_weather_info);
        rl_choose_city = (RelativeLayout) findViewById(R.id.rl_choose_city);
        rl_refresh_weather = (RelativeLayout) findViewById(R.id.rl_refresh_weather);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_location.setText(mCityName);
    }

    private void initData() {
        //获取天气信息
        getWeatherInfo();
    }

    private void initListener() {
        rl_choose_city.setOnClickListener(this);
        rl_refresh_weather.setOnClickListener(this);
    }
    //获取天气信息
    private void getWeatherInfo(){
        long lastRefushTime=System.currentTimeMillis();
        //保存当前刷新时间
        mPref.edit().putLong("lastRefushTime",lastRefushTime).commit();
        String areaid = mPref.getInt("areaId", 101010100) + "";
        String type = "forecast_f";
        String url = EncodeUrlUtils.getUrl(areaid, type);
        RequestParams entity = new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback< String >() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ShowWeatherActivity.this, result, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ShowWeatherActivity.this, "网络错误，请稍后刷新尝试更新天气", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            //选择城市
            case R.id.rl_choose_city:
                Intent intent = new Intent(ShowWeatherActivity.this, ChooseProvActivity.class);
                startActivity(intent);
                break;
            //天气刷新
            case R.id.rl_refresh_weather:

                break;
        }

    }
}
