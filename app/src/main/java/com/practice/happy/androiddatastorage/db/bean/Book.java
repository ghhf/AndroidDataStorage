package com.practice.happy.androiddatastorage.db.bean;


import com.practice.happy.androiddatastorage.db.data.BookContract;

/**
 * Created by Happy on 2017/10/5.
 */

public class Book{

    private long _id;
    private String bookName;
    private String bookAuthor;
    private int pages;
    private float bookPrice;
    private boolean isChoose;

    public Book(){

    }
    public Book(String bookName,String bookAuthor, int pages, float bookPrice){
        super();

        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.pages = pages;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    @Override
    public String toString() {
        return "书名:" + bookName + ", 作者：" + bookAuthor + ",价格:" + bookPrice + ",页数" + pages;
    }
}
