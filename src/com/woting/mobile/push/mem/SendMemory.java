package com.woting.mobile.push.mem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.woting.mobile.model.MobileKey;
import com.woting.mobile.push.model.Message;

public class SendMemory {
    //java的占位单例模式===begin
    private static class InstanceHolder {
        public static SendMemory instance = new SendMemory();
    }
    public static SendMemory getInstance() {
        return InstanceHolder.instance;
    }
    //java的占位单例模式===end

    protected ConcurrentHashMap<MobileKey, ConcurrentLinkedQueue<Message>> msgMap;//将要发送的消息列表

    /**
     * 初始化发送消息列表
     */
    private SendMemory() {
        msgMap=new ConcurrentHashMap<MobileKey, ConcurrentLinkedQueue<Message>>();
    }

    protected Map<MobileKey, ConcurrentLinkedQueue<Message>> getMsgMap() {
        return this.msgMap;
    }

    /**
     * 向某一设移动设备的输出队列中插入
     * @param mk 移动设备标识
     * @param msg 消息数据
     */
    public void addMsg2Queue(MobileKey mk, Message msg) {
        ConcurrentLinkedQueue<Message> mobileQueue=this.msgMap.get(mk);
        if (mobileQueue==null) {
            mobileQueue=new ConcurrentLinkedQueue<Message>();
            this.msgMap.put(mk, mobileQueue);
        }
        mobileQueue.add(msg);
    }

    /**
     * 从某一设备的发送队列中取出要发送的消息，并从该队列中将这条消息移除
     * @param mk 设备标识
     * @return 消息
     */
    public Message pollTypeQueue(MobileKey mk) {
        if (this.msgMap==null) return null;
        if (this.msgMap.get(mk)==null) return null;
        return this.msgMap.get(mk).poll();
    }

    /**
     * 从某一设备的发送队列中取出要发送的消息，但不从该队列移除这条消息，这条消息还存在于设备发送队列中
     * @param mk 设备标识
     * @return 消息
     */
    public Message peekMobileQueue(MobileKey mk) {
        if (this.msgMap==null) return null;
        if (this.msgMap.get(mk)==null) return null;
        return this.msgMap.get(mk).peek();
    }
}