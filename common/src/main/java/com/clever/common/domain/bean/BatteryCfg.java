package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class BatteryCfg {
    private String surplusTime;//剩余时间
    private String currentRate;//当前放电率

    public String getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(String currentRate) {
        this.currentRate = currentRate;
    }

    public String getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }
}
