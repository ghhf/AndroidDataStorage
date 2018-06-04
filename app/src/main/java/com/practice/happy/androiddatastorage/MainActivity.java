package com.practice.happy.androiddatastorage;
/**
 * Android数据存储
 * 一、SharedPreferences，存储xml,以键值对的方式存储，例如登录时记录用户名，以及一些设置相关的，
 *      共享不同应用之间的数据，通常用于配置文件的存储，例如开机启动
 * 二、File，存储二进制文件
 * 三、SQLite，存储大型文件
 * 四、Socket，网络存储
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button spBtn,sqliteBtn,fileBtn,socketBtn,xmlBtn,sdcardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xmlBtn = (Button) findViewById(R.id.xml_btn);
        xmlBtn.setOnClickListener(new MyClick());

        spBtn = (Button) findViewById(R.id.sharedpreference_btn);
        spBtn.setOnClickListener(new MyClick());

        sqliteBtn = (Button) findViewById(R.id.sqlite_btn);
        sqliteBtn.setOnClickListener(new MyClick());

        fileBtn = (Button) findViewById(R.id.file_btn);
        fileBtn.setOnClickListener(new MyClick());

        socketBtn = (Button) findViewById(R.id.socket_btn);
        socketBtn.setOnClickListener(new MyClick());

        sdcardBtn = findViewById(R.id.sdcard_btn);
        sdcardBtn.setOnClickListener(new MyClick());

    }

    class MyClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.xml_btn:
                    Intent xmlIntent = new Intent(MainActivity.this,XMLAc.class);
                    startActivity(xmlIntent);
                    break;
                case R.id.sharedpreference_btn:
                    Intent spIntent = new Intent(MainActivity.this,SharedPreferenceAc.class);
                    startActivity(spIntent);
                    break;
                case R.id.sdcard_btn:
                    Intent sdIntent = new Intent(MainActivity.this,SDCardBrowseActivity.class);
                    startActivity(sdIntent);
                    break;
                case R.id.file_btn:
                    Intent fileIntent = new Intent(MainActivity.this,FileAc.class);
                    startActivity(fileIntent);
                    break;
                case R.id.sqlite_btn:
                    Intent sqliteIntent = new Intent(MainActivity.this,SQLiteAc.class);
                    startActivity(sqliteIntent);
                    break;
                case R.id.socket_btn:
                    Intent socketIntent = new Intent(MainActivity.this,SocketAc.class);
                    startActivity(socketIntent);
                    break;
            }
        }
    }
}
