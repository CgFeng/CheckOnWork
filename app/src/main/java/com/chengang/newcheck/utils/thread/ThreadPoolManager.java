package com.chengang.newcheck.utils.thread;

import android.os.AsyncTask;
import android.os.Build.VERSION;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by urcha on 2016/4/19.
 */
public class ThreadPoolManager {
    private static ExecutorService threadPool = null;

    static {
        threadPool = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable runnable){
        threadPool.execute(runnable);
    }

    /**
     * 针对不同api的 asynctask处理
     * 3.0以后的asynctask被改为默认串行，使用自己的线程池实现并行
     */
    public static <Params, Progress, Result> void executeOnExecutor(AsyncTask<Params, Progress, Result> task, Params...
            params) {
        if (VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(threadPool, params);
        }
        else {
            task.execute(params);
        }
    }

    private static class Build {
    }
}
