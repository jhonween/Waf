package com.willard.waf.network;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: wyouflf
 * Date: 14-5-16
 * Time: 上午11:25
 */
public class PriorityExecutor implements Executor {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 256;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "PriorityExecutor #" + mCount.getAndIncrement());
        }
    };

    private final BlockingQueue<Runnable> mPoolWorkQueue = new LinkedBlockingQueue<Runnable>();
    private final ThreadPoolExecutor mThreadPoolExecutor;

    public PriorityExecutor() {
        this(CORE_POOL_SIZE);
    }

    /**
    ThreadPoolExecutor的完整构造方法的签名是：
    ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, 
    BlockingQueue workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) .
	corePoolSize - 池中所保存的线程数，包括空闲线程。
	maximumPoolSize - 池中允许的最大线程数。
	keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
	unit - keepAliveTime 参数的时间单位。
	workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。
	threadFactory - 执行程序创建新线程时使用的工厂。
	handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。
     * @param poolSize
     */
    public PriorityExecutor(int poolSize) {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                poolSize,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                mPoolWorkQueue,
                sThreadFactory);
    }

    public int getPoolSize() {
        return mThreadPoolExecutor.getCorePoolSize();
    }

    public void setPoolSize(int poolSize) {
        if (poolSize > 0) {
            mThreadPoolExecutor.setCorePoolSize(poolSize);
        }
    }

    public boolean isBusy() {
        return mThreadPoolExecutor.getActiveCount() >= mThreadPoolExecutor.getCorePoolSize();
    }

    @Override
    public void execute(final Runnable r) {
        mThreadPoolExecutor.execute(r);
    }
}
