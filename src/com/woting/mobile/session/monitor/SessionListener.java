package com.woting.mobile.session.monitor;

import java.util.Date;
import java.util.Timer;

import com.woting.mobile.session.MobileSessionConfig;
import com.woting.mobile.session.mem.SessionMemory;

/**
 * <pre>会话监听，包括：
 * 1-设置会话过期
 * 2-清空过期的会话
 * </pre>
 * @author wh
 */
//守护线程，与主进程同存亡，用户线程，自己要完成
public class SessionListener extends Thread {
    private static MobileSessionConfig msc=null;

    public static void Beginning(MobileSessionConfig msc) {
        SessionListener.msc=msc;
        SessionListener sl = new SessionListener();
        sl.start();
    }

    //开启会话监控
    private void startMonitor() {
        Timer mobileSession_Timer = new Timer("MobileSessionTimer", true);
        CleanTask ct = new CleanTask();
        mobileSession_Timer.schedule(ct, new Date(), msc.getCLEAN_INTERVAL());
    }

    @Override
    public void run() {
        try {
            sleep(5000);//多少毫秒后启动任务处理，先让系统的其他启动任务完成，这里设置死为10秒钟
            //初始化会话的内存结构
            SessionMemory.getInstance().init(SessionListener.msc);
            startMonitor(); //启动会话监控
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}