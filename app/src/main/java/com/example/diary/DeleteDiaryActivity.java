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

public class DeleteDiaryActivity extends AppCompatActivity {
    DiaryDatabase database = new DiaryDatabase(this, "Diary.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_diary);
        EditText delete_diary_by_title = findViewById(R.id.delete_diary_by_title);
        Button btn_finish_delete_diary = findViewById(R.id.finish_delete_diary);
        btn_finish_delete_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0, count1 = 0;
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
                    Set set = hashMap.keySet();
                    for (Object key : set) {
                        count1++;
                        if (key.toString().equals(delete_diary_by_title.getText().toString())) {//标题对得上
                            dp.delete("Diary","title = ?",new String[]{delete_diary_by_title.getText().toString()});
                            Toast.makeText(DeleteDiaryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        } else if (!key.toString().equals(delete_diary_by_title.getText().toString()) && count1 == count) {//找不到日记
                            Toast.makeText(DeleteDiaryActivity.this, "删除失败，此日记不存在", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                }
            }
        });
    }
}