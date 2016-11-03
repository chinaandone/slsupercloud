package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class ObstacleCfg  {
    private boolean status;//避障设置状态open/close
    private int statusValue;//避障设置状态值

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }
}
