package ru.lokincompany.lokengine.tools.executorservices;

import ru.lokincompany.lokengine.tools.Logger;

import java.util.concurrent.*;

public class EngineExecutors {
    public static final ExecutorService fastTasksExecutor;
    public static final ExecutorService longTasksExecutor;

    static {
        fastTasksExecutor = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, (thread, throwable) -> {
            Logger.warning("Error in fast task!", "LokEngine_Executors");
            Logger.underMessage(Logger.stackTraceToString(throwable.getStackTrace()));
        }, true);

        longTasksExecutor = new ThreadPoolExecutor(0, 2147483647, 5L, TimeUnit.SECONDS, new SynchronousQueue());
    }

}
