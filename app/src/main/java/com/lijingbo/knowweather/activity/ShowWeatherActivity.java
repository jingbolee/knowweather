package com.lijingbo.knowweather.activity;

import android.app.Activity;
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


    private void initView() {
        setContentView(R.layout.activity_show_weather_info);
        rl_choose_city = (RelativeLayout) findViewById(R.id.rl_choose_city);
        rl_refresh_weather = (RelativeLayout) findViewById(R.id.rl_refresh_weather);
        tv_location= (TextView) findViewById(R.id.tv_location);
        tv_location.setText(mCityName);
    }

    private void initData() {
        String areaid=mPref.getInt("areaId",101010100)+"";
        String type="forecast_f";
        String url= EncodeUrlUtils.getUrl(areaid,type);
        RequestParams entity=new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback< String >() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ShowWeatherActivity.this,result,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initListener() {
        rl_choose_city.setOnClickListener(this);
        rl_refresh_weather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ){
            case R.id.rl_choose_city:

                break;

            case R.id.rl_refresh_weather:

                break;
        }

    }
}
