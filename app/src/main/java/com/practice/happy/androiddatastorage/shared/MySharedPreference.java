package com.practice.happy.androiddatastorage.shared;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Happy on 2017/10/8.
 */

public class MySharedPreference {

    private Context context;

    public MySharedPreference(Context context) {
        this.context = context;
    }

    // 保存用户信息到 userInfo.xml中
    public boolean saveMessage(String name,String password){

        boolean flag = false;

        /**
        getSharedPreferences(),存储多个内容，会新建文件，存储在自定义的文件下
        getPreferences()，存储多个内容，后来的内容会覆盖之前的，存储在系统默认的文件下
         */
        SharedPreferences sharedPreferences = context.
                getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name",name);
        editor.putString("password",password);
        flag = editor.commit();

        return flag;
    }

    //从userInfo.xml中获取存储的数据
    public Map<String,String> getMessage(){
        Map<String,String> map = new HashMap<String,String>();
        SharedPreferences sharedPreferences = context.
                getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", null);
        String password = sharedPreferences.getString("password", null);
        map.put("name",name);
        map.put("password",password);
        return map;
    }
}
