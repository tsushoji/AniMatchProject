package com.web01.animatch.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.RegistDao;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.FormBusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.RegistForm;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.User;

/**
 * registロジッククラス
 * @author Tsuji
 * @version 1.0
 */
public class SignupService {

	//メンバー
	/**
	 * 登録区分
	 */
	private String registType;
	/**
	 * リソース・バンドルオブジェクト
	 */
	private ResourceBundle resBundle;
	/**
	 * メッセージマップ
	 */
	private Map<String, String> msgMap;
	/**
	 * メッセージサービスオブジェクト
	 */
	private MessageService messageService;
	/**
	 * 登録成功失敗
	 */
	private boolean canRegist = true;

	//定数
	/**
	 * 画像バイト配列サイズ
	 */
	private static final int BYTE_ARY_SIZE = 16777215;
	/**
	 * 日付フォーマット
	 */
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	/**
	 * 郵便番号フォーマット
	 */
	private static final String POSTAL_CODE_FORMAT = "^[0-9]{7}$";
	/**
	 * 市区町村フォーマット
	 */
	private static final String CITIES_FORMAT = "^.{1,}?[市区町村]$";
	/**
	 * 電話番号フォーマット
	 */
	private static final String TELEPHONE_NUMBER_FORMAT = "^[0-9]{10,11}$";
	/**
	 * メールアドレスフォーマット
	 */
	private static final String EMAIL_ADDRES_FORMAT = "^[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^$\\-\\|]+(\\.[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^$\\-\\|]+)*@[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^$\\-\\|]+(\\.[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^$\\-\\|]+)*$";
	/**
	 * 数値フォーマット
	 */
	private static final String NUMBER_FORMAT = "^[+]?([1-9]{1})([0-9]{0,})$";
	/**
	 * 小数フォーマット
	 */
	private static final String DECIMAL_FORMAT = "^[+]?([1-9]{1})([0-9]{0,37})[.]?([0-9]{0,})$";
	/**
	 * 先頭0の小数フォーマット
	 */
	private static final String INIT_ZERO_DECIMAL_FORMAT = "^[+]?([0]{1})[.]?([0-9]{0,})$";
	/**
	 * 0の小数フォーマット
	 */
	private static final String ZERO_DECIMAL_FORMAT = "^[+]?([0]{1})[.]?([0]{1,})$";
	/**
	 * 小数点以下0の整数フォーマット
	 */
	private static final String END_ZERO_DECIMAL_FORMAT = "^[+]?([1-9]{1})([0-9]{0,37})[.]?([0]{1,})$";
	/**
	 * 営業時間フォーマット
	 */
	private static final String BUSINESS_TIME_FORMAT = "^[0-9]{2}:[0-9]{2}$";
	/**
	 * プロパティ登録区分キー先頭文字列
	 */
	private static final String REGIST_TYPE_KEY_INIT_STR = "regist.type.";
	/**
	 * プロパティ都道府県キー先頭文字列
	 */
	private static final String PREFECTURES_KEY_INIT_STR = "prefectures.";
	/**
	 * プロパティ動物区分キー先頭文字列
	 */
	private static final String PET_TYPE_KEY_INIT_STR = "pet.type.";
	/**
	 * プロパティ曜日キー先頭文字列
	 */
	private static final String WEEKDAY_KEY_INIT_STR = "weekday.";
	/**
	 * プロパティファイル名
	 */
	private static final String PROPERTIES_NAME = "animatch";
	/**
	 * textarea開始タグ特殊文字
	 */
	private static final String TEXTAREA_INIT_PART_TAG = "<textarea";
	/**
	 * textarea終了タグ特殊文字
	 */
	private static final String TEXTAREA_END_TAG = "</textarea>";

	/**
	 * デフォルトコンストラクタ
	 */
	public SignupService() {
		this.resBundle = ResourceBundle.getBundle(PROPERTIES_NAME);
	}

