package com.practice.happy.androiddatastorage.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.practice.happy.androiddatastorage.db.data.BookContract;
import com.practice.happy.androiddatastorage.db.bean.Book;
import com.practice.happy.androiddatastorage.db.data.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Happy on 2017/10/6.
 * 数据库操作逻辑类
 */

public class BookDao {

    private MySQLiteOpenHelper myHelper;

    public BookDao(Context context) {
        //创建Dao时，实例化 MySQLiteOpenHelper
        myHelper = new MySQLiteOpenHelper(context);
    }

    //插入数据
    public boolean insert(Book book) {
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        Log.e("SQL --- 插入的数据：author=", book.getBookAuthor() + "-----name=" + book.getBookName()
                + "----price=" + book.getBookPrice() + "------pages=" + book.getPages());

        values.put(BookContract.BookEntry.COLUMN_BOOK_NAME, book.getBookName());
        values.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, book.getBookPrice());
        values.put(BookContract.BookEntry.COLUMN_BOOK_PAGES, book.getPages());
        values.put(BookContract.BookEntry.COLUMN_BOOK_AUTHOR, book.getBookAuthor());

        /**
         insert(String table, String nullColumnHack, ContentValues values)
         参数：String table 要插入数据的表的名称
         String nullColumnHack 我们知道数据库中除主键外其他的值可以为null，
         此参数表述如果你没有为表中的某一列数据提供数据，系统默认设置为该参数的值，
         此处传入null表示系统不会对那些没有提供数据的列进行填充
         ContentValues values 要插入的数据，是一个数据集

         返回值 long 最新插入的行的ID，如果发生错误，返回-1
         */

        long new_id = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);
        if (new_id == -1) {
            Log.e("SQL", "插入数据失败");
            return false;
        }
        book.setId(new_id);

        Log.e("SQL", "插入数据Id" + book.getId());
        Log.e("SQL", "插入数据成功" + book.getId() + book.getBookName() + book.getBookAuthor()
                + book.getPages() + book.getBookPrice());


        db.close();
        return true;
    }

    // 删除数据
    public int delete(String name) {
        Log.e("SQL", "删除数据Id -- " + name);

        SQLiteDatabase db = myHelper.getWritableDatabase();
        //  delete（表名，where语句，要查的字段）
        int count = db.delete(BookContract.BookEntry.TABLE_NAME, BookContract.BookEntry.COLUMN_BOOK_NAME + " = ?" , new String[]{name});
//        db.execSQL("DELETE FROM " + BookContract.BookEntry.TABLE_NAME +" WHERE id = " + id + ";");
        Log.e("SQL",BookContract.BookEntry.TABLE_NAME + BookContract.BookEntry.COLUMN_BOOK_NAME + " = ?" + new String[]{name});
        db.close();
        return count;
    }

    // 修改数据
    // UPDATE 表名 book SET name = 123 WHERE _ID == 2;
    public int update(Book book,String name) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_BOOK_NAME, book.getBookName());
        values.put(BookContract.BookEntry.COLUMN_BOOK_AUTHOR, book.getBookAuthor());
        values.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, book.getBookPrice());
        values.put(BookContract.BookEntry.COLUMN_BOOK_PAGES, book.getPages());
        int count = db.update(BookContract.BookEntry.TABLE_NAME, values,
                BookContract.BookEntry.COLUMN_BOOK_NAME + " = ?",
                new String[]{name});

        Log.e("SQL", "update" + book.getId() + book.getBookName() + book.getBookAuthor() + book.getBookPrice() + book.getPages());
        db.close();
        return count;
    }

    // 查询数据
    public List<Book> queryAll() {
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.query(BookContract.BookEntry.TABLE_NAME, null, null, null, null, null, null);
        Log.e("SQL", "数据库的数据总数为" + cursor.getCount());

        List<Book> list = new ArrayList<>();
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Book book = new Book();
                book.setBookName(cursor.getString(1));
                book.setBookAuthor(cursor.getString(2));
                book.setBookPrice(cursor.getFloat(3));
                book.setPages(cursor.getInt(4));

                list.add(book);
            }
        }

        cursor.close();
        db.close();
        return list;
    }

    //根据输入内容查找数据
    public List<Book> queryByInput(String[] queryStr) {

        List<Book> books = new ArrayList<>();

        SQLiteDatabase db = myHelper.getReadableDatabase();

        //select name,author from tables where pages= 100
        // 根据查询条件获取具体的列
        String[] projection = new String[]{
                BookContract.BookEntry.COLUMN_BOOK_NAME,
                BookContract.BookEntry.COLUMN_BOOK_AUTHOR,
                BookContract.BookEntry.COLUMN_BOOK_PAGES,
                BookContract.BookEntry.COLUMN_BOOK_PRICE
        };
        String selection = BookContract.BookEntry.COLUMN_BOOK_NAME + "=?";
        String[] selectionArgs = queryStr;

        Cursor cursor = db.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        try {

            while (cursor.moveToNext()) {
                Book book = new Book();
                book.setBookName(cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME)));
                book.setBookAuthor(cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME)));
                book.setBookPrice(cursor.getFloat(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE)));
                book.setPages(cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PAGES)));

                books.add(book);
            }
        } finally {
            cursor.close();
        }
        return books;

    }
}
