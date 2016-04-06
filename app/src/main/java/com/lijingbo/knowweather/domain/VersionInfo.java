package com.lijingbo.knowweather.domain;

/**
 * @FileName: com.lijingbo.knowweather.domain.VersionInfo.java
 * @Author: Li Jingbo
 * @Date: 2016-03-30 22:08
 * @Version V1.0 <描述当前版本功能>
 */
public class VersionInfo {
    private static final String TAG = "VersionInfo";
    private int versionCode;
    private String versionName;
    private String description;
    private String downLoadUrl;
    private boolean isUpdate;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }
}
