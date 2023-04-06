package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diary.Login.UserDatabase;

public class RegisterActivity extends AppCompatActivity {
    UserDatabase dpHelper = new UserDatabase(this, "User.db",
            null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitView();
    }

    public void InitView() {
        Button btn_finish_register = findViewById(R.id.finish_register);
        EditText register_phoneNumber = findViewById(R.id.register_phoneNumber);
        EditText register_password = findViewById(R.id.register_password);

        btn_finish_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase dp = dpHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("phoneNumber", register_phoneNumber.getText().toString());//获取输入的手机号
                contentValues.put("password", register_password.getText().toString());//获取输入的密码
                if (register_phoneNumber.getText().toString().length() != 11) {//手机号长度为11
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else if (register_password.getText().toString().length() < 6) {//密码长度要大于6
                    Toast.makeText(RegisterActivity.this, "密码至少为6个字符", Toast.LENGTH_SHORT).show();
                }
                if (register_phoneNumber.getText().toString().length() == 11 && register_password.getText().toString().length() >= 6) {
                    dp.insert("User", null, contentValues);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}