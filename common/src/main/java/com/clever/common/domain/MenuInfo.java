package com.clever.common.domain;

import com.clever.common.bean.BaseInfo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 1/20/16
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class MenuInfo extends BaseInfo{
    private String menuId;     //ERP编码
    private String subMenuId;  //
    private long shopId;       //商户编号
    private String name;       //名称
    private String price;      //价格
    private int amount;        //价格(分)
    private String unit;       //单位
    private String menuCode;   //编码
    private String scanCode;   //扫描码
    private Date createTime;
    private Date updateTime;
    private int status;
    private String photo;

    public MenuInfo(){
    }

    public MenuInfo(long shopId){
        this.shopId = shopId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getSubMenuId() {
        return subMenuId;
    }

    public void setSubMenuId(String subMenuId) {
        this.subMenuId = subMenuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        if(amount>0){
            DecimalFormat format = new DecimalFormat("0.00");
            price = format.format(new BigDecimal(amount/100));
        }
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
