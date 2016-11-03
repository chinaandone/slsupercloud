package com.clever.common.domain.bean;

/**
 * Created by 95 on 2016/4/18.
 */
public class WirelessNetwork {
    private String mode;//通讯方式
    private String working ;//工作频率
    private String stability ;//频率稳定度
    private String power;//发射功率
    private String electric;//静态电流

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
