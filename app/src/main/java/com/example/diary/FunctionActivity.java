package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.diary.Main.DiaryDatabase;
import com.example.diary.Main.MyAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class FunctionActivity extends AppCompatActivity {
    DiaryDatabase database = new DiaryDatabase(this, "Diary.db", null, 1);
    private RecyclerView recycler;
    private ArrayList<String> _title, _content, _time;
    private Date time;
    private SwipeRefreshLayout swipeRefreshLayout;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        recycler = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        InitData();
        MyAdapter myAdapter = new MyAdapter(this, _title, _content, _time);
        recycler.setAdapter(myAdapter);//recycler view显示

        //刷新，但是没用
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                InitData();
                                myAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    public void InitData() {//得到所有数据
        _title = new ArrayList<>();
        _content = new ArrayList<>();
        _time = new ArrayList<>();
        time = new Date();
        _title.clear();
        _content.clear();
        _title.clear();
        HashMap hashMap = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
        String format = simpleDateFormat.format(time);//时间
        SQLiteDatabase dp = database.getWritableDatabase();

        Cursor cursor = dp.query("Diary", new String[]{"title", "content"}, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range")
                String content = cursor.getString(cursor.getColumnIndex("content"));
                hashMap.put(title, content);
            } while (cursor.moveToNext());
            cursor.close();
            Set set = hashMap.keySet();
            for (Object key : set) {
                _title.add(key.toString());
                _content.add(hashMap.get(key).toString());
                _time.add(format.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//显示菜单
        getMenuInflater().inflate(R.menu.function_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//菜单选项
        switch (item.getItemId()) {
            case R.id.new_diary:
                Intent intent = new Intent(FunctionActivity.this, NewDiaryActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_diary:
                Intent intent2 = new Intent(FunctionActivity.this, DeleteDiaryActivity.class);
                startActivity(intent2);
                break;
            case R.id.find_diary:
                Intent intent1 = new Intent(FunctionActivity.this, FindDiaryActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
        return true;
    }
}