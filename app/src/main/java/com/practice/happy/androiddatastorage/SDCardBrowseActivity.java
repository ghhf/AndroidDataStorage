package com.practice.happy.androiddatastorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.practice.happy.androiddatastorage.util.SDCardUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDCardBrowseActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvFile;

    private List<Map<String, Object>> totalList = new ArrayList<>();
    private MyAdapter mAdapter = null;
    private File currentFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_browse);

        initView();

        // 当前文件目录的列表
        currentFile = new File(SDCardUtil.getSDCardBaseDir());
        // 实现ListView的刷新
        reloadListView(currentFile);

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.sd_list_file);
        tvFile = (TextView) findViewById(R.id.sd_tv_path);

        if (!SDCardUtil.isSDCardMounted()){
            tvFile.setText("未检测到SD卡！！");
            return;
        }
        mAdapter = new MyAdapter(totalList);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle("");

                File[] files = currentFile.listFiles();
                // 获取点击的File对象
                File itemClickFile = files[position];
                if (itemClickFile.isHidden()) {
                    setTitle("该目录或文件为隐藏文件或目录");
                } else if (itemClickFile.isDirectory()) {
                    File[] itemFiles = itemClickFile.listFiles();
                    if (itemFiles.length == 0) {
                        setTitle("该目录为空目录");
                    }
                    // 重新刷新加载，加载子目录的文件信息
                    reloadListView(itemClickFile);

                } else if (itemClickFile.isFile()) {
                    String extension = getFileExtension(itemClickFile);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (extension.equals("jpg")
                            || extension.equals("gif")
                            || extension.equals("png")
                            || extension.equals("bmp")
                            || extension.equals("ipeg")) {
                        intent.setDataAndType(Uri.fromFile(itemClickFile), "image/");
                    } else if (extension.equals("mp3")
                            || extension.equals("mp4")
                            || extension.equals("mpg")
                            || extension.equals("3gp")
                            || extension.equals("mpeg")) {
                        intent.setDataAndType(Uri.fromFile(itemClickFile), "video/*");

                    } else if (extension.equals("au")
                            || extension.equals("mid")) {
                        intent.setDataAndType(Uri.fromFile(itemClickFile), "audio/*");
                    } else if (extension.equals("txt")
                            || extension.equals("xml")
                            || extension.equals("log")
                            || extension.equals("html")) {
                        intent.setDataAndType(Uri.fromFile(itemClickFile), "text/*");

                    }
                    startActivity(intent);
                }

            }


        });

    }

    public void clickImage(View view) {
        switch (view.getId()) {
            case R.id.sd_iv_back:
                setTitle("");
                boolean flag = currentFile.getAbsoluteFile().equals(SDCardUtil.getSDCardBaseDir());
                if (!flag) {
                    reloadListView(currentFile.getParentFile());
                }
                break;
            default:
                break;
        }
    }

    // 刷新ListView
    private void reloadListView(File currentFile) {
        this.currentFile = currentFile;

        totalList.clear();
        totalList.addAll(getFilePath(currentFile));
        mAdapter.notifyDataSetChanged();
        tvFile.setText(currentFile.getAbsolutePath());
    }

    private List<Map<String, Object>> getFilePath(File currentFile) {
        List<Map<String, Object>> list = new ArrayList<>();
        File[] files = currentFile.listFiles();
        for (File file : files) {
            String path = file.getAbsolutePath();
            Map<String, Object> map = new HashMap<>();
            map.put("filename", path + file.getName());
            if (file.isDirectory()) {
                // 目录
                map.put("iconId", android.R.drawable.ic_dialog_info);
            } else {
                map.put("iconId", android.R.drawable.ic_input_get);

            }
            list.add(map);
        }
        return list;
    }

    // 获取文件的后缀
    private String getFileExtension(File itemClickFile) {
        String files = itemClickFile.getAbsolutePath();
        int pos = files.lastIndexOf('.');
        if (pos != -1) {
            String extension = files.substring(pos + 1);
            return extension;
        }

        return null;
    }

    class MyAdapter extends BaseAdapter {

        private List<Map<String, Object>> list = null;

        public MyAdapter(List<Map<String, Object>> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mholder = null;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_sdcard, parent, false);
                convertView.setTag(mholder);

            }
            mholder = new ViewHolder(convertView);

            mholder.itemTVTitle.setText((String) list.get(position).get("filename"));
            mholder.itemIVIcon.setImageResource((Integer) list.get(position).get("iconId"));
            return convertView;
        }

        class ViewHolder {
            private TextView itemTVTitle;
            private ImageView itemIVIcon;

            ViewHolder(View view) {
                itemIVIcon = view.findViewById(R.id.iv_item_icon);
                itemTVTitle = view.findViewById(R.id.tv_item_title);
            }
        }
    }


}
