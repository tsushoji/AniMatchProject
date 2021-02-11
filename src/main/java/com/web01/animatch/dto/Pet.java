package com.web01.animatch.dto;

import java.time.LocalDateTime;

/**
 * ペットオブジェクトクラス
 * @author Tsuji
 * @version 1.0
 */
public class Pet {

	//メンバー
	/**
	 * ペットID
	 */
	private int petId;
	/**
	 * 画像
	 */
	private byte[] image;
	/**
	 * ニックネーム
	 */
	private String nickName;
	/**
	 * 性別
	 */
	private String sex;
	/**
	 * 種類
	 */
	private String type;
	/**
	 * 体重
	 */
	private float weight;
	/**
	 * 補足
	 */
	private String remarks;
	/**
	 * 削除ステータス
	 */
	private int isDeleted;
	/**
	 * 新規登録時間
	 */
	private LocalDateTime insertedTime;
	/**
	 * 更新時間
	 */
	private LocalDateTime updatedTime;

	/**
	 * ペットIDgetter
	 * @return ペットID
	 */
	public int getPetId() {
		return petId;
	}

	/**
	 * ペットIDsetter
	 * @param ペットID
	 */
	public void setPetId(int petId) {
		this.petId = petId;
	}

	/**
	 * 画像getter
	 * @return 画像
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * 画像setter
	 * @param 画像
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * ニックネームgetter
	 * @return ニックネーム
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * ニックネームsetter
	 * @param ニックネーム
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 性別getter
	 * @return 性別
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 性別setter
	 * @param 性別
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 種類getter
	 * @return 種類
	 */
	public String getType() {
		return type;
	}

	/**
	 * 種類setter
	 * @param 種類
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 体重getter
	 * @return 体重
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * 体重setter
	 * @param 体重
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * 補足getter
	 * @return 補足
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 補足setter
	 * @param 補足
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 削除ステータスgetter
	 * @return 削除ステータス
	 */
	public int getIsDeleted() {
		return isDeleted;
	}

	/**
	 * 削除ステータスsetter
	 * @param 削除ステータス
	 */
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * 新規登録時間getter
	 * @return 新規登録時間
	 */
	public LocalDateTime getInsertedTime() {
		return insertedTime;
	}

	/**
	 * 新規登録時間setter
	 * @param 新規登録時間
	 */
	public void setInsertedTime(LocalDateTime insertedTime) {
		this.insertedTime = insertedTime;
	}

	/**
	 * 更新時間getter
	 * @return 更新時間
	 */
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * 更新時間setter
	 * @param 更新時間
	 */
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
}
