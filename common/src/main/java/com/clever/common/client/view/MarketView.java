package com.clever.common.client.view;

import com.clever.common.domain.MarketMonitor;

import java.util.Date;
import java.util.List;

/**
 * Created by Chay on 2016/7/26.
 */
public class MarketView {
    private Long marketId;//主键
    private String company;//发布公司
    private String brand;//发布品牌
    private String videoName;//发布视频名称
    private int mediaForm;//媒体形式
    private String mediaFormName;//媒体形式名称
    private int adLength;//广告长度
    private String adLengthName;//广告长度名称
    private int playFreq;//播放频次
    private String playFreqName;//播放频次名称
    private Long timeStart;//上画时间时间戳
    private Date timeStartDate;//上画时间
    private Long timeEnd;//下画时间时间戳
    private Date timeEndDate;//下画时间
    private String circle;//商圈，跟门店关联
    private List<MarketMonitor> monitors;//监控照片，跟门店关联
    private Long orgId;//门店ID
    private String clientName;//品牌名
    private String orgName;//门店名
    private Long deviceCount;//当前门店下设备数量


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

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public List<MarketMonitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<MarketMonitor> monitors) {
        this.monitors = monitors;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Long deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getMediaFormName() {
        return mediaFormName;
    }

    public void setMediaFormName(String mediaFormName) {
        this.mediaFormName = mediaFormName;
    }

    public String getAdLengthName() {
        return adLengthName;
    }

    public void setAdLengthName(String adLengthName) {
        this.adLengthName = adLengthName;
    }

    public String getPlayFreqName() {
        return playFreqName;
    }

    public void setPlayFreqName(String playFreqName) {
        this.playFreqName = playFreqName;
    }

    public Date getTimeStartDate() {
        return timeStartDate;
    }

    public void setTimeStartDate(Date timeStartDate) {
        this.timeStartDate = timeStartDate;
    }

    public Date getTimeEndDate() {
        return timeEndDate;
    }

    public void setTimeEndDate(Date timeEndDate) {
        this.timeEndDate = timeEndDate;
    }
}
