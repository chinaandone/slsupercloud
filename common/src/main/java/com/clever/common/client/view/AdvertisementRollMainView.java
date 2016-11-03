package com.clever.common.client.view;


import java.util.Date;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-02
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class AdvertisementRollMainView {
    private Long advertisementMainId;


    private Long advertisementId;

    private Long timeEnd; //默认值从广告对象的关联获取，前端可修改

    private Long timeStart; //默认值从广告对象的关联获取，前端可修改

    private Integer orderSeq; //默认值从广告对象的关联获取，前端可修改

    public AdvertisementRollMainView(){

    }

    public Long getAdvertisementMainId() {
        return advertisementMainId;
    }

    public void setAdvertisementMainId(Long advertisementMainId) {
        this.advertisementMainId = advertisementMainId;
    }

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Integer orderSeq) {
        this.orderSeq = orderSeq;
    }

//    private String active; //有效期由后台维护，自动判断是否有效

//    private Long pictureId;  //从广告对象的关联获取，不需要前端维护

//    private Long rollMainId; //从广告对象的关联获取，主图信息轮播ID是后台维护的

//    private String title; //从广告对象的关联获取，不需要前端维护

//    private Long createdBy;//创建者id由系统后台维护

//    private Date creadted;//创建日期由系统后台生成，前端不需传

//    private Long updatedBy;//修改者id由系统后台维护

//    private Date updated;//更新日期由系统后台生成，前端不需传

}