	/**
	 * 引数付きコンストラクタ
	 * @param registType 登録区分
	 */
	public SignupService(String registType) {
		this.registType = registType;
		this.resBundle = ResourceBundle.getBundle(PROPERTIES_NAME);
		this.msgMap = new HashMap<>();
		this.messageService = new MessageService();
	}

	/**
	 * 登録区分getter
	 * @return 登録区分
	 */
	public String getRegistType() {
		return this.registType;
	}

	/**
	 * 登録成功失敗getter
	 * @return 登録成功失敗
	 */
	public boolean isCanRegistFlg() {
		return this.canRegist;
	}

	/**
	 * 初期プロパティ設定
	 * @param request リクエストオブジェクト
	 */
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

	/**
	 * 登録
	 * @param request リクエストオブジェクト
	 * @return 登録成功失敗
	 */
	public boolean regist(HttpServletRequest request) {
		try {
			RegistForm registForm = getFormParameterDto(request);
			if(isValidate(request, registForm)) {
				registDao(request, registForm);
			}else {
				setAttributeKeyWithCanNotValidate(request, registForm);
			}
		} catch (SQLException | ParseException | IOException | ServletException e) {
			e.printStackTrace();
		}

		return this.canRegist;
	}

	/**
	 * 登録フォームパラメータ取得
	 * @param request リクエストオブジェクト
	 * @return 登録フォームオブジェクト
	 */
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
			//飼い主の場合
			case "001":
				registForm.setPetName(request.getParameter("pet-name"));
				registForm.setPetSex(request.getParameter("radio-pet-sex"));
				registForm.setPetType(request.getParameter("pet-type"));
				registForm.setPetWeight(request.getParameter("pet-weight"));
				registForm.setPetRemarks(request.getParameter("pet-remarks"));
				break;
			//トリマーの場合
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

	/**
	 * 営業時間登録フォームパラメータ取得
	 * @param request リクエストオブジェクト
	 * @return 営業時間登録フォームリスト
	 */
	private List<FormBusinessHours> getFormParameterBusinessHoursDto(HttpServletRequest request) throws IOException, ServletException, ParseException {
		List<FormBusinessHours> formBusinessHoursList = null;
		String formBusinessHoursWeek = request.getParameter("business-hours");
		if(!StringUtils.isEmpty(formBusinessHoursWeek)) {
			formBusinessHoursList = new ArrayList<>();
			String formBusinessHoursWeekAry[] = formBusinessHoursWeek.split(",");
			for(int i = 0; i < formBusinessHoursWeekAry.length; i++) {
				FormBusinessHours formBusinessHours = new FormBusinessHours();
				formBusinessHours.setBusinessHoursWeekdayNum(formBusinessHoursWeekAry[i]);
				formBusinessHours.setBusinessHoursStartTime(request.getParameter("business-hours-start-time-" + formBusinessHoursWeekAry[i]));
				formBusinessHours.setBusinessHoursEndTime(request.getParameter("business-hours-end-time-" + formBusinessHoursWeekAry[i]));
				formBusinessHours.setBusinessHoursRemarks(request.getParameter("business-hours-remarks-" + formBusinessHoursWeekAry[i]));

				formBusinessHoursList.add(formBusinessHours);
			}
		}
		return formBusinessHoursList;
	}

