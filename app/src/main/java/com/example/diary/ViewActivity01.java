package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diary.Main.DiaryDatabase;

import java.util.HashMap;
import java.util.Set;

public class ViewActivity01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DiaryDatabase database = new DiaryDatabase(this, "Diary.db", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view01);
        EditText view__title = findViewById(R.id.view__title);
        EditText view__content = findViewById(R.id.view__content);
        Button btn_finish_view_edit = findViewById(R.id.finish_view__edit);

        Intent intent = getIntent();
        String _title = intent.getStringExtra("title");
        String _content = intent.getStringExtra("content");
        view__title.setText(_title);
        view__content.setText(_content);

        btn_finish_view_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                SQLiteDatabase dp = database.getWritableDatabase();
                Cursor cursor = dp.query("Diary", new String[]{"title", "content"}, null, null,
                        null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        //count++;
                        @SuppressLint("Range")
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        @SuppressLint("Range")
                        String content = cursor.getString(cursor.getColumnIndex("content"));
                        hashMap.put(title, content);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                Intent intent = getIntent();
                Set set = hashMap.keySet();
                for (Object key : set) {
                    if (key.toString().equals(intent.getStringExtra("title"))) {//重新加载标题和内容
                        ContentValues values = new ContentValues();
                        values.put("title", view__title.getText().toString());
                        dp.update("Diary", values, "title = ?",
                                new String[]{_title.toString()});
                        values.clear();
                        values.put("content",view__content.getText().toString());
                        dp.update("Diary",values,"content = ?",new String[]{_content.toString()});
                        Toast.makeText(ViewActivity01.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}