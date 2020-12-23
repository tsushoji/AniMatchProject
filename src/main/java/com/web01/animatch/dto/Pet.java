package com.web01.animatch.dto;

import java.time.LocalDateTime;

public class Pet {
	private int petId;
	private byte[] image;
	private String nickName;
	private String sex;
	private String type;
	private float weight;
	private String remarks;
	private int delFlg;
	private LocalDateTime insertedTime;
	private LocalDateTime updatedTime;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
