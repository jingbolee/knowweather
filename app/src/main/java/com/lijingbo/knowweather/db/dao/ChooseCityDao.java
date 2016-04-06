package com.lijingbo.knowweather.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lijingbo.knowweather.domain.CityInfo;
import com.lijingbo.knowweather.domain.DistintInfo;
import com.lijingbo.knowweather.domain.ProvInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.lijingbo.knowweather.db.dao.ChooseCityDao.java
 * @Author: Li Jingbo
 * @Date: 2016-04-05 21:27
 * @Version V1.0 <描述当前版本功能>
 */
public class ChooseCityDao {
    private static final String TAG = "ChooseCityDao";
    private static String PATH = "data/data/com.lijingbo.knowweather/files/weather.db";

    public static List< ProvInfo > findProv() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        List< ProvInfo > provInfos = new ArrayList<>();
        Cursor cursor = db.query(true, "areaid_v", new String[]{"PROVCN"}, null, null, null, null, null, null);
        while ( cursor.moveToNext() ) {
            ProvInfo provInfo = new ProvInfo();
            provInfo.setProvName(cursor.getString(0));
            provInfos.add(provInfo);
        }
        cursor.close();
        db.close();
        return provInfos;

    }

    public static List< DistintInfo > findDistint(String provName) {
        List< DistintInfo > distintInfos = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.query(true, "areaid_v", new String[]{"DISTRICTCN"}, "PROVCN=?", new String[]{provName}, null, null, null, null);
        while ( cursor.moveToNext() ) {
            DistintInfo distintInfo = new DistintInfo();
            distintInfo.setProvName(provName);
            distintInfo.setDistintName(cursor.getString(0));
            distintInfos.add(distintInfo);
        }
        cursor.close();
        db.close();
        return distintInfos;
    }

    public static List< CityInfo > findCity(String provName,String distintName) {
        List< CityInfo > cityInfos = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.query(true, "areaid_v", new String[]{"NAMECN","AREAID"}, "PROVCN=? and DISTRICTCN=?", new String[]{provName,distintName}, null, null, null, null);
        while ( cursor.moveToNext() ) {
            CityInfo cityInfo = new CityInfo();
            cityInfo.setProvName(provName);
            cityInfo.setDistintName(distintName);
            cityInfo.setCityName(cursor.getString(0));
            cityInfo.setAreaId(Integer.parseInt(cursor.getString(1)));
            cityInfos.add(cityInfo);
        }
        cursor.close();
        db.close();
        return cityInfos;
    }
}
