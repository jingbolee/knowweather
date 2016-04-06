package com.lijingbo.knowweather.utils;

import com.google.gson.Gson;
import com.lijingbo.knowweather.domain.VersionInfo;

/**
 * @FileName: com.lijingbo.knowweather.utils.JsonParse.java
 * @Author: Li Jingbo
 * @Date: 2016-03-31 20:01
 * @Version V1.0 <描述当前版本功能>
 */
public class JsonParse {
    private static final String TAG = "JsonParse";

    public static VersionInfo parseJsonWithGson(String json){
        Gson gson=new Gson();
        return gson.fromJson(json, VersionInfo.class);
    }
}