	/**
	 * バリデーションチェック
	 * @param request リクエストオブジェクト
	 * @param registForm 登録フォームオブジェクト
	 * @return 登録成功失敗
	 */
	private boolean isValidate(HttpServletRequest request, RegistForm registForm) throws ParseException, IOException, ServletException {
		//ユーザーネーム10文字以下チェック
		if(registForm.getUserName().length() > 10) {
			this.msgMap.put("001", this.messageService.getMessage(MessageService.Type.ERROR, "001", "ユーザーネーム", "10文字以下"));
		}

		//パスワード欄と再入力欄が一致するかチェック
		if(!registForm.getPassword().equals(registForm.getRePassword())) {
			this.msgMap.put("002", this.messageService.getMessage(MessageService.Type.ERROR, "002", "再入力欄", "入力したパスワード"));
		}

		//パスワード20文字以下チェック
		if(registForm.getPassword().length() > 20) {
			this.msgMap.put("003", this.messageService.getMessage(MessageService.Type.ERROR, "001", "パスワード", "20文字以下"));
		}

		//郵便番号形式チェック
		if(!Pattern.matches(POSTAL_CODE_FORMAT, registForm.getPostalCode())) {
			this.msgMap.put("004", this.messageService.getMessage(MessageService.Type.ERROR, "003", "郵便番号"));
		}

		//都道府県選択チェック
		if(registForm.getPrefectures().equals("000")) {
			this.msgMap.put("005", this.messageService.getMessage(MessageService.Type.ERROR, "004", "都道府県"));
		}

		//市区町村形式チェック
		if(!Pattern.matches(CITIES_FORMAT, registForm.getCities())) {
			this.msgMap.put("006", this.messageService.getMessage(MessageService.Type.ERROR, "003", "市区町村"));
		}

		//市区町村9文字以下チェック
		if(registForm.getCities().length() > 9) {
			this.msgMap.put("007", this.messageService.getMessage(MessageService.Type.ERROR, "001", "市区町村", "9文字以下"));
		}

		//メールアドレス形式チェック
		if(!Pattern.matches(EMAIL_ADDRES_FORMAT, registForm.getEmailAddress())) {
			this.msgMap.put("008", this.messageService.getMessage(MessageService.Type.ERROR, "003", "メールアドレス"));
		}

		//メールアドレス254文字以下チェック
		if(registForm.getEmailAddress().length() > 254) {
			this.msgMap.put("009", this.messageService.getMessage(MessageService.Type.ERROR, "001", "メールアドレス", "254文字以下"));
		}

		//電話番号形式チェック
		if(!Pattern.matches(TELEPHONE_NUMBER_FORMAT, registForm.getTelephoneNumber())) {
			this.msgMap.put("010", this.messageService.getMessage(MessageService.Type.ERROR, "003", "電話番号"));
		}

		switch(this.registType) {
			//飼い主の場合
			case "001":
				//ペットニックネーム20文字以下チェック
				if(registForm.getPetName().length() > 20) {
					this.msgMap.put("011", this.messageService.getMessage(MessageService.Type.ERROR, "001", "ペットネーム", "20文字以下"));
				}

				//ペット体重数値チェック
				String registFormPetWeight = registForm.getPetWeight();
				//任意項目のため
				if(!registFormPetWeight.isEmpty()) {
					if(Pattern.matches(DECIMAL_FORMAT, registFormPetWeight) || Pattern.matches(INIT_ZERO_DECIMAL_FORMAT, registFormPetWeight)) {
						//ペット体重小数桁数チェック
						if(registFormPetWeight.equals("0") || Pattern.matches(ZERO_DECIMAL_FORMAT, registFormPetWeight)) {
							this.msgMap.put("012", this.messageService.getMessage(MessageService.Type.ERROR, "001", "ペット体重", "0以上の実数値"));
						}else {
							if(!Pattern.matches(END_ZERO_DECIMAL_FORMAT, registFormPetWeight)) {
								int pointIndex = registFormPetWeight.indexOf(".");
								if(pointIndex != -1) {
									BigDecimal registFormPetWeightVal = new BigDecimal(registFormPetWeight);
									if(registFormPetWeightVal.stripTrailingZeros().toString().substring(pointIndex + 1).length() > 2) {
										this.msgMap.put("013", this.messageService.getMessage(MessageService.Type.ERROR, "001", "ペット体重", "小数第2位まで"));
									}
								}
							}
						}
					}else {
						this.msgMap.put("012", this.messageService.getMessage(MessageService.Type.ERROR, "001", "ペット体重", "0以上の実数値"));
					}
				}

				String registFormPetRemarks = registForm.getPetRemarks();
				//備考200文字以下チェック
				if(registFormPetRemarks.length() > 200) {
					this.msgMap.put("014", this.messageService.getMessage(MessageService.Type.ERROR, "001", "備考", "200文字以下"));
				}

				//備考XSS対策
				if(registFormPetRemarks.contains(TEXTAREA_INIT_PART_TAG) || registFormPetRemarks.contains(TEXTAREA_END_TAG)) {
					this.msgMap.put("023", this.messageService.getMessage(MessageService.Type.ERROR, "007"));
				}

				break;

			//トリマーの場合
			case "002":
				//店名50文字以下チェック
				if(registForm.getStoreName().length() > 50) {
					this.msgMap.put("015", this.messageService.getMessage(MessageService.Type.ERROR, "001", "店名", "50文字以下"));
				}

				int errorTimeCount = 0;
				int errorRemarksCount = 0;
				int errorXSSRemarksCount = 0;
				//営業時間チェック
				List<FormBusinessHours> formBusinessHoursList = registForm.getFormBusinessHoursList();
				//Multipickerで選択されている場合
				if(formBusinessHoursList != null) {
					for(FormBusinessHours formBusinessHours: formBusinessHoursList){
						boolean judgeTimeFlg = true;
						//営業時間形式チェック
						String formBusinessHoursStartTime = formBusinessHours.getBusinessHoursStartTime();
						if(!formBusinessHoursStartTime.isEmpty()) {
							if(!Pattern.matches(BUSINESS_TIME_FORMAT, formBusinessHoursStartTime)) {
								formBusinessHours.setIsErrBusinessHoursStartTime("1");
								judgeTimeFlg = false;
								if(errorTimeCount == 0) {
									this.msgMap.put("016", this.messageService.getMessage(MessageService.Type.ERROR, "005", "営業時間"));
									errorTimeCount++;
								}
							}
						}

						String formBusinessHoursEndTime = formBusinessHours.getBusinessHoursEndTime();
						if(!formBusinessHoursEndTime.isEmpty()) {
							if(!Pattern.matches(BUSINESS_TIME_FORMAT, formBusinessHoursEndTime)) {
								formBusinessHours.setIsErrBusinessHoursEndTime("1");
								judgeTimeFlg = false;
								if(errorTimeCount == 0) {
									this.msgMap.put("016", this.messageService.getMessage(MessageService.Type.ERROR, "005", "営業時間"));
									errorTimeCount++;
								}
							}
						}

						if(judgeTimeFlg) {
							if(!formBusinessHoursStartTime.isEmpty() && !formBusinessHoursEndTime.isEmpty()) {
								String[] startBusinessTimeAry = formBusinessHoursStartTime.split(":");
								String[] endBusinessTimeAry = formBusinessHoursEndTime.split(":");
								if(startBusinessTimeAry[0].equals(endBusinessTimeAry[0])) {
									if(Integer.parseInt(startBusinessTimeAry[1]) >= Integer.parseInt(endBusinessTimeAry[1])) {
										formBusinessHours.setIsErrBusinessHoursStartTime("1");
										if(errorTimeCount == 0) {
											this.msgMap.put("016", this.messageService.getMessage(MessageService.Type.ERROR, "005", "営業時間"));
											errorTimeCount++;
										}
									}
								}else {
									if(Integer.parseInt(startBusinessTimeAry[0]) > Integer.parseInt(endBusinessTimeAry[0])) {
										formBusinessHours.setIsErrBusinessHoursStartTime("1");
										if(errorTimeCount == 0) {
											this.msgMap.put("016", this.messageService.getMessage(MessageService.Type.ERROR, "005", "営業時間"));
											errorTimeCount++;
										}
									}
								}
							}else if(formBusinessHoursStartTime.isEmpty() && !formBusinessHoursEndTime.isEmpty()) {
								formBusinessHours.setIsErrBusinessHoursStartTime("1");
								if(errorTimeCount == 0) {
									this.msgMap.put("016", this.messageService.getMessage(MessageService.Type.ERROR, "005", "営業時間"));
									errorTimeCount++;
								}
							}else if(!formBusinessHoursStartTime.isEmpty() && formBusinessHoursEndTime.isEmpty()) {
								formBusinessHours.setIsErrBusinessHoursStartTime("1");
								if(errorTimeCount == 0) {
									this.msgMap.put("016", this.messageService.getMessage(MessageService.Type.ERROR, "005", "営業時間"));
									errorTimeCount++;
								}
							}
						}

						String formBusinessHoursRemarks = formBusinessHours.getBusinessHoursRemarks();
						//補足100文字以下チェック
						if(formBusinessHoursRemarks.length() > 100) {
							formBusinessHours.setIsErrLengthBusinessHoursRemarks("1");
							if(errorRemarksCount == 0) {
								this.msgMap.put("017", this.messageService.getMessage(MessageService.Type.ERROR, "001", "営業時間補足", "100文字以下"));
								errorRemarksCount++;
							}
						}


						//補足XSS対策
						if(formBusinessHoursRemarks.contains(TEXTAREA_INIT_PART_TAG) || formBusinessHoursRemarks.contains(TEXTAREA_END_TAG)) {
							formBusinessHours.setIsErrXSSBusinessHoursRemarks("1");
							if(errorXSSRemarksCount == 0) {
								this.msgMap.put("024", this.messageService.getMessage(MessageService.Type.ERROR, "007"));
								errorXSSRemarksCount++;
							}
						}
					}
				}

				String registFormStoreEmployees = registForm.getStoreEmployees();
				//任意項目のため
				if(!registFormStoreEmployees.isEmpty()) {
					//従業員数数値チェック
					if(Pattern.matches(NUMBER_FORMAT, registFormStoreEmployees)) {
						//従業員数桁数チェック
						if(registFormStoreEmployees.length() > 8) {
							this.msgMap.put("019", this.messageService.getMessage(MessageService.Type.ERROR, "001", "従業員数", "8桁以下"));
						}
					}else {
						this.msgMap.put("018", this.messageService.getMessage(MessageService.Type.ERROR, "001", "従業員数", "数値"));
					}
				}

				String registFormCourseInfo = registForm.getCourseInfo();
				//コース200文字以下チェック
				if(registFormCourseInfo.length() > 200) {
					this.msgMap.put("020", this.messageService.getMessage(MessageService.Type.ERROR, "001", "コース", "200文字以下"));
				}

				//コースXSS対策
				if(registFormCourseInfo.contains(TEXTAREA_INIT_PART_TAG) || registFormCourseInfo.contains(TEXTAREA_END_TAG)) {
					this.msgMap.put("025", this.messageService.getMessage(MessageService.Type.ERROR, "007"));
				}

				String registFormCommitment = registForm.getCommitment();
				//こだわり200文字以下チェック
				if(registFormCommitment.length() > 200) {
					this.msgMap.put("021", this.messageService.getMessage(MessageService.Type.ERROR, "001", "こだわり", "200文字以下"));
				}

				//こだわりXSS対策
				if(registFormCommitment.contains(TEXTAREA_INIT_PART_TAG) || registFormCommitment.contains(TEXTAREA_END_TAG)) {
					this.msgMap.put("026", this.messageService.getMessage(MessageService.Type.ERROR, "007"));
				}

				break;

			default:
				break;
		}

		//バリデーションエラーがある場合
		if(this.msgMap.size() > 0) {
			//画像添付している場合
			if(request.getPart("file").getSize() > 0) {
				this.msgMap.put("022", this.messageService.getMessage(MessageService.Type.ERROR, "006", "画像"));
			}
			this.canRegist = false;
		}
		return this.canRegist;
	}

