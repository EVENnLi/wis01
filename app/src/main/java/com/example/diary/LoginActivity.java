package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diary.Login.UserDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    UserDatabase dpHelper = new UserDatabase(this, "User.db",
            null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitView();
    }

    public void InitView() {
        Button btnRegister = findViewById(R.id.register);
        Button btnLogin = findViewById(R.id.login);
        Button btn_skip_login = findViewById(R.id.skip_login);
        EditText phoneNumber = findViewById(R.id.phoneNumber);
        EditText password = findViewById(R.id.password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);//跳转到注册界面
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            int count = 0, count1 = 0;//用来计数

            @Override
            public void onClick(View view) {
                SQLiteDatabase dp = dpHelper.getWritableDatabase();
                Map map = new HashMap();//储存手机号和密码，一一对应
                Cursor cursor = dp.query("User", new String[]{"phoneNumber", "password"},
                        null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        count++;//得到一共有几组
                        @SuppressLint("Range")
                        String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                        @SuppressLint("Range")
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        map.put(phoneNumber, password);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                Set set = map.keySet();

                for (Object key : set) {
                    count1++;
                    if (key.toString().equals(phoneNumber.getText().toString()) &&
                            map.get(key).toString().equals(password.getText().toString())) {//手机号和密码对的上的话
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);//跳转到主界面
                        startActivity(intent);
                        finish();
                        break;
                    } else if (((!key.toString().equals(phoneNumber.getText().toString()) ||
                            !map.get(key).toString().equals(password.getText().toString()))) &&
                            count1 == count && phoneNumber.getText().toString().length() != 0 &&
                            password.getText().toString().length() != 0) {//对应不上
                        Toast.makeText(LoginActivity.this, "手机号或者密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                if (phoneNumber.getText().toString().length() == 0 || password.getText().toString().length() == 0) {//判空
                    Toast.makeText(LoginActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_skip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}