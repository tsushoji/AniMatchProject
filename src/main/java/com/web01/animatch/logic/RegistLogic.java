package com.web01.animatch.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.web01.animatch.Util.StringUtil;
import com.web01.animatch.dao.AnimatchConnection;
import com.web01.animatch.dao.RegistDao;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.FormBusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.RegistForm;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.User;

public class RegistLogic {
	private String registType;
	private ResourceBundle resBundle;
	private List<String> msgKeyList;
	private boolean canRegistFlg = true;
	private static final int BYTE_ARY_SIZE = 16777215;
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	private static final String REGIST_TYPE_KEY_INIT_STR = "regist.type.";
	private static final String PREFECTURES_KEY_INIT_STR = "prefectures.";
	private static final String PET_TYPE_KEY_INIT_STR = "pet.type.";
	private static final String WEEKDAY_KEY_INIT_STR = "weekday.";
	private static final String PROPERTIES_NAME = "animatch";

	public RegistLogic() {
		this.resBundle = ResourceBundle.getBundle(PROPERTIES_NAME);
	}

	public RegistLogic(String registType) {
		this.registType = registType;
		this.resBundle = ResourceBundle.getBundle(PROPERTIES_NAME);
		this.msgKeyList = new ArrayList<>();
	}

	public String getRegistType() {
		return registType;
	}

	public boolean isCanRegistFlg() {
		return canRegistFlg;
	}

