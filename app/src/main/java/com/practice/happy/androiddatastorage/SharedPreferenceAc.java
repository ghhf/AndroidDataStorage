package com.practice.happy.androiddatastorage;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.shared.MySharedPreference;

import java.util.Map;

/**
 * SharedPreferences 存储键值对，数据持久化。通常用于存储应用程序的配置参数，
 *
 * 存储的数据会在应用被卸载后清除
 *
 * 模仿一个登陆界面
 */
public class SharedPreferenceAc extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etName, etPassword;
    private CheckBox cbRemember;
    private Button btnLogin;
    private MySharedPreference mSP;
    private boolean isSaveSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        initView();

        mSP = new MySharedPreference(this);
        // 取出存储的数据
        Map<String, String> map = mSP.getMessage();

        Log.e("TAG","sp存储的数据" + map.get("name") + map.get("password"));

        if (map.get("name") != null && map.get("name") != null && !isSaveSuccess) {
            etName.setText(map.get("name"));
            etPassword.setText(map.get("password"));
            cbRemember.setVisibility(View.GONE);
        }

    }

    private void initView() {
        etName = (TextInputEditText) findViewById(R.id.edit_name);
        etPassword = (TextInputEditText) findViewById(R.id.edit_password);
        cbRemember = (CheckBox) findViewById(R.id.checkbox_remember);
        btnLogin = (Button) findViewById(R.id.login_btn);
        btnLogin.setOnClickListener(SharedPreferenceAc.this);
        cbRemember.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SharedPreferenceAc.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SharedPreferenceAc.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(SharedPreferenceAc.this, "登陆成功", Toast.LENGTH_SHORT).show();

        //如果选中记住密码复选框，执行保存操作
        if (cbRemember.isChecked()) {
           isSaveSuccess = mSP.saveMessage(name,password);
            Log.e("TAG","复选框选中，保存密码----->>"  + isSaveSuccess);
        }


    }

}
