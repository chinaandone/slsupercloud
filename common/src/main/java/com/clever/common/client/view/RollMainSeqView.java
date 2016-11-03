package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;

import java.util.List;


public class RollMainSeqView {

	private Long rollMainId;//轮播主图信息ID

	private Integer orderSeq;//排列顺序

	public Long getRollMainId() {
		return rollMainId;
	}

	public void setRollMainId(Long rollMainId) {
		this.rollMainId = rollMainId;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}
}