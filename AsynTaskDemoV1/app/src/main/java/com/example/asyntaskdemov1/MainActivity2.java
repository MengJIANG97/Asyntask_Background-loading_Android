package com.example.asyntaskdemov1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService 的理解与使用
 */
class MyThread implements Runnable{
    @Override
    public void run() {
        Log.d("正在执行: ",Thread.currentThread().getName());
    }
}
public class MainActivity2 extends AppCompatActivity {
    ExecutorService service,service1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        service = Executors.newFixedThreadPool(10);
        //容纳10個线程任务的线程池

        /**
         * 异步的 Runnable 接口的实现
         */

        service.execute(() -> {
            Log.d("ddd", "execute");
            try {
                Thread.sleep(5000);
                Log.d("ddd", "execute5000");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            Runnable syncRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("Thread", Thread.currentThread().getName());
                }
            };
            executorService.execute(syncRunnable);
        }


        /*
        while (true){
            ThreadPoolExecutor pool = (ThreadPoolExecutor)service;
            Log.d("ddd 当前排队线程数："," "+pool.getQueue().size());

            Log.d("ddd 活动线程"," "+pool.getActiveCount());
        }
         */
        service1 = Executors.newFixedThreadPool(3);
        for (int i=0;i<10;i++){
            service1.execute(new MyThread());
        }

    }

    /**
     * ExecutorService 并不会马上关闭，
     * 而是不再接收新的任务，壹但所有的线程结束执行当前任务，
     * ExecutorServie 才会真的关闭。
     * 所有在调用 shutdown() 方法之前提交到 ExecutorService 的任务都会执行。
     *
     * 调用 shutdownNow() 方法。
     * 这個方法会尝试马上关闭所有正在执行的任务，
     * 并且跳过所有已经提交但是还没有运行的任务。
     * 但是对于正在执行的任务，是否能够成功关闭它是无法保证的，
     * 有可能他们真的被关闭掉了，也有可能它会壹直执行到任务结束。
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (service != null)
            service.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ddd", "ondestroy");
    }
}