package com.woting.searchword.model;

import java.io.Serializable;
import java.util.Map;

import com.woting.cm.core.common.model.Owner;

/**
 * 属于某一个所有者的查找此结构
 * 词管理就是通过这个数据来完成的
 * @author wanghui
 */
public class OwnerWord implements Serializable {
    private static final long serialVersionUID = 3456689479976991230L;

    private Owner owner; //本组词管理的所有人，目前只有系统所有者
    private int splitLevel; //向下分级的层数，默认值为5
    private Map<String, MiddleWord> middleWordMap; //用于查找热词的结构
    private Map<String, Word> searchFinalMap; //最终查找词的结构

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public int getSplitLevel() {
        return splitLevel;
    }
    public void setSplitLevel(int splitLevel) {
        this.splitLevel = splitLevel;
    }
    public Map<String, MiddleWord> getSearchMap() {
        return middleWordMap;
    }
    public void setSearchMap(Map<String, MiddleWord> searchMap) {
        this.middleWordMap = searchMap;
    }
    public Map<String, Word> getSearchFinalMap() {
        return searchFinalMap;
    }
    public void setSearchFinalMap(Map<String, Word> searchFinalMap) {
        this.searchFinalMap = searchFinalMap;
    }
}