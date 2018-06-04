package com.practice.happy.androiddatastorage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.util.XMLUtil;

import java.io.IOException;

/**
 * Created by Happy on 2018/5/3.
 */

public class XMLAc extends AppCompatActivity {

    private TextInputEditText etNmae, etPwd;
    private TextView text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        etNmae = (TextInputEditText) findViewById(R.id.et_name_xml);
        etPwd = (TextInputEditText) findViewById(R.id.et_password_xml);
        text = (TextView) findViewById(R.id.tv_text_xml);


    }

    public void saveClick(View v) throws IOException {
        String name = etNmae.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        if (name.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.e("TAG", "name" + name + "----- password" + pwd);
        // 保存数据
        boolean flag = false;

        flag = XMLUtil.saveXmlByXmlSerializer(this, name, pwd);

        if (flag) {
            Toast.makeText(XMLAc.this, "保存数据成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(XMLAc.this, "保存数据失败", Toast.LENGTH_SHORT).show();

        }
    }

    public void parseClick(View v) throws Exception {
        text.setText(""); // 先把内容置为空

        String str = XMLUtil.parseXmlFile(this);
        text.setText("解析的xml数据为：" + str);
    }
}
