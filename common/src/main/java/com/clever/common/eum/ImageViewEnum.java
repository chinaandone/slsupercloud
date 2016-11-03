package com.clever.common.eum;

import com.clever.common.bean.ImageView;

/**
 * INFO: 定义系统支持的图片格式
 * 因为图片使用的是七牛的云存储，因此支持 直接在IMG_URL后面进行格式大小的切换
 * http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/10/3
 * Time: 9:27
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 *
 * @deprecated 直接使用7牛服务器的设置 不需要人为指定
 */
public enum ImageViewEnum {
    MIDDLE("M", new ImageView(2, 600, 400), "中图尺寸"),
    SMALL("S", new ImageView(2, 380, 200), "小图尺寸");

    private String desc;
    private ImageView imageView;
    private String imgType;

    //构造函数必须为private的,防止意外调用
    private ImageViewEnum(String imgType, ImageView imageView,
                          String desc) {

        this.imgType = imgType;
        this.imageView = imageView;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getImgType() {
        return imgType;
    }

    public static ImageViewEnum getEnum(String imgType) {
        if (MIDDLE.getImgType().equals(imgType)) {
            return MIDDLE;
        } else if (SMALL.getImgType().equals(imgType)) {
            return SMALL;

        }
        return null;
    }
}
