package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 1/20/16
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class TableTypeBeanView extends BaseBean {
    private Long tableTypeId;//桌子类型ID
    private String name;//桌子类型名称
    private String description;//桌子类型描述
    private int capacity;//桌子类型载客能力
    private int minimum;//桌子类型承受最小人数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(Long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }
}
