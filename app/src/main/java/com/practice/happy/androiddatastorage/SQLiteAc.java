package com.practice.happy.androiddatastorage;

import android.content.Intent;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.db.InsertDataActivity;
import com.practice.happy.androiddatastorage.db.SearchDataActivity;
import com.practice.happy.androiddatastorage.db.bean.Book;
import com.practice.happy.androiddatastorage.db.BookAdapter;
import com.practice.happy.androiddatastorage.db.dao.BookDao;
import com.practice.happy.androiddatastorage.db.data.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SQLiteAc extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int SUCCESS = 0;
    private static final int FAILED = 1;
    private static final int EMPTY = 2;

    private TextView emptyTv;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private List<Book> list = new ArrayList<>();
    private BookDao bookDao;
    private BookAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            refreshLayout.setRefreshing(false);

            switch (msg.what) {

                case EMPTY:
                    recyclerView.setVisibility(View.GONE);
                    emptyTv.setVisibility(View.VISIBLE);
                    emptyTv.setText("当前内容为空，赶紧点击右上角 + 添加吧！！");
                    break;

                case SUCCESS:
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyTv.setVisibility(View.GONE);

                    // 为adapter设置数据
                    adapter.recyclerData(list);

                    recyclerView.setAdapter(adapter);

                    break;
                case FAILED:
                    emptyTv.setVisibility(View.VISIBLE);
                    emptyTv.setText("获取数据失败，尝试下拉重新加载......");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        initView();

    }


    @Override
    protected void onStart() {
        super.onStart();
        onLoadData();
    }

    private void initView() {

        emptyTv = findViewById(R.id.sql_empty);

        recyclerView = findViewById(R.id.sql_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SQLiteAc.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BookAdapter(SQLiteAc.this);

        refreshLayout = findViewById(R.id.sql_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sqlite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(InsertDataActivity.newIntent(this, null));
                return true;

            case R.id.query:
                Intent query = new Intent(this, SearchDataActivity.class);
                startActivity(query);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        onLoadData();
    }

    private void onLoadData() {

        bookDao = new BookDao(SQLiteAc.this);

        //查询数据库中的所有数据
        list = bookDao.queryAll();

        Message msg = new Message();
        if (list.isEmpty() || list.size() == 0) {
            msg.what = EMPTY;
        } else {
            msg.what = SUCCESS;
        }

        handler.sendMessage(msg);

        Log.e("TAG", "sql ------- size == " + list.size());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 接收传递过来的数据
        if (resultCode == InsertDataActivity.INSERT_SUCCESS) {
            String name = data.getStringExtra("name");
            String author = data.getStringExtra("author");
            String page = data.getStringExtra("page");
            String price = data.getStringExtra("price");

            Log.e("TAG", "返回SQLiteAc的数据" + name + ",---" + author + ",---" + page + ",---" + price);

            adapter.notifyDataSetChanged();

        }
    }
}
