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

import com.web01.animatch.dao.AnimatchConnection;
import com.web01.animatch.dao.RegistDao;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.User;

public class RegistLogic {
	private String registType;
	private ResourceBundle resBundle;
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
	}

	public String getRegistType() {
		return registType;
	}

	public boolean isCanRegistFlg() {
		return canRegistFlg;
	}

	public void setProperties(HttpServletRequest request) {
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
				if(this.canRegistFlg) {
					this.canRegistFlg = registDao.registOwner(user);
					setAttributeAfterSuccessRegistOwner(request, user);
				}
				break;
			case "002":
				Store store = getParameterStoreDto(request, registDao);
				List<BusinessHours> businessHoursList = getParameterBusinessHoursDto(request);
				store.setBusinessHoursList(businessHoursList);
				user.setStore(store);
				if(this.canRegistFlg) {
					this.canRegistFlg = registDao.registTrimmer(user);
					setAttributeAfterSuccessRegistTrimmer(request, user);
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
		}
		user.setPassword(password);
		user.setSex(request.getParameter("radio-user-sex"));
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
		byte[] fileData = convertPartToByteArray(part);
		pet.setImage(fileData);
		pet.setNickName(request.getParameter("pet-name"));
		pet.setSex(request.getParameter("radio-pet-sex"));
		pet.setType(request.getParameter("pet-type"));
		pet.setWeight(Float.parseFloat(request.getParameter("pet-weight")));
		pet.setRemarks(request.getParameter("pet-remarks"));
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
		byte[] fileData = convertPartToByteArray(part);
		store.setImage(fileData);
		store.setStoreName(request.getParameter("store-name"));
		store.setEmployeesNumber(Integer.parseInt(request.getParameter("store-employees")));
		store.setCourseInfo(request.getParameter("course-info"));
		store.setCommitment(request.getParameter("commitment"));
		store.setDelFlg(1);
		LocalDateTime now = LocalDateTime.now();
		store.setInsertedTime(now);
		store.setUpdatedTime(now);

		return store;
	}

	private List<BusinessHours> getParameterBusinessHoursDto(HttpServletRequest request) throws IOException, ServletException, ParseException {
		List<BusinessHours> businessHoursList = new ArrayList<>();
		String businessHoursWeekAry[] = request.getParameter("business-hours").split(",");
		for(int i = 0; i < businessHoursWeekAry.length; i++) {
			BusinessHours businessHours = new BusinessHours();
			businessHours.setBusinessDay("00" + businessHoursWeekAry[i]);
			String[] startBusinessTimeAry = request.getParameter("business-hours-start-time-" + businessHoursWeekAry[i]).split(":");
			String[] endBusinessTimeAry = request.getParameter("business-hours-end-time-" + businessHoursWeekAry[i]).split(":");
			businessHours.setStartBusinessTime(LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1])));
			businessHours.setEndBusinessTime(LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1])));
			businessHours.setComplement(request.getParameter("business-hours-remarks-" + businessHoursWeekAry[i]));
			businessHours.setDelFlg(1);
			LocalDateTime now = LocalDateTime.now();
			businessHours.setInsertedTime(now);
			businessHours.setUpdatedTime(now);

			businessHoursList.add(businessHours);
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

	private void setAttributeAfterSuccessRegistOwner(HttpServletRequest request, User user) {
		Pet pet = user.getPet();
		request.setAttribute("registType", this.registType);
		request.setAttribute("user", user);
		request.setAttribute("pet", pet);
		String base64String = Base64.getEncoder().encodeToString(pet.getImage());
		request.setAttribute("petImage", base64String);

	}

	private void setAttributeAfterSuccessRegistTrimmer(HttpServletRequest request, User user) {
		Store store = user.getStore();
		request.setAttribute("registType", this.registType);
		request.setAttribute("user", user);
		request.setAttribute("store", store);
		String base64String = Base64.getEncoder().encodeToString(store.getImage());
		request.setAttribute("storeImage", base64String);
		request.setAttribute("businessHoursList", store.getBusinessHoursList());
	}
}
