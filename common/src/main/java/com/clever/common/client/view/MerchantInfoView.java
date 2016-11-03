package com.clever.common.client.view;

import com.clever.common.bean.BaseInfo;

/**
 * Info: 商户信息（客户端显示）
 * User: zhangxinglong@rui10.com
 * Date: 5/15/15
 * Time: 10:09
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class MerchantInfoView extends BaseInfo {

    private String name;
    private String nearAddr;
    private int remain;
    private int red; //0没有 1有
    private int dietary;//0没有 1有
    private long bizAreaId;//
    private String position;
    private int marketType; //市场类型 10已安装硬件 20签合同未安装 30未安装未签合同 40导入
    private boolean queueFree = Boolean.TRUE; //免排队标志
    private long cityId;
    private int soldCount;   //订单量
    private Integer averagePrice;   //人均消费
    private String photoStr;   //商户图片



    public String getPhotoStr() {
        return photoStr;
    }

    public void setPhotoStr(String photoStr) {
        this.photoStr = photoStr;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getDietary() {
        return dietary;
    }

    public void setDietary(int dietary) {
        this.dietary = dietary;
    }

    public long getBizAreaId() {
        return bizAreaId;
    }

    public void setBizAreaId(long bizAreaId) {
        this.bizAreaId = bizAreaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNearAddr() {
        return nearAddr;
    }

    public void setNearAddr(String nearAddr) {
        this.nearAddr = nearAddr;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isQueueFree() {
        return queueFree;
    }

    public void setQueueFree(boolean queueFree) {
        this.queueFree = queueFree;
    }

    public int getMarketType() {
        return marketType;
    }

    public void setMarketType(int marketType) {
        this.marketType = marketType;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }


}
