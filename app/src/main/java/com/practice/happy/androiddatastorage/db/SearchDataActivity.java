package com.practice.happy.androiddatastorage.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.R;
import com.practice.happy.androiddatastorage.db.bean.Book;
import com.practice.happy.androiddatastorage.db.dao.BookDao;

import java.util.ArrayList;
import java.util.List;

public class SearchDataActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView rvSearchResult;
    protected BookAdapter adapter;

    private List<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);

        initView();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_GO) {
                    //TODO
                    String s = etSearch.getText().toString().trim();
                    if (s!= null && s.length()!=0){
                        bookList = searchData(s);
                        if(bookList.size() == 0){
                            Toast.makeText(SearchDataActivity.this,"查询的数据不存在",Toast.LENGTH_SHORT).show();

                        }else{
                            adapter.recyclerData(bookList);
                            rvSearchResult.setAdapter(adapter);
                        }

//                        for (int i=0; i<bookList.size(); i++){
//                            Log.e("SQL","查询操作---Search bookList " + bookList.get(i));
//
//                        }
//                        Log.e("SQL","查询操作---Search  " + s);
                    }else {
                        Toast.makeText(SearchDataActivity.this,"查询内容不能为空",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private List<Book> searchData(String... name) {
        BookDao bookDao = new BookDao(this);

        String[] selection = name;

        bookList = bookDao.queryByInput(selection);
        return bookList;
    }

    private void initView() {
        etSearch = findViewById(R.id.search_et);
        rvSearchResult = findViewById(R.id.search_result);
        adapter = new BookAdapter(this);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
