package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class BraceletCfg {
    private String id;//手环id
    private boolean status;//手环状态
    private String power;//电量图标
    private String smsTotal;//信息总条数
    private String smsCurrent;//当前条数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getSmsTotal() {
        return smsTotal;
    }

    public void setSmsTotal(String smsTotal) {
        this.smsTotal = smsTotal;
    }

    public String getSmsCurrent() {
        return smsCurrent;
    }

    public void setSmsCurrent(String smsCurrent) {
        this.smsCurrent = smsCurrent;
    }
}
