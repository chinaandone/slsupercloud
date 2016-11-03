package com.clever.common.bean;

/**
 * INFO: ${todo}
 * User: zhaokai@mail.qianwang365.com
 * Date: 10/31
 * Time: 15:50
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */

public class NotifyMsg {


    private Long productId;        //商品id
    private String picUrl;    //商品图片
    private String title;    //标题
    private String productName; //商品名称
    private String price;//订单价格
    private Long stock;    //库存
    private int status;
    private String errorMsg; //审核不通过时要加入原因

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "NotifyMsg{" +
                "productId=" + productId +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", stock=" + stock +
                ", status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
