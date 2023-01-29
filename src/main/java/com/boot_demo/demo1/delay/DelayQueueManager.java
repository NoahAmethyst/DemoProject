package com.boot_demo.demo1.delay;


import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Slf4j
public abstract class DelayQueueManager<T> implements CommandLineRunner {
    public DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    /**
     * 加入到延时队列中
     *
     * @param
     */
    abstract void put(T element, long expire);

    /**
     * 取消延时任务
     *
     * @param task
     * @return
     */
    public boolean remove(DelayTask task) {
        log.info("cancel delay task：{}", task);
        return delayQueue.remove(task);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("initialize the delay queue.");
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
    }

    /**
     * 延时任务执行线程
     */
    private void excuteThread() {
        while (true) {
            try {
                DelayTask<T> task = delayQueue.take();
                processTask(task);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * 内部执行延时任务
     *
     * @param task
     */
    abstract void processTask(DelayTask<T> task);
}
