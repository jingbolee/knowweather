package com.lijingbo.knowweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lijingbo.knowweather.R;
import com.lijingbo.knowweather.db.dao.ChooseCityDao;
import com.lijingbo.knowweather.domain.CityInfo;

import java.util.List;

public class ChooseCityActivity extends AppCompatActivity {
    private ListView lv_city;
    private String mProvName;
    private String mDistineName;
    private List< CityInfo > cityInfos;
    private ChooseCityAdapter adapter;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProvName = getIntent().getStringExtra("provName");
        mDistineName = getIntent().getStringExtra("distineName");
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_choose_city);
        lv_city = (ListView) findViewById(R.id.lv_city);
    }

    private void initData() {
        mPref = getSharedPreferences("cityInfo", MODE_PRIVATE);
        cityInfos = ChooseCityDao.findCity(mProvName, mDistineName);
        adapter = new ChooseCityAdapter();
        lv_city.setAdapter(adapter);
    }

    private void initListener() {
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                String cityName = cityInfos.get(position).getCityName();
                int areaId = cityInfos.get(position).getAreaId();
                Intent intent = new Intent(ChooseCityActivity.this, ShowWeatherActivity.class);
                intent.putExtra("cityName", cityName);
                mPref.edit().putString("cityName", cityName).commit();
                mPref.edit().putInt("areaId", areaId).commit();
                startActivity(intent);
                finish();
            }
        });
    }

    class ChooseCityAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cityInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CityInfo cityInfo = cityInfos.get(position);
            TextView tv = new TextView(ChooseCityActivity.this);
            tv.setText(cityInfo.getCityName());
            return tv;
        }
    }

}