	/**
	 * バリデーションエラー属性設定
	 * @param request リクエストオブジェクト
	 * @param registForm 登録フォームオブジェクト
	 */
	private void setAttributeKeyWithCanNotValidate(HttpServletRequest request, RegistForm registForm) {
		request.setAttribute("registForm", registForm);
		request.setAttribute("formBusinessHoursList", registForm.getFormBusinessHoursList());
		request.setAttribute("formRegistType", getRegistType());
		request.setAttribute("msgMap", this.msgMap);
	}

	/**
	 * 登録DB処理
	 * @param request リクエストオブジェクト
	 * @param registForm 登録フォームオブジェクト
	 */
	private void registDao(HttpServletRequest request, RegistForm registForm) {
		DBConnection con = new DBConnection();
		RegistDao registDao = new RegistDao(con.getConnection());
		try {
			User user = getParameterUserDto(registForm, registDao);
			switch(this.registType) {
				//飼い主の場合
				case "001":
					Pet pet = getParameterPetDto(request, registForm, registDao);
					user.setPet(pet);
					setAttributeRegistOwner(request, user);
					if(this.canRegist) {
						//DB登録処理前に登録成功失敗リセット
						this.canRegist = false;
						this.canRegist = registDao.registOwner(user);
					}
					break;

				//トリマーの場合
				case "002":
					Store store = getParameterStoreDto(request, registForm, registDao);
					List<BusinessHours> businessHoursList = getParameterBusinessHoursDto(registForm);
					store.setBusinessHoursList(businessHoursList);
					user.setStore(store);
					setAttributeRegistTrimmer(request, user);
					if(this.canRegist) {
						//DB登録処理前に登録成功失敗リセット
						this.canRegist = false;
						this.canRegist = registDao.registTrimmer(user);
					}
					break;

				default:
					break;
			}
		} catch (SQLException | ParseException | IOException | ServletException e) {
			e.printStackTrace();
		}finally {
			con.close();
		}
	}

