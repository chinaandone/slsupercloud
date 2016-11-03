package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class SelfCheckBean {
    private String checkName;
    private boolean status;

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
