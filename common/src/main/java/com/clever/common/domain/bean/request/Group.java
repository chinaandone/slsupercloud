package com.clever.common.domain.bean.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95 on 2016/4/20.
 */
public class Group {
    private String deviceId;
    private List<Member> members = new ArrayList<Member>();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
