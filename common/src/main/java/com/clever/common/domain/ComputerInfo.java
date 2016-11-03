package com.clever.common.domain;

import com.clever.common.bean.BaseInfo;

/**
 * Created by 95 on 2016/4/26.
 */
public class ComputerInfo extends BaseInfo {
    private long orgId;
    private String name;
    private String description;
    private String boardNo;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
