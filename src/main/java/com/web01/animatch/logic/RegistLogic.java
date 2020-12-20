package com.web01.animatch.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.joda.time.DateTime;

import com.web01.animatch.dao.AnimatchConnection;
import com.web01.animatch.dao.RegistDao;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.User;

public class RegistLogic {
	private String registType;
	private boolean canRegistFlg = true;
	private static final int BYTE_ARY_SIZE = 16777215;
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	private static final String TIME_FORMAT = "HH:mm:00";

	public RegistLogic(String registType) {
		this.registType = registType;
	}

	public String getRegistType() {
		return registType;
	}

	public boolean isCanRegistFlg() {
		return canRegistFlg;
	}

	public boolean regist(HttpServletRequest request) throws ParseException, SQLException, IOException, ServletException {
		User user = getParameterUserDto(request);
		AnimatchConnection con = new AnimatchConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		switch(this.registType) {
		case "001":
			Pet pet = getParameterPetDto(request);
			user.setPetInfoId(pet);
			if(this.canRegistFlg) {
				this.canRegistFlg = registDao.registOwner(user);
			}
			break;
		case "002":
			Store store = getParameterStoreDto(request);
			List<BusinessHours> businessHoursList = getParameterBusinessHoursDto(request);
			store.setBusinessHoursList(businessHoursList);
			user.setStore(store);
			if(this.canRegistFlg) {
				this.canRegistFlg = registDao.registTrimmer(user);
			}
			break;
		default:
			break;
		}
		con.close();
		return this.canRegistFlg;
	}

	private User getParameterUserDto(HttpServletRequest request) {
		User user = new User();
		AnimatchConnection con = new AnimatchConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		try {
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
			String prefectures = request.getParameter("prefectures");
			String cities = request.getParameter("cities");
			String address = prefectures + cities;
			user.setStreetAddress(address);
			user.setEmailAddress(request.getParameter("email-address"));
			user.setTelephoneNumber(request.getParameter("telephone-number").replace("-", ""));
			user.setDelFlg(1);
			DateTime now = DateTime.now();
			user.setInsertedTime(now);
			user.setUpdatedTime(now);
		} catch (SQLException | ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			con.close();
		}

		return user;
	}

	private Pet getParameterPetDto(HttpServletRequest request) {
		Pet pet = new Pet();
		AnimatchConnection con = new AnimatchConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		try {
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
			DateTime now = DateTime.now();
			pet.setInsertedTime(now);
			pet.setUpdatedTime(now);
		} catch (SQLException | IOException | ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			con.close();
		}

		return pet;
	}

	private Store getParameterStoreDto(HttpServletRequest request) {
		Store store = new Store();
		AnimatchConnection con = new AnimatchConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		try {
			store.setStoreId(registDao.getMaxStoreId() + 1);
			Part part = request.getPart("file");
			byte[] fileData = convertPartToByteArray(part);
			store.setImage(fileData);
			store.setStoreName(request.getParameter("store-name"));
			store.setEmployeesNumber(Integer.parseInt(request.getParameter("store-employees")));
			store.setCourseInfo(request.getParameter("course-info"));
			store.setCommitment(request.getParameter("commitment"));
			store.setDelFlg(1);
			DateTime now = DateTime.now();
			store.setInsertedTime(now);
			store.setUpdatedTime(now);
		} catch (SQLException | IOException | ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			con.close();
		}

		return store;
	}

	private List<BusinessHours> getParameterBusinessHoursDto(HttpServletRequest request) throws IOException, ServletException, ParseException {
		List<BusinessHours> businessHoursList = new ArrayList<>();
		String businessHoursWeekAry[] = request.getParameter("business-hours").split(",");
		SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
		for(int i = 0; i < businessHoursWeekAry.length; i++) {
			BusinessHours businessHours = new BusinessHours();
			businessHours.setBusinessDay("00" + businessHoursWeekAry[i]);
			businessHours.setStartBusinessTime(timeFormat.parse(request.getParameter("business-hours-start-time-" + businessHoursWeekAry[i])));
			businessHours.setEndBusinessTime(timeFormat.parse(request.getParameter("business-hours-end-time-" + businessHoursWeekAry[i])));
			businessHours.setComplement(request.getParameter("business-hours-remarks-" + businessHoursWeekAry[i]));
			businessHours.setDelFlg(1);
			DateTime now = DateTime.now();
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
}
