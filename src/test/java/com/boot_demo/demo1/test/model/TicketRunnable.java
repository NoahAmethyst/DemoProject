package com.boot_demo.demo1.test.model;

public class TicketRunnable implements Runnable {
    private int sum=200;//sum是基本数据类型，不能用作对象锁，所以要给sum配一把对象锁
    //  private Object obj=new Object();//obj--新建一个与sum共享变量的平行的对象
    private String name;
    public TicketRunnable(String name){
        this.name=name;
    }


    @Override
    public void run() {
//        System.out.println(name+"启动");
        while(true){
            synchronized (this) {//用一个和sum平行的对象也可以，但必须是对象(Object obj=new Object();)
                if (sum > 0) {
                    System.out.println(name + ":" + sum);
                    sum--;
                }else{
                    break;
                }
            }
        }
    }
//总结：非静态变量要作对象锁时，直接用this代替
}

