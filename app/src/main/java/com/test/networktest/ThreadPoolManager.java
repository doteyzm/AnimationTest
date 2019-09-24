package com.test.networktest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类
 * 创建队列
 * 创建线程
 */
public class ThreadPoolManager {
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 6, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        addTask(r);
                    }
                });
        mThreadPoolExecutor.execute(coreThread);
    }

    //将网络请求任务添加到队列中
    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //创建核心线程，将队列中的请求拿出来，交给线程池处理
    public Runnable coreThread = new Runnable() {
        Runnable runnable = null;

        @Override
        public void run() {
            while (true) {
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runnable);

            }
        }
    };
}
