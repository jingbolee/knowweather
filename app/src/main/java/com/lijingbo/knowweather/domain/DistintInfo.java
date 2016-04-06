package com.lijingbo.knowweather.domain;

/**
 * @FileName: com.lijingbo.knowweather.domain.DistintInfo.java
 * @Author: Li Jingbo
 * @Date: 2016-04-05 21:33
 * @Version V1.0 <描述当前版本功能>
 */
public class DistintInfo {
    private static final String TAG = "DistintInfo";
    private String distintName;
    private String provName;

    public String getDistintName() {
        return distintName;
    }

    public void setDistintName(String distintName) {
        this.distintName = distintName;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }
}
