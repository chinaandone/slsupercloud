package com.clever.common.domain;

import com.clever.common.bean.BaseInfo;

import java.util.Date;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-03-30
 * Time: 14:46
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class VersionInfo extends BaseInfo{

    private String name;         //版本名称
    private long shopId;         //商户ID
    private String version;      //版本号
    private String info;         //版本升级显示信息
    private String url;          //版本升级url
    private String description;  //版本升级备注
    private Date updateTime;     //更新时间状态0生效1不生效
    private int status;          //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