	public void setInitPropertiesKey(HttpServletRequest request) {
		List<String> registTypeKeyList = new ArrayList<>();
		List<String> prefecturesKeyList = new ArrayList<>();
		List<String> petTypeKeyList = new ArrayList<>();
		List<String> weekdayKeyList = new ArrayList<>();
		Collections.list(this.resBundle.getKeys()).forEach(key -> {
		   if(key.startsWith(REGIST_TYPE_KEY_INIT_STR)){
			   registTypeKeyList.add(key);
		   }

	       if(key.startsWith(PREFECTURES_KEY_INIT_STR)){
	    	   prefecturesKeyList.add(key);
		   }

	       if(key.startsWith(PET_TYPE_KEY_INIT_STR)){
	    	   petTypeKeyList.add(key);
	       }

	       if(key.startsWith(WEEKDAY_KEY_INIT_STR)){
	    	   weekdayKeyList.add(key);
	       }
		});
	   registTypeKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));
	   prefecturesKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));
	   petTypeKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));
	   weekdayKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));

	   request.setAttribute("registTypeKeyList", registTypeKeyList);
	   request.setAttribute("prefecturesKeyList", prefecturesKeyList);
	   request.setAttribute("petTypeKeyList", petTypeKeyList);
	   request.setAttribute("weekdayKeyList", weekdayKeyList);
	}

	public boolean regist(HttpServletRequest request) {
		try {
			RegistForm registForm = getFormParameterDto(request);
			if(isValidate(registForm)) {
				registDao(request, registForm);
			}else {
				setAttributeKeyWithNotValidate(request, registForm);
			}
		} catch (SQLException | ParseException | IOException | ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return this.canRegistFlg;
	}

	private RegistForm getFormParameterDto(HttpServletRequest request) throws SQLException, ParseException, IOException, ServletException {
		RegistForm registForm = new RegistForm();
		registForm.setUserName(request.getParameter("user-name"));
		registForm.setPassword(request.getParameter("password"));
		registForm.setRePassword(request.getParameter("re-password"));
		registForm.setSex(request.getParameter("radio-user-sex"));
		registForm.setBirthday(request.getParameter("birthday"));
		registForm.setPostalCode(request.getParameter("postal-code"));
		registForm.setPrefectures(request.getParameter("prefectures"));
		registForm.setCities(request.getParameter("cities"));
		registForm.setEmailAddress(request.getParameter("email-address"));
		registForm.setTelephoneNumber(request.getParameter("telephone-number"));
		switch(this.registType) {
		case "001":
			registForm.setPetName(request.getParameter("pet-name"));
			registForm.setPetSex(request.getParameter("radio-pet-sex"));
			registForm.setPetType(request.getParameter("pet-type"));
			registForm.setPetWeight(request.getParameter("pet-weight"));
			registForm.setPetRemarks(request.getParameter("pet-remarks"));
			break;
		case "002":
			registForm.setStoreName(request.getParameter("store-name"));
			registForm.setFormBusinessHoursInputValue(request.getParameter("business-hours"));
			registForm.setFormBusinessHoursList(getFormParameterBusinessHoursDto(request));
			registForm.setStoreEmployees(request.getParameter("store-employees"));
			registForm.setCourseInfo(request.getParameter("course-info"));
			registForm.setCommitment(request.getParameter("commitment"));
			break;
		default:
			break;
		}

		return registForm;
	}

	private List<FormBusinessHours> getFormParameterBusinessHoursDto(HttpServletRequest request) throws IOException, ServletException, ParseException {
		List<FormBusinessHours> formBusinessHoursList = null;
		String formBusinessHoursWeek = request.getParameter("business-hours");
		if(!StringUtil.isNullOrEmpty(formBusinessHoursWeek)) {
			formBusinessHoursList = new ArrayList<>();
			String formBusinessHoursWeekAry[] = formBusinessHoursWeek.split(",");
			for(int i = 0; i < formBusinessHoursWeekAry.length; i++) {
				FormBusinessHours formBusinessHours = new FormBusinessHours();
				formBusinessHours.setBusinessHoursWeekdayNum(formBusinessHoursWeekAry[i]);
				formBusinessHours.setBusinessHoursStartTime(request.getParameter("business-hours-start-time-" + formBusinessHoursWeekAry[i]));
				formBusinessHours.setBusinessHoursEndTime(request.getParameter("business-hours-end-time-" + formBusinessHoursWeekAry[i]));
				formBusinessHours.setBusinessHoursRemarks(getParameterData(request.getParameter("business-hours-remarks-" + formBusinessHoursWeekAry[i])));

				formBusinessHoursList.add(formBusinessHours);
			}
		}
		return formBusinessHoursList;
	}

	private boolean isValidate(RegistForm registForm) {
		int errCount = 0;
		if(!registForm.getPassword().equals(registForm.getRePassword())) {
			this.msgKeyList.add("001");
			errCount++;
		}

		if(errCount > 0) {
			this.msgKeyList.add("002");
			canRegistFlg = false;
		}
		return this.canRegistFlg;
	}

	private void setAttributeKeyWithNotValidate(HttpServletRequest request, RegistForm registForm) {
		request.setAttribute("registForm", registForm);
		request.setAttribute("formBusinessHoursList", registForm.getFormBusinessHoursList());
		request.setAttribute("formRegistType", getRegistType());
		request.setAttribute("msgKeyList", this.msgKeyList);
	}

	private void registDao(HttpServletRequest request, RegistForm registForm) {
		AnimatchConnection con = new AnimatchConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		try {
			User user = getParameterUserDto(registForm, registDao);
			switch(this.registType) {
			case "001":
				Pet pet = getParameterPetDto(request, registForm, registDao);
				user.setPetInfoId(pet);
				setAttributeRegistOwner(request, user);
				if(this.canRegistFlg) {
					this.canRegistFlg = registDao.registOwner(user);
				}
				break;
			case "002":
				Store store = getParameterStoreDto(request, registForm, registDao);
				List<BusinessHours> businessHoursList = getParameterBusinessHoursDto(registForm);
				store.setBusinessHoursList(businessHoursList);
				user.setStore(store);
				setAttributeRegistTrimmer(request, user);
				if(this.canRegistFlg) {
					this.canRegistFlg = registDao.registTrimmer(user);
				}
				break;
			default:
				break;
			}
		} catch (SQLException | ParseException | IOException | ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			con.close();
		}
	}

	private User getParameterUserDto(RegistForm registForm, RegistDao registDao) throws SQLException, ParseException {
		User user = new User();
		user.setUserId(registDao.getMaxUserId() + 1);
		user.setUserName(registForm.getUserName());
		user.setPassword(registForm.getPassword());
		user.setSex(getParameterData(registForm.getSex()));
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		user.setBirthday(dateFormat.parse(registForm.getBirthday()));
		user.setPostalCode(registForm.getPostalCode().replace("-", ""));
		String prefectures = this.resBundle.getString(PREFECTURES_KEY_INIT_STR + registForm.getPrefectures());
		String cities = registForm.getCities();
		String address = prefectures + cities;
		user.setStreetAddress(address);
		user.setEmailAddress(registForm.getEmailAddress());
		user.setTelephoneNumber(registForm.getTelephoneNumber());
		user.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		user.setInsertedTime(now);
		user.setUpdatedTime(now);

		return user;
	}

	private Pet getParameterPetDto(HttpServletRequest request, RegistForm registForm, RegistDao registDao) throws SQLException, IOException, ServletException {
		Pet pet = new Pet();
		pet.setPetId(registDao.getMaxPetId() + 1);
		Part part = request.getPart("file");
		if(part.getSize() > 0) {
			byte[] fileData = convertPartToByteArray(part);
			pet.setImage(fileData);
		}
		pet.setNickName(registForm.getPetName());
		pet.setSex(getParameterData(registForm.getPetSex()));
		pet.setType(getSelectParameterData(registForm.getPetType()));
		String petWeight = registForm.getPetWeight();
		if(!StringUtil.isNullOrEmpty(petWeight)) {
			pet.setWeight(Float.parseFloat(petWeight));
		}
		pet.setRemarks(getParameterData(registForm.getPetRemarks()));
		pet.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		pet.setInsertedTime(now);
		pet.setUpdatedTime(now);

		return pet;
	}

	private Store getParameterStoreDto(HttpServletRequest request, RegistForm registForm, RegistDao registDao) throws SQLException, IOException, ServletException {
		Store store = new Store();
		store.setStoreId(registDao.getMaxStoreId() + 1);
		Part part = request.getPart("file");
		if(part.getSize() > 0) {
			byte[] fileData = convertPartToByteArray(part);
			store.setImage(fileData);
		}
		store.setStoreName(registForm.getStoreName());
		String storeEmployees = registForm.getStoreEmployees();
		if(!StringUtil.isNullOrEmpty(storeEmployees)) {
			store.setEmployeesNumber(Integer.parseInt(storeEmployees));
		}
		store.setCourseInfo(getParameterData(registForm.getCourseInfo()));
		store.setCommitment(getParameterData(registForm.getCommitment()));
		store.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		store.setInsertedTime(now);
		store.setUpdatedTime(now);

		return store;
	}

	private byte[] convertPartToByteArray(Part part) throws IOException {
		InputStream inputStream = part.getInputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[BYTE_ARY_SIZE];
		int readByteSize;
		while((readByteSize = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, readByteSize);
		}

		inputStream.close();
		buffer.close();

		return buffer.toByteArray();
	}

	private String getSelectParameterData(String param) {
		String rtnVal = null;
		if(!StringUtil.isNullOrEmpty(param)) {
			if(!param.equals("000")) {
				rtnVal = param;
			}
		}
		return rtnVal;
	}

	private List<BusinessHours> getParameterBusinessHoursDto(RegistForm registForm) throws IOException, ServletException, ParseException {
		List<BusinessHours> businessHoursList = new ArrayList<>();
		for(FormBusinessHours formBusinessHours: registForm.getFormBusinessHoursList()){
			BusinessHours businessHours = new BusinessHours();
			businessHours.setBusinessDay("00" + formBusinessHours.getBusinessHoursWeekdayNum());
			String[] startBusinessTimeAry = formBusinessHours.getBusinessHoursStartTime().split(":");
			String[] endBusinessTimeAry = formBusinessHours.getBusinessHoursEndTime().split(":");
			businessHours.setStartBusinessTime(LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1])));
			businessHours.setEndBusinessTime(LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1])));
			businessHours.setComplement(getParameterData(formBusinessHours.getBusinessHoursRemarks()));
			businessHours.setDelFlg(1);
			LocalDateTime now = LocalDateTime.now();
			businessHours.setInsertedTime(now);
			businessHours.setUpdatedTime(now);

			businessHoursList.add(businessHours);
		}
		return businessHoursList;
	}

	private String getParameterData(String param) {
		String rtnVal = null;
		if(!StringUtil.isNullOrEmpty(param)) {
			rtnVal = param;
		}
		return rtnVal;
	}

	private void setAttributeRegistOwner(HttpServletRequest request, User user) {
		Pet pet = user.getPet();
		request.setAttribute("registType", this.registType);
		request.setAttribute("user", user);
		request.setAttribute("pet", pet);
		byte[] petImage = pet.getImage();
		if(petImage != null) {
			String base64String = Base64.getEncoder().encodeToString(petImage);
			request.setAttribute("petImage", base64String);
		}
	}

	private void setAttributeRegistTrimmer(HttpServletRequest request, User user) {
		Store store = user.getStore();
		request.setAttribute("registType", this.registType);
		request.setAttribute("user", user);
		request.setAttribute("store", store);
		byte[] storeImage = store.getImage();
		if(storeImage != null) {
			String base64String = Base64.getEncoder().encodeToString(storeImage);
			request.setAttribute("storeImage", base64String);
		}
		request.setAttribute("businessHoursList", store.getBusinessHoursList());
	}

}
