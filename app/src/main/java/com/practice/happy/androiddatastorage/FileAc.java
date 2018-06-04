package com.practice.happy.androiddatastorage;

import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件存储 通过，如音乐、图片或视频
 * 内部存储：存储在内部存储空间，该文件是该应用程序私有的，其他应用程序如果想操作该文件，需要设置权限，
 * 当应用程序被卸载时，该文件也被删除。
 * 使用Context提供的openFileOutput()来打开应用程序的输出流，将数据保存到指定文件中
 * openFileInput()来打开应用程序对应的输入流，从文件中读取数据
 * <p>
 * 外部存储：存储在一些外围设备上，该文件可以被其他应用程序共享，该文件可被浏览，修改和删除，
 * 由于外围设备可能被删除，丢失或处于其他状态，
 * 因此使用之前必须用 Environment.getExternalStorageState() 该方法来确认外围设备是否可用
 */
public class FileAc extends AppCompatActivity {

    private TextInputEditText edit, sdEdit;
    private Button saveBtn, readBtn, sdSaveBtn, sdReadBtn;
    private TextView textView, sdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        edit = (TextInputEditText) findViewById(R.id.edit);
        saveBtn = (Button) findViewById(R.id.save);
        readBtn = (Button) findViewById(R.id.read);
        textView = (TextView) findViewById(R.id.text_view);

        sdEdit = (TextInputEditText) findViewById(R.id.edit_sd);
        sdSaveBtn = (Button) findViewById(R.id.save_sd);
        sdReadBtn = (Button) findViewById(R.id.read_sd);
        sdTextView = (TextView) findViewById(R.id.text_view_sd);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(getFilesDir(), "data.txt");

//                edit.setError("内容不能为空");
                String data = edit.getText().toString().trim();
                edit.setText(""); // 获取数据后，清空输入框
                if (data.isEmpty() || data == null) {
                    Toast.makeText(FileAc.this, "保存的内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
//                    FileOutputStream fos;
                    try {
//                        fos = openFileOutput("data.txt", Context.MODE_APPEND);//以追加的模式保存内容
//                        fos.write(data.getBytes());
//                        fos.close();

                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(data + "#" + data);
                        writer.close();

                        Toast.makeText(FileAc.this, "保存数据成功", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "File存储内部存储，保存数据成功 =======>>" + data);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("TAG", "File存储内部存储，存储数据失败");
                    }
                }

            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            File file = new File(getFilesDir(), "data.txt");

            @Override
            public void onClick(View view) {
                String data = "";
                FileInputStream fis;
                try {
//                    fis = openFileInput("data.txt");
//                    byte[] buffer = new byte[fis.available()];
//                    fis.read(buffer);
//                    data = new String(buffer);

                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    while (reader.read() != -1){
                        data = reader.readLine();
                        Log.e("TAG", "data=========>>>" + data );
                    }
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                textView.setText("保存的数据是：" + data);
            }
        });

        sdSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = Environment.getExternalStorageState();
                String data = sdEdit.getText().toString().trim();

                // 判断SDCard是否被装载
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File extraFile = new File(Environment.getExternalStorageDirectory(), "sdData.txt");

                    Log.e("TAG", "File 外部存储，存储路径" +
                            Environment.getExternalStorageDirectory() + "sdData.txt");

//                    FileOutputStream fos;
                    try {
                        // 字节流的方式存储
//                        fos = new FileOutputStream(extraFile);

//                        fos.write(data.getBytes());
//                        fos.close();
//                        Toast.makeText(FileAc.this,"保存数据成功",Toast.LENGTH_SHORT).show();

                        // 内部存储
//                        File file = new File(Environment.getDataDirectory(), "data.txt");
//                        FileWriter writer = new FileWriter(file);
//                        writer.write(data);
//                        writer.close();

                        BufferedWriter writer = new BufferedWriter(new FileWriter(extraFile));
                        writer.write(data);
                        writer.close();

                        Toast.makeText(FileAc.this, "保存数据成功", Toast.LENGTH_SHORT).show();

                        Log.e("TAG", "File存储内部存储，存储数据成功");

                    } catch (IOException e) {

                        e.printStackTrace();
                        Log.e("TAG", "File存储内部存储，存储数据失败");
                    }
                }
            }
        });

        sdReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = Environment.getExternalStorageState();
                String data = "";

                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File file = new File(Environment.getExternalStorageDirectory(), "sdData.txt");
                    FileInputStream fis;
                    try {
//                        fis = new FileInputStream(file);
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//                        data = reader.readLine();
//                        fis.close();

                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        while (reader.read() != -1){
                           data = reader.readLine();
                        }
                        reader.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sdTextView.setText("保存的数据：" + data);
                }
            }
        });
    }
}
