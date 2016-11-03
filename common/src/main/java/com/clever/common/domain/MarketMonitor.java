package com.clever.common.domain;

/**
 * Created by Chay on 2016/7/26.
 * 广告推广跟监控照片关联的中间类
 */
public class MarketMonitor implements Comparable<MarketMonitor>{
    private Long marketId;

    private Long pictrueId;

    private String pictruePath;

    private String qiniuPath;

    private String picOrinName;//图片原始文件名

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getPictrueId() {
        return pictrueId;
    }

    public void setPictrueId(Long pictrueId) {
        this.pictrueId = pictrueId;
    }

    public String getPictruePath() {
        return pictruePath;
    }

    public void setPictruePath(String pictruePath) {
        this.pictruePath = pictruePath;
    }

    public String getQiniuPath() {
        return qiniuPath;
    }

    public void setQiniuPath(String qiniuPath) {
        this.qiniuPath = qiniuPath;
    }

    public String getPicOrinName() {
        return picOrinName;
    }

    public void setPicOrinName(String picOrinName) {
        this.picOrinName = picOrinName;
    }

    @Override
    public int compareTo(MarketMonitor o) {
        //因为marketId都是相同的，所以排序比较靠picId就可以了
        return this.pictrueId.compareTo(o.pictrueId);
    }
}
