package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diary.Main.DiaryDatabase;

public class NewDiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary);
        EditText title = findViewById(R.id.title);
        EditText content = findViewById(R.id.content);
        Button btn_finish_edit = findViewById(R.id.finish_edit);

        DiaryDatabase database = new DiaryDatabase(this, "Diary.db", null, 1);
        btn_finish_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase dp = database.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", title.getText().toString());
                contentValues.put("content", content.getText().toString());
                dp.insert("Diary", null, contentValues);
                Toast.makeText(NewDiaryActivity.this, "成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}