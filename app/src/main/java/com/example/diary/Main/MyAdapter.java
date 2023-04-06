package com.example.diary.Main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.R;
import com.example.diary.ViewActivity01;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private View view;
    private Context context;
    private ArrayList<String> titleList, contentList, timeList;

    public MyAdapter(Context context, ArrayList<String> titleList, ArrayList<String> contentList, ArrayList<String> timeList) {
        this.context = context;
        this.titleList = titleList;
        this.contentList = contentList;
        this.timeList = timeList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView title,content,time;
        ImageView picture;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_view);
            content = itemView.findViewById(R.id.content_view);
            time = itemView.findViewById(R.id.time);
            picture = itemView.findViewById(R.id.picture);
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//图片点击事件
                    Intent intent = new Intent(itemView.getContext(), ViewActivity01.class);
                    //通过intent传递数据
                    intent.putExtra("title",title.getText().toString());
                    intent.putExtra("content",content.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.recyclerview,parent,
                false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyHolder holder, int position) {
        holder.title.setText(titleList.get(position));
        holder.content.setText(contentList.get(position));
        holder.time.setText(timeList.get(position));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
