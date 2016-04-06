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
import com.lijingbo.knowweather.domain.ProvInfo;

import java.util.List;

public class ChooseProvActivity extends Activity {
    private ListView lv_prov;
    private MyAdapter adapter;
    private List< ProvInfo > provInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }



    private void initView() {
        setContentView(R.layout.activity_choose_prov);
        lv_prov = (ListView) findViewById(R.id.lv_prov);

    }

    private void initData() {
        provInfos = ChooseCityDao.findProv();
        adapter=new MyAdapter();
        lv_prov.setAdapter(adapter);

    }
    private void initListener() {
        lv_prov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                String provName=provInfos.get(position).getProvName();
                Intent intent=new Intent(ChooseProvActivity.this,ChooseDistintActivity.class);
                intent.putExtra("provName",provName);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return provInfos.size();
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
            ProvInfo provInfo=provInfos.get(position);
            TextView tv=new TextView(ChooseProvActivity.this);
            tv.setText(provInfo.getProvName());
            return tv;
        }
    }
}
