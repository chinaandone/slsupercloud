package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class InductionCfg {
    private boolean status;//接近感应状态open/close
    private int statusValue;//接近感应状态值

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