	/**
	 * パラメータセットユーザオブジェクト取得
	 * @param registForm 登録フォームオブジェクト
	 * @param RegistDao 登録DB処理オブジェクト
	 * @return ユーザオブジェクト
	 */
	private User getParameterUserDto(RegistForm registForm, RegistDao registDao) throws SQLException, ParseException {
		User user = new User();

		user.setUserName(registForm.getUserName());
		user.setPassword(registForm.getPassword());
		user.setSex(getParameterData(registForm.getSex()));
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		user.setBirthday(dateFormat.parse(registForm.getBirthday()));
		user.setPostalCode(registForm.getPostalCode());
		String prefectures = this.resBundle.getString(PREFECTURES_KEY_INIT_STR + registForm.getPrefectures());
		String cities = registForm.getCities();
		String address = prefectures + cities;
		user.setStreetAddress(address);
		user.setEmailAddress(registForm.getEmailAddress());
		user.setTelephoneNumber(registForm.getTelephoneNumber());
		user.setIsDeleted(1);
		LocalDateTime now = LocalDateTime.now();
		user.setInsertedTime(now);
		user.setUpdatedTime(now);

		return user;
	}

	/**
	 * パラメータセットペットオブジェクト取得
	 * @param request リクエストオブジェクト
	 * @param registForm 登録フォームオブジェクト
	 * @param request 登録DB処理オブジェクト
	 * @return ペットオブジェクト
	 */
	private Pet getParameterPetDto(HttpServletRequest request, RegistForm registForm, RegistDao registDao) throws SQLException, IOException, ServletException {
		Pet pet = new Pet();

		Part part = request.getPart("file");
		if(part.getSize() > 0) {
			byte[] fileData = convertPartToByteArray(part);
			pet.setImage(fileData);
		}
		pet.setNickName(registForm.getPetName());
		pet.setSex(getParameterData(registForm.getPetSex()));
		pet.setType(getSelectParameterData(registForm.getPetType()));
		String petWeight = registForm.getPetWeight();
		if(!StringUtils.isEmpty(petWeight)) {
			pet.setWeight(Float.parseFloat(petWeight));
		}
		pet.setRemarks(getParameterData(registForm.getPetRemarks()));
		pet.setIsDeleted(1);
		LocalDateTime now = LocalDateTime.now();
		pet.setInsertedTime(now);
		pet.setUpdatedTime(now);

		return pet;
	}

