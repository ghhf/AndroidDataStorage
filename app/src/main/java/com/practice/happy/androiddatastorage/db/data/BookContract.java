package com.practice.happy.androiddatastorage.db.data;

import android.net.Uri;
import android.net.wifi.aware.PublishConfig;
import android.provider.BaseColumns;

/**
 * Created by Happy on 2017/10/5.
 * 存储数据库的结构
 */

public final class BookContract {
    private BookContract(){}

    public static final String CONTENT_ZUTHORITY ="com.practice.happy.androiddatastorage.bookStore";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_ZUTHORITY);

    public static final String PATH_BOOKSTORE = "bookStore";

    // 内部类BookEntry，为数据库中表的每一列定义为常量
    public static final class BookEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_BOOKSTORE);
        public static final String TABLE_NAME = "book";
        public static final String _ID = BaseColumns._ID ;
        public static final String COLUMN_BOOK_AUTHOR = "author";
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_PAGES = "pages";
    }

}
