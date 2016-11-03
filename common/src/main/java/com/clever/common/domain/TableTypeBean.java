package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.TableTypeBeanView;

import java.util.Date;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 3/18/2016
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class TableTypeBean extends BaseBean {

    private Long tableTypeId;
    private String name;
    private String description;
    private int capacity;
    private int minimum;

    public TableTypeBean(){}

    public TableTypeBean(Long tableTypeId){
        this.setTableTypeId(tableTypeId);
    }

    public TableTypeBean(Long clientId,Long orgId){
        this.setClientId(clientId);
        this.setOrgId(orgId);
    }

    public TableTypeBean(TableTypeBeanView tPV,User user){
        if( null != tPV){
            this.setTableTypeId(tPV.getTableTypeId());
            this.setOrgId(tPV.getOrgId());
            this.setClientId(tPV.getClientId());
            this.setCapacity(tPV.getCapacity());
            this.setDescription(tPV.getDescription());
            this.setName(tPV.getName());
            this.setMinimum(tPV.getMinimum());
        }

        if( null != user){
            this.setCreatedBy(user.getUserId());
            this.setCreated(new Date());
            this.setUpdatedBy(user.getUserId());
            this.setUpdated(new Date());
        }
    }

    public Long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(Long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

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
