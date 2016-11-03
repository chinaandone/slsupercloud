package com.clever.common.view;


import com.clever.common.eum.ProductAuditEnum;
import com.clever.common.eum.ProductPublishEnum;
import com.clever.common.exception.DataValidateException;

public class GoodsOnSaleView {

    private Long id;
    private String proName;
    private String labelName;
    private String imagePath;
    private String qrCodePath;
    private Long productPrice;//商品单价  单位分
    private Long stockNum;//库存数量

    private Integer auditState = ProductAuditEnum.INIT.getIntVlue();     //ProductAuditEnum
    private Integer publishState = ProductPublishEnum.UP.getIntVlue(); //ProductPublishEnum
    //冗余字段
    private Integer finalAuditState; //展示主要给API展示用的 审核状态  （把 2 ， 4合并成一个 通过)

    private String maxTime;

    private String auditDesc; //最后一次审核不通过，通过原因


    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
    }

    public void setFinalAuditState(Integer finalAuditState) {
        this.finalAuditState = finalAuditState;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    public Integer getFinalAuditState() throws DataValidateException {
        if (this.finalAuditState != null) {
            return this.finalAuditState;
        }
        ProductAuditEnum enum1 = ProductAuditEnum.getEnum(this.getAuditState());
        if (enum1 == ProductAuditEnum.ERROR) {
            throw new DataValidateException("商品审核状态码异常");
        }
        int finalAuditState = 0;

        //将 2 4 合成状态2
        switch (enum1) {
            case PASS:
            case PASS_LIMIT:
                finalAuditState = ProductAuditEnum.PASS.getIntVlue();
                break;
            default:
                finalAuditState = enum1.getIntVlue();
                break;
        }
        return finalAuditState;

    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }
}
