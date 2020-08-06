package cn.xian.springframework.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author lishixian
 * @date 2020/7/27 下午1:59
 */
@Slf4j
public class MyDefaultThreadPool {

    public static void main(String[] args) {

        ThreadFactory namedThreadFactory = new MyDefaultThreadFactory();
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();

        ExecutorService executorService = new ThreadPoolExecutor(8, 16,
                60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50),
                namedThreadFactory, discardOldestPolicy);


        executorService.submit(() -> {
            log.info("哈哈哈");
        });

        executorService.submit(() -> {
            log.info("哈哈哈2");
        });


    }


}
