package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class ROMObj {
    private String totalROM;//总RAM
    private String currentROM;//当前使用RAM

    public String getTotalROM() {
        return totalROM;
    }

    public void setTotalROM(String totalROM) {
        this.totalROM = totalROM;
    }

    public String getCurrentROM() {
        return currentROM;
    }

    public void setCurrentROM(String currentROM) {
        this.currentROM = currentROM;
    }
}
