package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.AdvertisementRollMainView;
import com.clever.common.util.DateTime;

import java.util.Date;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-02
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class AdvertisementRollMain extends BaseBean {

	//BaseBean已经包含基本公有参数：clientId，orgId,active,createdBy,created,updatedBy,updated
	private Long advertisementMainId;

	private Long advertisementId;

	private Integer orderSeq;

	private Long pictureId;

	private Date timeEnd;

	private Date timeStart;

	private String title;

	private Long rollMainId;

	private String orgName;

	public AdvertisementRollMain(){

	}

	public AdvertisementRollMain(Long rollMainId,
								 Long advertisementMainId,
								 String title,
								 Date created
								 ){
		this.rollMainId = rollMainId;
		this.advertisementMainId = advertisementMainId;
		this.title = title;
		this.setCreated(created);
	}

	/**
	 * 初始化插入AdvertisementRollMain对象的构造函数
	 * @param advertisementRollMainView 前端传来的advertisementRollMain对象，目前只包含该对象id，关联要推送的广告id
	 * @param advertisement 要推的广告对象
	 * @param user 用户对象
	 * @param orgId 具体推到哪个店铺的id
	 * @param clientId 具体要推的店铺属于哪个品牌的id
	 * @param created 为保证同一批次的创建日期是相同的，我们传入统一的创建日期
	 */
	public AdvertisementRollMain(
			AdvertisementRollMainView advertisementRollMainView,
			Advertisement advertisement,
			User user,
			Long orgId,
			Long clientId,
			Date created){
		//advertisementRollMainView
		if( null != advertisementRollMainView){
			//顺序，开始时间，结束时间默认值从广告对象取，同时前端可以修改
			this.timeEnd = DateTime.toMillis(advertisementRollMainView.getTimeEnd());
			this.timeStart = DateTime.toMillis(advertisementRollMainView.getTimeStart());
			//优先级先取前端传回来的值，如果为空，则用广告的值，如果也为空，则用默认值
			if(null != advertisementRollMainView.getOrderSeq()){
				this.orderSeq = advertisementRollMainView.getOrderSeq();
			}
			else if( null != advertisement.getOrderSeq()){
				this.orderSeq = advertisement.getOrderSeq();
			}
		}
		//advertisement
		if (null != advertisement){
			this.advertisementId = advertisement.getAdvertisementId();
			this.pictureId = advertisement.getPictrueId();
			this.title = advertisement.getTitle();
			this.rollMainId = advertisement.getRollMainId();
		}

		//user
		if ( null != user){
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}

		//time
		if(null != created) {
			this.setCreated(created);
			this.setUpdated(created);
		}
		else {
			Date tempDate = new Date();
			this.setCreated(tempDate);
			this.setUpdated(tempDate);
		}

		//others
		this.setOrgId(orgId);
		this.setClientId(clientId);
	}

	public Long getAdvertisementMainId() {
		return advertisementMainId;
	}

	public void setAdvertisementMainId(Long advertisementMainId) {
		this.advertisementMainId = advertisementMainId;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

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

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}