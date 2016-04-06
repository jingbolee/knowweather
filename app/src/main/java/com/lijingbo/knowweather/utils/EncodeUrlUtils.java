package com.lijingbo.knowweather.utils;

import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @FileName: com.lijingbo.knowweather.utils.EncodeUrlUtils.java
 * @Author: Li Jingbo
 * @Date: 2016-04-05 10:37
 * @Version V1.0 <描述当前版本功能>
 */
public class EncodeUrlUtils {
    private static final String TAG = "EncodeUrlUtils";

    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'
    };
    //气象平台appid和privatekey
    static String appid = "42fc70f1eef1d273";
    static String appid_8 = "42fc70";
    static String private_key = "f5fe80_SmartWeatherAPI_fc44bb5";

    public static String standardURLEncoder(String data, String key) {
        byte[] byteHMAC = null;
        String urlEncoder = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
            if ( byteHMAC != null ) {
                String oauth = encode(byteHMAC);
                if ( oauth != null ) {
                    urlEncoder = URLEncoder.encode(oauth, "utf8");
                }
            }
        } catch ( InvalidKeyException e1 ) {
            e1.printStackTrace();
        } catch ( Exception e2 ) {
            e2.printStackTrace();
        }
        return urlEncoder;
    }

    public static String encode(byte[] from) {
        StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for ( int i = 0; i < from.length; i++ ) {
            num = num % 8;
            while ( num < 8 ) {
                switch ( num ) {
                    case 0:
                        currentByte = (char) (from[i] & lead6byte);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & last6byte);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & last4byte);
                        currentByte = (char) (currentByte << 2);
                        if ( (i + 1) < from.length ) {
                            currentByte |= (from[i + 1] & lead2byte) >>> 6;
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & last2byte);
                        currentByte = (char) (currentByte << 4);
                        if ( (i + 1) < from.length ) {
                            currentByte |= (from[i + 1] & lead4byte) >>> 4;
                        }
                        break;
                }
                to.append(encodeTable[currentByte]);
                num += 6;
            }
        }
        if ( to.length() % 4 != 0 ) {
            for ( int i = 4 - to.length() % 4; i > 0; i-- ) {
                to.append("=");
            }
        }
        return to.toString();
    }


    /**
     * 获取到url
     *
     * @param areaId:城市ID
     * @param type：请求数据类型
     * @return 网络请求的url
     */
    public static String getUrl(String areaId, String type) {
        String url;
        Date currDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String date = format.format(currDate);
        String publicKey = "http://open.weather.com.cn/data/?" +
                "areaid=" + areaId + "&" +
                "type=" + type + "&" +
                "date=" + date + "&" +
                "appid=" + appid;

        String key = standardURLEncoder(publicKey, private_key);
        url = "http://open.weather.com.cn/data/?" +
                "areaid=" + areaId + "&" +
                "type=" + type + "&" +
                "date=" + date + "&" +
                "appid=" + appid_8 + "&" +
                "key=" + key;
        LogUtils.e(TAG,"气象数据平台URL:"+url);
        return url;
    }
}
