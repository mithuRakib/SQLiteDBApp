package com.example.practiceproject.InternetConnection;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static final Object LOCK = new Object();
    private static AppExecutor appExecutorInstance;

    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    public AppExecutor(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutor getInstance() {
        if (appExecutorInstance == null) {
            synchronized (LOCK) {
                appExecutorInstance = new AppExecutor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return appExecutorInstance;
    }

    public Executor getDiskIO() { return diskIO; }
    public Executor getMainThread() { return mainThread; }
    public Executor getNetworkIO() { return networkIO; }

    private static class MainThreadExecutor implements Executor {
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }
}
