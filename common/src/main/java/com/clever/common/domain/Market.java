package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.MarketView;
import com.clever.common.util.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Chay on 2016/7/26.
 */
public class Market extends BaseBean {
    private Long    marketId;//主键
    private String  company;//发布推广需求的公司名
    private String  brand;//发布推广需求的公司旗下的品牌名
    private String  videoName;//发布视频名称
    private String  circle;//商圈
    private int     mediaForm;//媒体形式
    private int     adLength;//广告长度
    private int     playFreq;//播放频次
    private Date    timeStart;//上画时间
    private Date    timeEnd;//下画时间
    private Long deviceCount;//当前门店下设备数量

    private List<MarketMonitor> monitors;//监控照片

    public Market(){}

    public Market(Long marketId){
        this.marketId = marketId;
    }

    public Market(MarketView marketView, User user) {
        if(marketView != null){
            this.marketId       =   marketView.getMarketId();
            this.company        =   marketView.getCompany();
            this.brand          =   marketView.getBrand();
            this.videoName      =   marketView.getVideoName();
            this.circle         =   marketView.getCircle();
            this.mediaForm      =   marketView.getMediaForm();
            this.adLength       =   marketView.getAdLength();
            this.playFreq       =   marketView.getPlayFreq();
            this.timeStart      =   DateTime.toMillis(marketView.getTimeStart());
            this.timeEnd        =   DateTime.toMillis(marketView.getTimeEnd());
            this.monitors       =   marketView.getMonitors();
        }
        if(user != null){
            this.setCreatedBy(user.getUserId());
            this.setCreated(new Date());
        }
    }


    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getMediaForm() {
        return mediaForm;
    }

    public void setMediaForm(int mediaForm) {
        this.mediaForm = mediaForm;
    }

    public int getAdLength() {
        return adLength;
    }

    public void setAdLength(int adLength) {
        this.adLength = adLength;
    }

    public int getPlayFreq() {
        return playFreq;
    }

    public void setPlayFreq(int playFreq) {
        this.playFreq = playFreq;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<MarketMonitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<MarketMonitor> monitors) {
        this.monitors = monitors;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public Long getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Long deviceCount) {
        this.deviceCount = deviceCount;
    }
}