	/**
	 * パラメータセット店舗オブジェクト取得
	 * @param request リクエストオブジェクト
	 * @param registForm 登録フォームオブジェクト
	 * @param request 登録DB処理オブジェクト
	 * @return 店舗オブジェクト
	 */
	private Store getParameterStoreDto(HttpServletRequest request, RegistForm registForm, RegistDao registDao) throws SQLException, IOException, ServletException {
		Store store = new Store();

		Part part = request.getPart("file");
		if(part.getSize() > 0) {
			byte[] fileData = convertPartToByteArray(part);
			store.setImage(fileData);
		}
		store.setStoreName(registForm.getStoreName());
		String storeEmployees = registForm.getStoreEmployees();
		if(!StringUtils.isEmpty(storeEmployees)) {
			store.setEmployeesNumber(Integer.parseInt(storeEmployees));
		}
		store.setCourseInfo(getParameterData(registForm.getCourseInfo()));
		store.setCommitment(getParameterData(registForm.getCommitment()));
		store.setIsDeleted(1);
		LocalDateTime now = LocalDateTime.now();
		store.setInsertedTime(now);
		store.setUpdatedTime(now);

		return store;
	}

	/**
	 * 受け取ったパートからバイト配列に変換
	 * @param part パート
	 * @return バイト配列
	 */
	private byte[] convertPartToByteArray(Part part) throws IOException  {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream inputStream = part.getInputStream();

		try{
			byte[] data = new byte[BYTE_ARY_SIZE];
			int readByteSize;
			while((readByteSize = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, readByteSize);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			buffer.close();
			inputStream.close();
		}

		return buffer.toByteArray();
	}

	/**
	 * 選択ボックスパラメータデータ取得
	 * @param param 文字列引数
	 * @return 文字列引数がnullまたは空であるかつ選択されていない場合、null<br>
	 * そうでない場合、文字列引数
	 */
	private String getSelectParameterData(String param) {
		String rtnVal = null;
		if(!StringUtils.isEmpty(param)) {
			if(!param.equals("000")) {
				rtnVal = param;
			}
		}
		return rtnVal;
	}

	/**
	 * パラメータセット営業時間オブジェクト取得
	 * @param registForm 登録フォームオブジェクト
	 * @return 登録営業時間リスト
	 */
	private List<BusinessHours> getParameterBusinessHoursDto(RegistForm registForm) throws IOException, ServletException, ParseException {
		List<BusinessHours> businessHoursList = null;
		List<FormBusinessHours> formBusinessHoursList = registForm.getFormBusinessHoursList();
		if(formBusinessHoursList != null) {
			businessHoursList = new ArrayList<>();
			for(FormBusinessHours formBusinessHours: formBusinessHoursList){
				BusinessHours businessHours = new BusinessHours();
				businessHours.setBusinessDay("00" + formBusinessHours.getBusinessHoursWeekdayNum());
				String[] startBusinessTimeAry = formBusinessHours.getBusinessHoursStartTime().split(":");
				String[] endBusinessTimeAry = formBusinessHours.getBusinessHoursEndTime().split(":");
				businessHours.setStartBusinessTime(LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1])));
				businessHours.setEndBusinessTime(LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1])));
				businessHours.setComplement(getParameterData(formBusinessHours.getBusinessHoursRemarks()));
				businessHours.setIsDeleted(1);
				LocalDateTime now = LocalDateTime.now();
				businessHours.setInsertedTime(now);
				businessHours.setUpdatedTime(now);

				businessHoursList.add(businessHours);
			}
		}
		return businessHoursList;
	}

	/**
	 * パラメータデータ取得
	 * @param param 文字列引数
	 * @return 文字列引数がnullまたは空である場合、null<br>
	 * そうでない場合、文字列引数
	 */
	private String getParameterData(String param) {
		String rtnVal = null;
		if(!StringUtils.isEmpty(param)) {
			rtnVal = param;
		}
		return rtnVal;
	}

	/**
	 * 飼い主用登録オブジェクト属性設定
	 * @param request リクエストオブジェクト
	 * @param user ユーザオブジェクト
	 */
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

	/**
	 * トリマー用登録オブジェクト属性設定
	 * @param request リクエストオブジェクト
	 * @param user ユーザオブジェクト
	 */
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