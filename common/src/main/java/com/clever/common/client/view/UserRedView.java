package com.clever.common.client.view;

import com.clever.common.bean.BaseInfo;

import java.util.Date;

/**
 * Info: 红包（手机端显示）
 * User: zhangxinglong@rui10.com
 * Date: 5/16/15
 * Time: 09:47
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class UserRedView extends BaseInfo {

    private int amount;     //红包面值
    private Date startTime;    //有效期开始时间
    private Date endTime;      //有效期结束时间

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}
