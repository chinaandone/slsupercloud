package com.clever.common.domain;

import com.clever.common.bean.BaseBean;

import java.util.Date;

public class ProductStatusLog extends BaseBean {
    private Long productStatusLogId;
    private Long productStatusTypeId;//上传记录的类型ID。说明:1.开机;2.12点状态;3.19点状态;4.关机;1000.用户说的话;1001.语音Log;10000.离线;10001.上线;
    private Long computerId;
    private String boardNo;
    private int computerType;
    private String productGlobalIp;
    private String productLocalIp;
    private String productPower;//电池电量,百分比
    private String tableId;
    private String detail;
    private String softVersionCode;//软件版本号
    private String softVersionName;//软件版本名称
    private String chargeStatus;//充电状态,0充电,1未充电
    private String ramStatus;//RAM状态
    private String romStatus;//ROM状态
    private String volume;//音量
    private String wifiStatus;//WIFI状态
    private String picId;
    private String qiniuPath;//截图七牛路径
    private String macAddress;//设备网卡MAC地址
    private String padMode;//平板设备型号
    private String OSVersion;//平板系统软件版本
    private Long timestamp;//上传日志时的平板系统时间，查看平板系统时间是否正确的依据
    private Date logTime;//上传日志时的平板系统时间，查看平板系统时间是否正确的依据

    public ProductStatusLog() {
    }

    public Long getProductStatusLogId() {
        return productStatusLogId;
    }

    public void setProductStatusLogId(Long productStatusLogId) {
        this.productStatusLogId = productStatusLogId;
    }

    public Long getProductStatusTypeId() {
        return productStatusTypeId;
    }

    public void setProductStatusTypeId(Long productStatusTypeId) {
        this.productStatusTypeId = productStatusTypeId;
    }

    public Long getComputerId() {
        return computerId;
    }

    public void setComputerId(Long computerId) {
        this.computerId = computerId;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public int getComputerType() {
        return computerType;
    }

    public void setComputerType(int computerType) {
        this.computerType = computerType;
    }

    public String getProductGlobalIp() {
        return productGlobalIp;
    }

    public void setProductGlobalIp(String productGlobalIp) {
        this.productGlobalIp = productGlobalIp;
    }

    public String getProductLocalIp() {
        return productLocalIp;
    }

    public void setProductLocalIp(String productLocalIp) {
        this.productLocalIp = productLocalIp;
    }

    public String getProductPower() {
        return productPower;
    }

    public void setProductPower(String productPower) {
        this.productPower = productPower;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSoftVersionCode() {
        return softVersionCode;
    }

    public void setSoftVersionCode(String softVersionCode) {
        this.softVersionCode = softVersionCode;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getRamStatus() {
        return ramStatus;
    }

    public void setRamStatus(String ramStatus) {
        this.ramStatus = ramStatus;
    }

    public String getRomStatus() {
        return romStatus;
    }

    public void setRomStatus(String romStatus) {
        this.romStatus = romStatus;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWifiStatus() {
        return wifiStatus;
    }

    public void setWifiStatus(String wifiStatus) {
        this.wifiStatus = wifiStatus;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getQiniuPath() {
        return qiniuPath;
    }

    public void setQiniuPath(String qiniuPath) {
        this.qiniuPath = qiniuPath;
    }

    public String getSoftVersionName() {
        return softVersionName;
    }

    public void setSoftVersionName(String softVersionName) {
        this.softVersionName = softVersionName;
    }

    public String getPadMode() {
        return padMode;
    }

    public void setPadMode(String padMode) {
        this.padMode = padMode;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}
