package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diary.Main.DiaryDatabase;

import java.util.HashMap;
import java.util.Set;

public class FindDiaryActivity extends AppCompatActivity {
    DiaryDatabase database = new DiaryDatabase(this, "Diary.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_diary);
        EditText find_diary_by_title = findViewById(R.id.find_diary_by_title);
        Button btn_finish_find_diary = findViewById(R.id.finish_find_diary);


        btn_finish_find_diary.setOnClickListener(new View.OnClickListener() {
            int count = 0, count1 = 0;
            @Override
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                SQLiteDatabase dp = database.getWritableDatabase();
                Cursor cursor = dp.query("Diary", new String[]{"title", "content"}, null, null,
                        null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        count++;
                        @SuppressLint("Range")
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        @SuppressLint("Range")
                        String content = cursor.getString(cursor.getColumnIndex("content"));
                        hashMap.put(title, content);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                Set set = hashMap.keySet();
                for (Object key : set) {
                    count1++;
                    if (key.toString().equals(find_diary_by_title.getText().toString())) {
                        Toast.makeText(FindDiaryActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (!key.toString().equals(find_diary_by_title.getText().toString()) && count1 == count) {
                        Toast.makeText(FindDiaryActivity.this, "查找失败，此日记不存在", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });

    }
}