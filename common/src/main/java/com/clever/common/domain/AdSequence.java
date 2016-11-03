package com.clever.common.domain;

import com.clever.common.bean.BaseBean;


public class AdSequence extends BaseBean {

	private Long adSequenceId;

	private String name;

	private String description;

	private String vformat;

	private Integer isautosequence;

	private Long incrementno;

	private Long startno;

	private Long currentnext;

	private Long currentnextsys;

	private String isaudited;

	private Integer istableid;

	private String prefix;

	private String suffix;

	private Integer startnewyear;

	private String datecolumn;

	private String decimalpattern;

	public AdSequence(){

	}

	public AdSequence(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAdSequenceId() {
		return adSequenceId;
	}

	public void setAdSequenceId(Long adSequenceId) {
		this.adSequenceId = adSequenceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVformat() {
		return vformat;
	}

	public void setVformat(String vformat) {
		this.vformat = vformat;
	}

	public Integer getIsautosequence() {
		return isautosequence;
	}

	public void setIsautosequence(Integer isautosequence) {
		this.isautosequence = isautosequence;
	}

	public Long getIncrementno() {
		return incrementno;
	}

	public void setIncrementno(Long incrementno) {
		this.incrementno = incrementno;
	}

	public Long getStartno() {
		return startno;
	}

	public void setStartno(Long startno) {
		this.startno = startno;
	}

	public Long getCurrentnext() {
		return currentnext;
	}

	public void setCurrentnext(Long currentnext) {
		this.currentnext = currentnext;
	}

	public Long getCurrentnextsys() {
		return currentnextsys;
	}

	public void setCurrentnextsys(Long currentnextsys) {
		this.currentnextsys = currentnextsys;
	}

	public String getIsaudited() {
		return isaudited;
	}

	public void setIsaudited(String isaudited) {
		this.isaudited = isaudited;
	}

	public Integer getIstableid() {
		return istableid;
	}

	public void setIstableid(Integer istableid) {
		this.istableid = istableid;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Integer getStartnewyear() {
		return startnewyear;
	}

	public void setStartnewyear(Integer startnewyear) {
		this.startnewyear = startnewyear;
	}

	public String getDatecolumn() {
		return datecolumn;
	}

	public void setDatecolumn(String datecolumn) {
		this.datecolumn = datecolumn;
	}

	public String getDecimalpattern() {
		return decimalpattern;
	}

	public void setDecimalpattern(String decimalpattern) {
		this.decimalpattern = decimalpattern;
	}
}