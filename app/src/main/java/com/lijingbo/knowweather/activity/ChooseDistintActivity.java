package com.lijingbo.knowweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lijingbo.knowweather.R;
import com.lijingbo.knowweather.db.dao.ChooseCityDao;
import com.lijingbo.knowweather.domain.DistintInfo;

import java.util.List;

public class ChooseDistintActivity extends Activity {

    private String mProvName;
    private ListView lv_distint;
    ChooseDistintAdapter adapter;
    List< DistintInfo > distintInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProvName = getIntent().getStringExtra("provName");
        initView();
        initData();
        initListener();
    }


    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_choose_distint);
        lv_distint= (ListView) findViewById(R.id.lv_distint);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        distintInfos = ChooseCityDao.findDistint(mProvName);
        adapter=new ChooseDistintAdapter();
        lv_distint.setAdapter(adapter);
    }


    private void initListener() {
        lv_distint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                String distineName=distintInfos.get(position).getDistintName();
                Intent intent=new Intent(ChooseDistintActivity.this,ChooseCityActivity.class);
                intent.putExtra("provName",mProvName);
                intent.putExtra("distineName",distineName);
                startActivity(intent);
            }
        });
    }


    class ChooseDistintAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return distintInfos.size();
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
            DistintInfo distintInfo=distintInfos.get(position);
            TextView tv=new TextView(ChooseDistintActivity.this);
            tv.setText(distintInfo.getDistintName());
            return tv;
        }
    }

}
