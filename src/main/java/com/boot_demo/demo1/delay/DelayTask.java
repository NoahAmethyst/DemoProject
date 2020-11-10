package com.boot_demo.demo1.delay;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


@Data
public class DelayTask implements Delayed {

    private TaskElement data;

    private long expire = System.currentTimeMillis() + 3000;

    /**
     * 构造延时任务
     *
     * @param data   业务数据
     * @param expire 任务延时时间（ms）
     */
    public DelayTask(TaskElement data) {
        super();
        this.data = data;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        } else {
            return 0;
        }
    }
}
