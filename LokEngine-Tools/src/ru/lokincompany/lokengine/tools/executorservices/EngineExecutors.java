package ru.lokincompany.lokengine.tools.executorservices;

import ru.lokincompany.lokengine.tools.Logger;

import java.util.concurrent.*;

public class EngineExecutors {
    public static final ExecutorService fastTasksExecutor;
    public static final ExecutorService longTasksExecutor;

    static LongTasksThreadFactory longTasksThreadFactory;

    static {
        longTasksThreadFactory = new LongTasksThreadFactory();

        fastTasksExecutor = Executors.newWorkStealingPool();
        longTasksExecutor = new ThreadPoolExecutor(0, 2147483647, 5L, TimeUnit.SECONDS, new SynchronousQueue(), longTasksThreadFactory);
    }

}
