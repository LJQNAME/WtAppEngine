package com.woting.mobile.push.mem;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.woting.mobile.model.MobileKey;
import com.woting.mobile.push.model.Message;

public class PushMemoryManage {
    //java的占位单例模式===begin
    private static class InstanceHolder {
        public static PushMemoryManage instance = new PushMemoryManage();
    }
    public static PushMemoryManage getInstance() {
        PushMemoryManage pmm = InstanceHolder.instance;
        return pmm;
    }
    //java的占位单例模式===end

    protected ReceiveMemory rm; //接收数据内存结构
    protected SendMemory sm; //发送数据内存结构

    private boolean serverIsRuning=false; //推送服务是否正常运行
    public boolean isServerIsRuning() {
        return this.serverIsRuning;
    }
    public void setServerIsRuning(boolean serverIsRuning) {
        this.serverIsRuning = serverIsRuning;
    }

    /*
     * 构造方法，设置消息推送内存结构
     */
    private PushMemoryManage() {
        rm=ReceiveMemory.getInstance();
        sm=SendMemory.getInstance();
    }

    /**
     * 清理发送内存结构，把没有数据的设备删除掉
     * ConcurrentLinkedQueue<Message>
     */
    public void clean() {
        if (this.sm.msgMap!=null&&!this.sm.msgMap.isEmpty()) {
            for (MobileKey sKey: this.sm.msgMap.keySet()) {
                ConcurrentLinkedQueue<Message> mq = this.sm.msgMap.get(sKey);
                if (mq==null||mq.isEmpty()) this.sm.msgMap.remove(sKey);
            }
        }
    }

    public ReceiveMemory getReceiveMemory() {
        return this.rm;
    }

    public SendMemory getSendMemory() {
        return this.sm;
    }
}