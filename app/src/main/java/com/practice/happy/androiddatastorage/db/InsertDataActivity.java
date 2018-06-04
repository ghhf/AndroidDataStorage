package com.practice.happy.androiddatastorage.db;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.R;
import com.practice.happy.androiddatastorage.SQLiteAc;
import com.practice.happy.androiddatastorage.db.bean.Book;
import com.practice.happy.androiddatastorage.db.dao.BookDao;


public class InsertDataActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int INSERT_SUCCESS = 0;
    private TextInputEditText etName, etAuthor, etPrice, etPages;
    private Button btnAdd;
    private BookDao bookDao;
    private Book book = new Book();
    private static boolean flag = false;  // 判断是否携带参数

    public static Intent newIntent(Context context, Book sbook){

        Intent intent = new Intent(context,InsertDataActivity.class);

        if (sbook != null){
            flag = true;

            intent.putExtra("name",sbook.getBookName());
            intent.putExtra("author",sbook.getBookAuthor());
            intent.putExtra("pages",sbook.getPages());
            intent.putExtra("price",sbook.getBookPrice());
        }else {
            flag = false;
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_insert_page);

        bookDao = new BookDao(this);

        etName = (TextInputEditText) findViewById(R.id.insert_name);
        etAuthor = (TextInputEditText) findViewById(R.id.insert_author);
        etPages = (TextInputEditText) findViewById(R.id.insert_pages);
        etPrice = (TextInputEditText) findViewById(R.id.insert_price);
        btnAdd = (Button) findViewById(R.id.insert_save);


        btnAdd.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (flag){
            btnAdd.setText("更新");

            String name = getIntent().getStringExtra("name");
            String author = getIntent().getStringExtra("author");
            int pages = getIntent().getIntExtra("pages",0);
            float price =getIntent().getFloatExtra("price",0.0f);

            Log.e("SQL","传递过来的数据------->>>" + name + author + pages + price);
            // 先把传递过来的数据设置到界面上
            etName.setText(name);
            etAuthor.setText(author);
            etPages.setText(pages + "");
            etPrice.setText(price + "");

        }else {
            btnAdd.setText("添加");
        }
    }

    @Override
    public void onClick(View view) {
        if (flag){
            btnAdd.setText("更新");
            updateData(book);
        }else {
            btnAdd.setText("添加");
            addData();
        }

        finish();
    }

    public void updateData(Book book){

        String name = getIntent().getStringExtra("name");

        // 获取用户修改后的数据
        book.setBookName(etName.getText().toString().trim());
        book.setBookAuthor(etAuthor.getText().toString().trim());
        book.setBookPrice(Float.parseFloat(etPrice.getText().toString().trim()));
        book.setPages(Integer.parseInt(etPages.getText().toString().trim()));

        Log.e("SQL","修改的数据------->>>" +
                etName.getText().toString().trim() +
                etAuthor.getText().toString().trim() +
                etPrice.getText().toString().trim() +
                etPages.getText().toString().trim());

        // 传递修改后的数据
        int updateResult = bookDao.update(book,name);

        if (updateResult == -1){
            Toast.makeText(this,"更新失败" ,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();

        }

    }

    public void addData(){
        String bookName = etName.getText().toString().trim();
        String bookAuthor = etAuthor.getText().toString().trim();
        String bookPage = etPages.getText().toString().trim();
        String bookPrice = etPrice.getText().toString().trim();

        if (TextUtils.isEmpty(bookName) || TextUtils.isEmpty(bookAuthor)
                || TextUtils.isEmpty(bookPage) || TextUtils.isEmpty(bookPrice)) {

            Toast.makeText(InsertDataActivity.this, "内容输入不完整,无法添加数据",
                    Toast.LENGTH_SHORT).show();
        } else {

//                Book book = new Book(bookName, bookAuthor,
//                        bookPage.equals("") ? 0: Integer.parseInt(bookPage),
//                        bookPrice.equals("") ? 0 :Float.parseFloat(bookPrice));
            Book book = new Book();
            book.setBookName(bookName);
            book.setBookAuthor(bookAuthor);
            book.setBookPrice(bookPrice.equals("") ? 0 : Float.parseFloat(bookPrice));
            book.setPages(bookPage.equals("") ? 0 : Integer.parseInt(bookPage));

            bookDao.insert(book); // 插入数据到数据库中

            if (book.getId() == -1) {
                // 插入数据失败
                Toast.makeText(InsertDataActivity.this, "添加图书信息失败！", Toast.LENGTH_SHORT).show();
            }
            Log.e("SQL", "插入的Id  set = " + book.getId());

            Toast.makeText(InsertDataActivity.this, "成功添加一本图书信息！", Toast.LENGTH_SHORT).show();

            // 点击保存后，返回， 把信息传回去
            Intent intent = new Intent(InsertDataActivity.this, SQLiteAc.class);
            intent.putExtra("name", bookName);
            intent.putExtra("author", bookAuthor);
            intent.putExtra("page", bookPage);
            intent.putExtra("price", bookPrice);
            setResult(INSERT_SUCCESS, intent);


        }
    }
}
