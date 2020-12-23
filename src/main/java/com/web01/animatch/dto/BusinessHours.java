package com.web01.animatch.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class BusinessHours {
	private String businessDay;
	private Date startBusinessTime;
	private Date endBusinessTime;
	private String complement;
	private int delFlg;
	private LocalDateTime insertedTime;
	private LocalDateTime updatedTime;

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

	public LocalDateTime getInsertedTime() {
		return insertedTime;
	}

	public void setInsertedTime(LocalDateTime insertedTime) {
		this.insertedTime = insertedTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
}
