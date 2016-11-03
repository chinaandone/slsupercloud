package com.clever.common.bean;

public class ImageView {


    public ImageView(Integer mode, Integer width, Integer height) {
        this.mode = mode;
        this.width = width;
        this.height = height;
    }

    private Integer mode;
    private Integer width;

    private Integer height;
    private Integer quality;
    private String format;

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "ImageView{" +
                "mode=" + mode +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
