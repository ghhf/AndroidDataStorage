package com.practice.happy.androiddatastorage.db.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.db.data.BookContract;


/**
 * Created by Happy on 2017/10/5.
 * 数据库辅助类
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bookStore.db";
    public static final int DATABASE_VERSION = 1;

    private Context mContext;

    //创建表
    private static final String SQL_CREATE_BOOKSTORE_TABLE = "CREATE TABLE " +
            BookContract.BookEntry.TABLE_NAME + "(" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BookContract.BookEntry.COLUMN_BOOK_NAME + " TEXT, " +
            BookContract.BookEntry.COLUMN_BOOK_AUTHOR + " TEXT, " +
            BookContract.BookEntry.COLUMN_BOOK_PRICE + " REAL, " +
            BookContract.BookEntry.COLUMN_BOOK_PAGES + " INTEGER" + ");";

    //删除表
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXITS"
            + BookContract.BookEntry.TABLE_NAME;

    /**
     * 构造函数四个参数：
     * 第一个参数：Context，上下文对象，对数据库进行操作
     * 第二个参数：数据库名，创建数据库时使用的名称
     * 第三个参数：允许在查询数据时返回一个自定义的Cursor，普通情况下传入null
     * 第四个参数：当前数据库版本号，可用于数据库升级操作
     */

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_BOOKSTORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
