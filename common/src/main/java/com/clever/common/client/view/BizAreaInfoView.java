package com.clever.common.client.view;

import com.clever.common.bean.BaseInfo;

import java.util.List;

/**
 * Info: 商圈信息
 * User: zhangxinglong@rui10.com
 * Date: 15/5/18
 * Time: 14:54
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class BizAreaInfoView extends BaseInfo {

    private String name;  //商圈名称
    private List<BizAreaInfoView> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BizAreaInfoView> getList() {
        return list;
    }

    public void setList(List<BizAreaInfoView> list) {
        this.list = list;
    }

}
