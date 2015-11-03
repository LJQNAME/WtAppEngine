package com.woting.passport.UGA.persistence.pojo;

import java.sql.Timestamp;
import java.util.List;

import com.spiritdata.framework.core.model.BaseObject;

public class Group extends BaseObject {
    private static final long serialVersionUID = -4171166651180143388L;

    private String groupId; //用户组id
    private String groupName; //用户组名称
    private String groupImg; //用户组头像
    private String pId; //上级用户组Id
    private int sort; //用户组排序
    private String createUserId; //创建者id
    private String adminUserId;  //管理者id
    private String descn; //用户描述
    private Timestamp CTime; //记录创建时间
    private Timestamp lmTime; //最后修改时间:last modify time

    private List<User> groupUsers; //所属用户

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupImg() {
        return groupImg;
    }
    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }
    public String getCreateUserId() {
        return createUserId;
    }
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    public String getAdminUserId() {
        return adminUserId;
    }
    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }
    public String getDescn() {
        return descn;
    }
    public void setDescn(String descn) {
        this.descn = descn;
    }
    public Timestamp getCTime() {
        return CTime;
    }
    public void setCTime(Timestamp cTime) {
        CTime = cTime;
    }
    public Timestamp getLmTime() {
        return lmTime;
    }
    public void setLmTime(Timestamp lmTime) {
        this.lmTime = lmTime;
    }
    public List<User> getGroupUsers() {
        return groupUsers;
    }
    public void setGroupUsers(List<User> groupUsers) {
        this.groupUsers = groupUsers;
    }

    /* 以下属性不在数据库表中明显出现*/
    private int groupCount; //组用户个数
    public int getGroupCount() {
        return groupCount;
    }
    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }
}