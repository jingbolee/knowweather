package com.lijingbo.knowweather;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * @FileName: com.lijingbo.knowweather.ApplicationTest.java
 * @Author: Li Jingbo
 * @Date: 2016-04-05 14:33
 * @Version V1.0 <描述当前版本功能>
 */
public class ApplicationTest extends ApplicationTestCase< Application > {
    private static final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }

    public void test() throws Exception {
        int input1 = 1;
        int input2 = 2;
        assertEquals(input1, input2);
    }
}
