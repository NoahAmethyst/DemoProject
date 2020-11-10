package com.boot_demo.demo1.delay;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class DelayQueueManager implements CommandLineRunner {
    private DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    /**
     * 加入到延时队列中
     *
     * @param
     */
    public void put(TaskElement element) {
        log.info("加入延时任务：{}", JSONObject.toJSONString(element));
        DelayTask delayTask = new DelayTask(element);
        delayTask.setData(element);
        this.delayQueue.put(delayTask);
    }

    /**
     * 取消延时任务
     *
     * @param task
     * @return
     */
    public boolean remove(DelayTask task) {
        log.info("取消延时任务：{}", task);
        return delayQueue.remove(task);
    }

    /**
     * 取消延时任务
     *
     * @param taskid
     * @return
     */
    public boolean remove(String taskid) {
        return remove(new DelayTask(new TaskElement(taskid)));
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化延时队列");
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
    }

    /**
     * 延时任务执行线程
     */
    private void excuteThread() {
        while (true) {
            try {
                DelayTask task = delayQueue.take();
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
    private void processTask(DelayTask task) {
        log.info("执行延时任务：{}", JSONObject.toJSONString(task));
        //根据task中的data自定义数据来处理相关逻辑，例 if (task.getData() instanceof XXX) {}
    }
}
