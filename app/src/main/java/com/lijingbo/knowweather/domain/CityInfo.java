package com.lijingbo.knowweather.domain;

/**
 * @FileName: com.lijingbo.knowweather.domain.CityInfo.java
 * @Author: Li Jingbo
 * @Date: 2016-04-05 21:36
 * @Version V1.0 <描述当前版本功能>
 */
public class CityInfo {
    private static final String TAG = "CityInfo";
    private String cityName;
    private String distintName;
    private String provName;
    private int areaId;

    public String getDistintName() {
        return distintName;
    }

    public void setDistintName(String distintName) {
        this.distintName = distintName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
