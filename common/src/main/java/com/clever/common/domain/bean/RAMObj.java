package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class RAMObj  {
    private String totalRAM;//总RAM
    private String currentRAM;//当前使用RAM

    public String getTotalRAM() {
        return totalRAM;
    }

    public void setTotalRAM(String totalRAM) {
        this.totalRAM = totalRAM;
    }

    public String getCurrentRAM() {
        return currentRAM;
    }

    public void setCurrentRAM(String currentRAM) {
        this.currentRAM = currentRAM;
    }
}
