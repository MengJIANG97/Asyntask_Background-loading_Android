package com.example.asyntaskdemov1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    MyTask myTask;
    Button button,cancel;
    // 加载、取消按钮
    TextView text;
    // 更新的UI组件
    ProgressBar progressBar;
    // 进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this,MainActivity2.class);
            startActivity(intent);
        });
        button = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        myTask = new MyTask();

        button.setOnClickListener(v -> myTask.execute());
        cancel.setOnClickListener(v -> myTask.cancel(true));
    }

    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            text.setText("加载中");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                int count = 0;
                int length = 1;
                while (count<99){
                    count+=length;
                    publishProgress(count);
                    Thread.sleep(50);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            text.setText("loading..."+values[0]+ "%");
        }

        @Override
        protected void onPostExecute(String s) {
            text.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {
            text.setText("已取消");
            progressBar.setProgress(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
    }
}