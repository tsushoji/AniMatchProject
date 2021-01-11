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
import com.web01.animatch.dto.Pet;
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
		AnimatchConnection con = new AnimatchConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		try {
			User user = getParameterUserDto(request, registDao);
			switch(this.registType) {
			case "001":
				Pet pet = getParameterPetDto(request, registDao);
				user.setPetInfoId(pet);
				setAttributeRegistOwner(request, user);
				if(this.canRegistFlg) {
					this.canRegistFlg = registDao.registOwner(user);
				}
				break;
			case "002":
				Store store = getParameterStoreDto(request, registDao);
				List<BusinessHours> businessHoursList = getParameterBusinessHoursDto(request);
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

		return this.canRegistFlg;
	}

	private User getParameterUserDto(HttpServletRequest request, RegistDao registDao) throws SQLException, ParseException {
		User user = new User();
		user.setUserId(registDao.getMaxUserId() + 1);
		user.setUserName(request.getParameter("user-name"));
		String password = request.getParameter("password");
		String rePassword = request.getParameter("re-password");
		if(!password.equals(rePassword)) {
			canRegistFlg = false;
			this.msgKeyList.add("001");
		}
		user.setPassword(password);
		user.setSex(getParameterData(request.getParameter("radio-user-sex")));
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		user.setBirthday(dateFormat.parse(request.getParameter("birthday")));
		user.setPostalCode(request.getParameter("postal-code").replace("-", ""));
		String prefectures = this.resBundle.getString(PREFECTURES_KEY_INIT_STR + request.getParameter("prefectures"));
		String cities = request.getParameter("cities");
		String address = prefectures + cities;
		user.setStreetAddress(address);
		user.setEmailAddress(request.getParameter("email-address"));
		user.setTelephoneNumber(request.getParameter("telephone-number").replace("-", ""));
		user.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		user.setInsertedTime(now);
		user.setUpdatedTime(now);

		return user;
	}

	private Pet getParameterPetDto(HttpServletRequest request, RegistDao registDao) throws SQLException, IOException, ServletException {
		Pet pet = new Pet();
		pet.setPetId(registDao.getMaxPetId() + 1);
		Part part = request.getPart("file");
		if(part.getSize() > 0) {
			byte[] fileData = convertPartToByteArray(part);
			pet.setImage(fileData);
		}
		pet.setNickName(request.getParameter("pet-name"));
		pet.setSex(getParameterData(request.getParameter("radio-pet-sex")));
		pet.setType(getSelectParameterData(request.getParameter("pet-type")));
		String petWeight = request.getParameter("pet-weight");
		if(!StringUtil.isNullOrEmpty(petWeight)) {
			pet.setWeight(Float.parseFloat(petWeight));
		}
		pet.setRemarks(getParameterData(request.getParameter("pet-remarks")));
		pet.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		pet.setInsertedTime(now);
		pet.setUpdatedTime(now);

		return pet;
	}

	private Store getParameterStoreDto(HttpServletRequest request, RegistDao registDao) throws SQLException, IOException, ServletException {
		Store store = new Store();
		store.setStoreId(registDao.getMaxStoreId() + 1);
		Part part = request.getPart("file");
		if(part.getSize() > 0) {
			byte[] fileData = convertPartToByteArray(part);
			store.setImage(fileData);
		}
		store.setStoreName(request.getParameter("store-name"));
		String storeEmployees = request.getParameter("store-employees");
		if(!StringUtil.isNullOrEmpty(storeEmployees)) {
			store.setEmployeesNumber(Integer.parseInt(storeEmployees));
		}
		store.setCourseInfo(getParameterData(request.getParameter("course-info")));
		store.setCommitment(getParameterData(request.getParameter("commitment")));
		store.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		store.setInsertedTime(now);
		store.setUpdatedTime(now);

		return store;
	}

	private List<BusinessHours> getParameterBusinessHoursDto(HttpServletRequest request) throws IOException, ServletException, ParseException {
		List<BusinessHours> businessHoursList = null;
		String businessHoursWeek = request.getParameter("business-hours");
		if(!StringUtil.isNullOrEmpty(businessHoursWeek)) {
			businessHoursList = new ArrayList<>();
			String businessHoursWeekAry[] = businessHoursWeek.split(",");
			for(int i = 0; i < businessHoursWeekAry.length; i++) {
				BusinessHours businessHours = new BusinessHours();
				businessHours.setBusinessDay("00" + businessHoursWeekAry[i]);
				String startBusinessTime = request.getParameter("business-hours-start-time-" + businessHoursWeekAry[i]);
				String endBusinessTime = request.getParameter("business-hours-end-time-" + businessHoursWeekAry[i]);
				if(!StringUtil.isNullOrEmpty(startBusinessTime) && !StringUtil.isNullOrEmpty(endBusinessTime)) {
					String[] startBusinessTimeAry = startBusinessTime.split(":");
					String[] endBusinessTimeAry = endBusinessTime.split(":");
					businessHours.setStartBusinessTime(LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1])));
					businessHours.setEndBusinessTime(LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1])));
				}
				businessHours.setComplement(getParameterData(request.getParameter("business-hours-remarks-" + businessHoursWeekAry[i])));
				businessHours.setDelFlg(1);
				LocalDateTime now = LocalDateTime.now();
				businessHours.setInsertedTime(now);
				businessHours.setUpdatedTime(now);

				businessHoursList.add(businessHours);
			}
		}
		return businessHoursList;
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

	private String getParameterData(String param) {
		String rtnVal = null;
		if(!StringUtil.isNullOrEmpty(param)) {
			rtnVal = param;
		}
		return rtnVal;
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

	public void setMsgPropertiesKey(HttpServletRequest request) {
		request.setAttribute("msgKeyList", this.msgKeyList);
	}
}
