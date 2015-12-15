package com.woting.mobile.push.monitor;

import java.util.Date;
import java.util.Timer;

import com.woting.mobile.push.PushConfig;
import com.woting.mobile.push.PushSocketServer;
import com.woting.mobile.push.mem.ReceiveMemory;
import com.woting.mobile.session.monitor.CleanSessionTask;

public class PushListener extends Thread {
    private static PushConfig pc=null;//推送服务的配置信息

    public static void begin(PushConfig pc) {
        PushListener.pc=pc;
        PushListener pl = new PushListener();
        pl.start();
    }

    //开启推送内存清理任务
    private void startCleanMonitor() {
        System.out.println("启动推送内存清理任务，任务启动间隔["+pc.getCLEAN_INTERVAL()+"]毫秒");
        Timer pushClean_Timer = new Timer("CleanPushMemoryTimer", true);
        CleanPushMemoryTask cpmt = new CleanPushMemoryTask();
        pushClean_Timer.schedule(cpmt, new Date(), pc.getCLEAN_INTERVAL());
    }

    @Override
    public void run() {
        try {
            sleep(5000);//多少毫秒后启动任务处理，先让系统的其他启动任务完成，这里设置死为10秒钟
            //初始化内存结构
            ReceiveMemory.getInstance();
            //启动服务，服务
            PushSocketServer pss = new PushSocketServer(pc);
            pss.setDaemon(true);
            pss.start();
            //启动读取线程
            int i=0;
            for (;i<pc.getTHREADCOUNT_DEALRECEIVEQUEUE(); i++) {
                DealReceivePureQueue drpq = new DealReceivePureQueue(""+i);
                drpq.setDaemon(true);
                drpq.start();
            }
            //启动读取-接收队列-线程
            //启动清理内存服务——垃圾回收
            startCleanMonitor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}