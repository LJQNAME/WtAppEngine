package com.woting.appengine.intercom.mem;

import java.util.concurrent.ConcurrentHashMap;
import com.woting.appengine.intercom.model.GroupInterCom;

public class GroupMemory {
    //java的占位单例模式===begin
    private static class InstanceHolder {
        public static GroupMemory instance = new GroupMemory();
    }
    public static GroupMemory getInstance() {
        return InstanceHolder.instance;
    }
    //java的占位单例模式===end

    protected ConcurrentHashMap<String, GroupInterCom> gicMap;//对讲组信息Map

    private GroupMemory() {
        this.gicMap=new ConcurrentHashMap<String, GroupInterCom>();
    }
}