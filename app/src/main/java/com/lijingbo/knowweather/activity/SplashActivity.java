package com.lijingbo.knowweather.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lijingbo.knowweather.R;
import com.lijingbo.knowweather.domain.VersionInfo;
import com.lijingbo.knowweather.utils.JsonParse;
import com.lijingbo.knowweather.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends Activity {
    public static final String TAG = "SplashActivity";
    private static final int UPDATE_SUGGESTION = 6782;
    private static final int UPDATE_MUST = 8888;
    private static final int UPDATE_ERROR_NETWORK = 6666;
    private TextView tv_version;
    //    private RelativeLayout rl_background;
    private ImageView iv_splash_background;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ( msg.what ) {
                case UPDATE_SUGGESTION:
                    showUpdateDialog(mUpdate);
                    break;
                case UPDATE_MUST:
                    showUpdateDialog(mUpdate);
                    break;
                case UPDATE_ERROR_NETWORK:
                    Toast.makeText(getApplicationContext(), "网络错误...", Toast.LENGTH_LONG).show();
                    goToWeatherActivity();
                    break;
            }
        }
    };


    //服务器返回的版本信息
    private String mVersionName;
    private String mDescription;
    private String mDownLoadUrl;
    private boolean mUpdate;
    private int mVersionCode;
    private SharedPreferences mPref;
    private String mCityName;//已保存的城市名称


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        copyDB("weather.db");
    }


    private void initView() {
        mPref = getSharedPreferences("cityInfo", MODE_PRIVATE);
        mCityName = mPref.getString("cityName", null);
        setContentView(R.layout.activity_splash);
//        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        iv_splash_background = (ImageView) findViewById(R.id.iv_splash_background);
        //使用图片框架Picasso加载图片。设置Splash页面的背景图，以后可以使用网络图片，做到联网更新Splash页面
        Picasso.with(this).load(R.mipmap.splash).fit().into(iv_splash_background);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本号:" + getAppVersion()[0]);
    }


    private void initData() {
        checkVersion();
    }


    /**
     * 获取当前版本名称和版本号
     */
    private String[] getAppVersion() {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(getPackageName(), 0);
        } catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
        return new String[]{packageInfo.versionName, String.valueOf(packageInfo.versionCode)};
    }

    /**
     * 从服务器获取版本信息
     */
    private void checkVersion() {
        final long startTime = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                Message msg = Message.obtain();
                try {
                    URL url = new URL("http://192.168.100.103:8080/update.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    if ( connection.getResponseCode() == 200 ) {
                        InputStream inputStream = connection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffers = new byte[1024];
                        int len;
                        while ( (len = inputStream.read(buffers)) != -1 ) {
                            baos.write(buffers, 0, len);
                        }
                        String result = baos.toString();
                        LogUtils.e(TAG, "获取到的服务器信息为：" + result);
                        baos.close();
                        inputStream.close();
                        VersionInfo versionInfo = JsonParse.parseJsonWithGson(result);
                        mVersionCode = versionInfo.getVersionCode();
                        mUpdate = versionInfo.isUpdate();
                        mVersionName = versionInfo.getVersionName();
                        mDescription = versionInfo.getDescription();
                        mDownLoadUrl = versionInfo.getDownLoadUrl();
                        LogUtils.e(TAG, "获取到的服务器信息详情为：" + mVersionName + mDescription + mDownLoadUrl);
                        if ( mVersionCode > Integer.valueOf(getAppVersion()[1]) ) {
                            if ( mUpdate ) {
                                //强制更新
                                msg.what = UPDATE_MUST;
                            } else {
                                //非强制更新
                                msg.what = UPDATE_SUGGESTION;
                            }
                        }
                    }
                } catch ( IOException e ) {
                    //网络错误
                    msg.what = UPDATE_ERROR_NETWORK;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;
                    if ( timeUsed < 2000 ) {
                        SystemClock.sleep(2000 - timeUsed);
                    }
                    mHandler.sendMessage(msg);
                    if ( connection != null ) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    //弹出app更新的Dialog
    private void showUpdateDialog(boolean mUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        builder.setTitle("版本更新 " + mVersionName);
        builder.setMessage(mDescription);
        builder.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashActivity.this, "下载更新", Toast.LENGTH_SHORT).show();
            }
        });
        //假如不是强制更新，显示取消按钮
        if ( !mUpdate ) {
            builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    goToWeatherActivity();
                }
            });
        }
        builder.show();
    }


    //进入到ShowWeatherActivity页面
    private void goToWeatherActivity() {
        if ( mCityName != null ) {
            //保存有城市名称时，进入到显示天气页面
            Intent intent = new Intent(SplashActivity.this, ShowWeatherActivity.class);
            intent.putExtra("cityName",mCityName);
            startActivity(intent);
            finish();
        } else {
            //进入选择城市页面
            Intent intent = new Intent(SplashActivity.this, ChooseProvActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //把asset文件下的数据库拷贝到app的目录下
    private void copyDB(String dbName) {
        File file = new File(getFilesDir(), dbName);
        InputStream inputStream = null;
        OutputStream opt = null;
        if ( file.exists() ) {
            return;
        } else {
            try {
                inputStream = getAssets().open(dbName);
                opt = new FileOutputStream(file);
                byte[] buff = new byte[1024];
                int len;
                while ( (len = inputStream.read(buff)) != -1 ) {
                    opt.write(buff, 0, len);
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                try {
                    opt.close();
                    inputStream.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

}
