package com.practice.happy.androiddatastorage.db;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.happy.androiddatastorage.R;
import com.practice.happy.androiddatastorage.db.bean.Book;
import com.practice.happy.androiddatastorage.db.dao.BookDao;
import com.practice.happy.androiddatastorage.db.data.BookContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Happy on 2017/10/5.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    private Context context;
    List<Book> bookList;
    private BookDao bookDao;

    public BookAdapter(Context context){
        super();
        this.context = context;
        bookList = new ArrayList<>();
        bookDao = new BookDao(context);
    }

    public void recyclerData(List<Book> list){
        this.bookList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sqlite_book,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final long id = bookList.get(position).getId();
        final String name = bookList.get(position).getBookName();
        final String author = bookList.get(position).getBookAuthor();
        final int page = bookList.get(position).getPages();
        final float price = bookList.get(position).getBookPrice();

        holder.tvName.setText(name);
        holder.tvAuthor.setText("作者:"+ author);
        holder.tvPages.setText("总页数" + page);
        holder.tvPrice.setText(" ￥：" + price);

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG","执行删除操作 ===== " + id );
                new AlertDialog.Builder(context).setTitle("确认删除" + name + "?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("TAG","取消删除" );

                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int delResult = bookDao.delete(name);
                        if (delResult == -1){
                            Log.e("TAG","删除失败" );
                        }

                        Log.e("TAG","删除的数据成功 ===== " + id + name );
                        Toast.makeText(context,"删除成功" + name,Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();  // 通知刷新界面

                    }
                }).show();

            }
        });

        holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               context.startActivity(InsertDataActivity.newIntent(context, bookList.get(position)));
            }
        });
        Log.e("TAG","ADAPTER" + bookList.get(position).getBookName() + bookList.get(position).getId());

    }


    @Override
    public int getItemCount() {
        return bookList.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvAuthor;
        TextView tvPrice;
        TextView tvPages;
        TextView tvDelete;
        TextView tvUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvPages = itemView.findViewById(R.id.tv_pages);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvUpdate = itemView.findViewById(R.id.tv_update);

        }
    }

}
