package cn.xian.springframework.thread.pool;

import cn.xian.log.Log;

import java.util.concurrent.*;

/**
 * @author lishixian
 * @date 2020/7/27 下午1:59
 */
public class MyDefaultThreadPool {

    public static void main(String[] args) {

        ThreadFactory namedThreadFactory = new MyDefaultThreadFactory();
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();

        ExecutorService executorService = new ThreadPoolExecutor(8, 16,
                60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50),
                namedThreadFactory, discardOldestPolicy);


        executorService.submit(() -> {
            Log.info("哈哈哈");
        });

        executorService.submit(() -> {
            Log.info("哈哈哈2");
        });


    }


}
