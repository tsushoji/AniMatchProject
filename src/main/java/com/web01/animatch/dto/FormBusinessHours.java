package com.web01.animatch.dto;

public class FormBusinessHours {
	private String businessHoursWeekdayNum;;
	private String businessHoursStartTime;
	private String businessHoursEndTime;
	private String businessHoursRemarks;
	private boolean businessHoursStartTimeErrFlg;
	private boolean businessHoursEndTimeErrFlg;
	private boolean businessHoursRemarksErrFlg;

	public String getBusinessHoursWeekdayNum() {
		return businessHoursWeekdayNum;
	}
	public void setBusinessHoursWeekdayNum(String businessHoursWeekdayNum) {
		this.businessHoursWeekdayNum = businessHoursWeekdayNum;
	}
	public String getBusinessHoursStartTime() {
		return businessHoursStartTime;
	}
	public void setBusinessHoursStartTime(String businessHoursStartTime) {
		this.businessHoursStartTime = businessHoursStartTime;
	}
	public String getBusinessHoursEndTime() {
		return businessHoursEndTime;
	}
	public void setBusinessHoursEndTime(String businessHoursEndTime) {
		this.businessHoursEndTime = businessHoursEndTime;
	}
	public String getBusinessHoursRemarks() {
		return businessHoursRemarks;
	}
	public void setBusinessHoursRemarks(String businessHoursRemarks) {
		this.businessHoursRemarks = businessHoursRemarks;
	}
	public boolean isBusinessHoursStartTimeErrFlg() {
		return businessHoursStartTimeErrFlg;
	}
	public void setBusinessHoursStartTimeErrFlg(boolean businessHoursStartTimeErrFlg) {
		this.businessHoursStartTimeErrFlg = businessHoursStartTimeErrFlg;
	}
	public boolean isBusinessHoursEndTimeErrFlg() {
		return businessHoursEndTimeErrFlg;
	}
	public void setBusinessHoursEndTimeErrFlg(boolean businessHoursEndTimeErrFlg) {
		this.businessHoursEndTimeErrFlg = businessHoursEndTimeErrFlg;
	}
	public boolean isBusinessHoursRemarksErrFlg() {
		return businessHoursRemarksErrFlg;
	}
	public void setBusinessHoursRemarksErrFlg(boolean businessHoursRemarksErrFlg) {
		this.businessHoursRemarksErrFlg = businessHoursRemarksErrFlg;
	}
}
