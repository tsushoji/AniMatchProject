package com.web01.animatch.dto;

import java.time.LocalDateTime;
import java.util.List;

public class Store {
	private int storeId;
	private byte[] image;
	private List<BusinessHours> businessHoursList;
	private String storeName;
	private int employeesNumber;
	private String courseInfo;
	private String commitment;
	private int delFlg;
	private LocalDateTime insertedTime;
	private LocalDateTime updatedTime;

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public List<BusinessHours> getBusinessHoursList() {
		return businessHoursList;
	}

	public void setBusinessHoursList(List<BusinessHours> businessHoursList) {
		this.businessHoursList = businessHoursList;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getEmployeesNumber() {
		return employeesNumber;
	}

	public void setEmployeesNumber(int employeesNumber) {
		this.employeesNumber = employeesNumber;
	}

	public String getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(String courseInfo) {
		this.courseInfo = courseInfo;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
