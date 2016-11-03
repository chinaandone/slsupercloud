package com.clever.common.domain.enums;

public enum PictrueKindType {
    NOTYPE(0, "未分类"),//未分类
    ACTIVITY(1, "优惠专区"), //优惠专区
    ADVERTISEMENT(2, "广告图片"), //广告图片
    FOOD(3, "菜品图片");//菜品图片
    // 成员变量
    private String name;
    private Integer index;

    // 构造方法
    private PictrueKindType(Integer index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(Integer index) {
        if(index == null){
            index = 0;
        }
        for (PictrueKindType c : PictrueKindType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
