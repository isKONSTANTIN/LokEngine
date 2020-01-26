package ru.lokincompany.lokengine.tools.executorservices;

import java.util.concurrent.ThreadFactory;

class LongTasksThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {

        Thread thread = new Thread(runnable);
        thread.setName("LokEngine-Long-Task-Thread");

        return thread;
    }

}
