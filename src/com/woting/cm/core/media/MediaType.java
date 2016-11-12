package com.woting.cm.core.media;

/**
 * 内容类型的枚举值
 * @author wanghui
 */
public enum MediaType {
    RADIO("RADIO", "wt_BroadCast"), AUDIO("AUDIO", "wt_MediaAsset"), SEQU("SEQU", "wt_SeqMediaAsset"), TEXT("TEXT", "wt_Article"), ERR("ERR", "err");

    private String _typeName;
    private String _tabName;
    private MediaType(String typeName, String tabName) {
        _typeName=typeName;
        _tabName=tabName;
    }

    /**
     * 根据表名，创建相应的枚举值
     * @param tabName 表名
     * @return 对应的美剧值
     */
    public static MediaType buildByTabName(String tabName) {
        if (tabName.toUpperCase().equals("wt_BroadCast")) return RADIO;
        else if (tabName.toUpperCase().equals("wt_MediaAsset")) return AUDIO;
        else if (tabName.toUpperCase().equals("wt_SeqMediaAsset")) return SEQU;
        else if (tabName.toUpperCase().equals("wt_Article")) return TEXT;
        else return ERR;
    }

    /**
     * 根据类型名，创建相应的枚举值
     * @param tabName 表名
     * @return 对应的美剧值
     */
    public static MediaType buildByTypeName(String typeName) {
        if (typeName.toUpperCase().equals("RADIO")) return RADIO;
        else if (typeName.toUpperCase().equals("AUDIO")) return AUDIO;
        else if (typeName.toUpperCase().equals("SEQU")) return SEQU;
        else if (typeName.toUpperCase().equals("TEXT")) return TEXT;
        else return ERR;
    }

    /**
     * 获取枚举值对应的表名
     * @return 对应表名
     */
    public String getTabName() {
        return _tabName;
    }

    /**
     * 获取枚举值对应的分类名
     * @return 对应的分类名
     */
    public String getTypeName() {
        return _typeName;
    }
}