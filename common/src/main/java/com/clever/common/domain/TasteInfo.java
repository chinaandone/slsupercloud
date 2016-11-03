package com.clever.common.domain;

import com.clever.common.bean.BaseInfo;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 1/20/16
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class TasteInfo extends BaseInfo{

    private String tasteId;     //口味编码
    private String name;       //名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTasteId() {
        return tasteId;
    }

    public void setTasteId(String tasteId) {
        this.tasteId = tasteId;
    }

}
