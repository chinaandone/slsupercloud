package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.Pictrue;


public class RollDetailView {

	private Long rollDetailId;//轮播详细ID

//	private Long rollMainId;//轮播主图信息ID

	private Long pictrueId;//轮播详细图片ID

//	private Long detailTextId;//轮播详细文本ID，保存不需要传入

	private Integer orderSeq;//排列顺序
//
//	private Integer rollTime;//轮播时间

	public RollDetailView(){

	}

	public Long getRollDetailId() {
		return rollDetailId;
	}

	public void setRollDetailId(Long rollDetailId) {
		this.rollDetailId = rollDetailId;
	}

	public Long getPictrueId() {
		return pictrueId;
	}

	public void setPictrueId(Long pictrueId) {
		this.pictrueId = pictrueId;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

}