package com.web01.animatch.dto;

import java.util.Date;

import org.joda.time.DateTime;

public class BusinessHours {
	private String businessDay;
	private Date startBusinessTime;
	private Date endBusinessTime;
	private String complement;
	private int delFlg;
	private DateTime insertedTime;
	private DateTime updatedTime;

	public String getBusinessDay() {
		return businessDay;
	}

	public void setBusinessDay(String businessDay) {
		this.businessDay = businessDay;
	}

	public Date getStartBusinessTime() {
		return startBusinessTime;
	}

	public void setStartBusinessTime(Date startBusinessTime) {
		this.startBusinessTime = startBusinessTime;
	}

	public Date getEndBusinessTime() {
		return endBusinessTime;
	}

	public void setEndBusinessTime(Date endBusinessTime) {
		this.endBusinessTime = endBusinessTime;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}

	public DateTime getInsertedTime() {
		return insertedTime;
	}

	public void setInsertedTime(DateTime insertedTime) {
		this.insertedTime = insertedTime;
	}

	public DateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(DateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
}
