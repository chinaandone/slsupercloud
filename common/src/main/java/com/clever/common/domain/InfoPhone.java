package com.clever.common.domain;

import com.clever.common.bean.BaseInfo;

/**
 * Created by 95 on 2016/4/26.
 */
public class InfoPhone extends BaseInfo {
    private long orgId;
    private String name;
    private String phone;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
