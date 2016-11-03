/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.clever.common.domain.enums;

/**
 * 树目录类型常量定义
 * @author xiangqing.tan
 * @version $Id: TreeMenuTypeEnum.java, v 0.1 2011-8-10 下午04:24:05 xiangqing.tan Exp $
 */
public enum TreeMenuType {
    //叶子
    LEAF("叶子"),

    //节点
    NODE("节点"),

    //根
    ROOT("根");

    ;

    /* 常量描述 */
    private String description;

    /**
     * 构造方法
     * @param description
     */
    TreeMenuType(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }
}
