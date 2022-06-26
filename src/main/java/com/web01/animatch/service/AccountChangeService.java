package com.web01.animatch.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dao.CreateDao;
import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.DeleteDao;
import com.web01.animatch.dao.ReadDao;
import com.web01.animatch.dao.UpdateDao;
import com.web01.animatch.dto.AccountChangeForm;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.ChangeBusinessHoursInfo;
import com.web01.animatch.dto.FormBusinessHours;
import com.web01.animatch.dto.OwnerInfo;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.RegistedAccountForm;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.TrimmerInfo;
import com.web01.animatch.dto.TrimmerInfoBusinessHours;
import com.web01.animatch.dto.User;
import com.web01.animatch.dto.UserSession;
import com.web01.animatch.exception.MyException;

/**
 * アカウント変更サービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class AccountChangeService extends BaseService {

 //メンバー
 /**
  * 登録区分
  */
 private UserType registType;
 /**
  * プロパティーサービスオブジェクト
  */
 private PropertiesService propertiesService;
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

 /**
  * ペット体重を更新するか
  */
 private boolean isUpdatePetWeight = false;
 /**
  * 補足を更新するか
  */
 private boolean isUpdateRemarks = false;

 /**
  * 従業員数を更新するか
  */
 private boolean isUpdateEmployeesNum = false;
 /**
  * コース情報を更新するか
  */
 private boolean isUpdateCourseInfo = false;
 /**
  * こだわりポイントを更新するか
  */
 private boolean isUpdateCommitment = false;

 /**
  * 営業時間補足を更新するか
  */
 private Map<String, Boolean> isUpdateBusinessHoursComplementMap = new HashMap<>();

 //定数
 /**
  * IDデフォルト値
  */
 private static final int DEFAULT_ID = 1000000000;
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
  * 都道府県フォーマット
  */
 private static final String PREFECTURE_FORMAT = "^.{1,}?[都道府県]";
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
 public AccountChangeService() {
  this.propertiesService = new PropertiesService();
  this.messageService = new MessageService();
 }

 /**
  * コンストラクタ
  */
 public AccountChangeService(String registedType) {
  this.propertiesService = new PropertiesService();
  registType = UserType.getEnumFromId(registedType);
  this.messageService = new MessageService();

  this.msgMap = new HashMap<>();
 }

 /**
  * 登録区分ID取得
  * @return 登録区分
  */
 private String getRegistTypeId() {
  String registType = null;
  switch (this.registType) {
  //飼い主の場合
  case OWNER:
   registType = "001";
   break;
  //トリマーの場合
  case TRIMMER:
   registType = "002";
   break;
  default:
   break;
  }
  return registType;
 }

 /**
  * 登録成功失敗getter
  * @return 登録成功失敗
  */
 public boolean isCanRegistFlg() {
  return this.canRegist;
 }

 /**
  * 初回表示設定
  * @param request リクエストオブジェクト
  */
 public void setFirstDisplay(HttpServletRequest request) {
  setInitPropertiesKey(request);
  UserSession userSession = new UserSession();
  SessionService sessionService = new SessionService((HttpServletRequest)request);
  userSession = (UserSession)sessionService.getBindingKeySessionValue(AuthService.USER_SESSION_KEY_NAME);
  Integer userId = userSession.getUserId();
  Integer petId = userSession.getPetId();
  Integer storeId = userSession.getStoreId();
  setRegistedInfoAttributeKey(request, userId, petId, storeId);
 }

 /**
  * 初期プロパティ設定
  * @param request リクエストオブジェクト
  */
 private void setInitPropertiesKey(HttpServletRequest request) {
  Map<String, String> registTypeMap = new HashMap<>();
  Map<String, String> prefecturesMap = new HashMap<>();
  Map<String, String> petTypeMap = new HashMap<>();
  Map<String, String> weekdayMap = new HashMap<>();
  Map<String, String> humanSexMap = new HashMap<>();
  Map<String, String> petSexMap = new HashMap<>();
  registTypeMap = this.propertiesService.getValues(PropertiesService.REGIST_TYPE_KEY_INIT_STR);
  prefecturesMap = this.propertiesService.getValues(PropertiesService.PREFECTURES_KEY_INIT_STR);
  petTypeMap = this.propertiesService.getValues(PropertiesService.PET_TYPE_KEY_INIT_STR);
  weekdayMap = this.propertiesService.getValues(PropertiesService.WEEKDAY_KEY_INIT_STR);
  humanSexMap = this.propertiesService.getValues(PropertiesService.HUMAN_SEX_KEY_INIT_STR);
  petSexMap = this.propertiesService.getValues(PropertiesService.PET_SEX_KEY_INIT_STR);

  request.setAttribute("registTypeMap", registTypeMap);
  request.setAttribute("prefecturesMap", prefecturesMap);
  request.setAttribute("petTypeMap", petTypeMap);
  request.setAttribute("weekdayMap", weekdayMap);
  request.setAttribute("humanSexMap", humanSexMap);
  request.setAttribute("petSexMap", petSexMap);
 }

 /**
  * 登録情報属性設定
  * @param request リクエストオブジェクト
  * @param userId ユーザID
  * @param petId ペットID
  * @param storeId 店舗ID
  */
 private void setRegistedInfoAttributeKey(HttpServletRequest request, int userId, int petId, int storeId) {
  DBConnection con = new DBConnection();
  ReadDao readDao = new ReadDao(con.getConnection());
  AccountChangeForm accountChangeForm = null;

  try {
   if(petId != DEFAULT_ID && storeId == DEFAULT_ID) {
    this.registType = UserType.OWNER;
    OwnerInfo ownerInfo = readDao.findOwnerInfoByUserId(userId);
    accountChangeForm = setAccountChangeInfoWithOwnerInfo(request, ownerInfo);
   }else if(petId == DEFAULT_ID && storeId != DEFAULT_ID) {
    this.registType = UserType.TRIMMER;
    TrimmerInfo trimmerInfo = readDao.findTrimmerInfoByUserId(userId);
    accountChangeForm = setAccountChangeInfoWithTrimmerInfo(request,trimmerInfo);
   }else {
    throw new MyException("msg.error.011",
     this.messageService.getMessage(MessageService.MessageType.ERROR, "011", "アカウント情報"));
   }
  }catch(SQLException e) {
   e.printStackTrace();
  }catch(MyException e) {
   e.printStackTrace();
  }catch(Exception e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

  String registTypeId = this.getRegistTypeId();
  String registTypeName = this.propertiesService
   .getValue(PropertiesService.REGIST_TYPE_KEY_INIT_STR + registTypeId);
  request.setAttribute("formRegistType", registTypeId);
  request.setAttribute("registTypeName", registTypeName);

  request.setAttribute("accountChangeForm", accountChangeForm);
  request.setAttribute("formBusinessHoursList", accountChangeForm.getFormBusinessHoursList());
  request.setAttribute("msgMap", this.msgMap);
 }

 /**
  * 飼い主変更情報設定
  * @param request リクエストオブジェクト
  * @param ownerInfo 飼い主情報オブジェクト
  * @return 飼い主情報変更フォームオブジェクト
  */
 private AccountChangeForm setAccountChangeInfoWithOwnerInfo(HttpServletRequest request, OwnerInfo ownerInfo)
   throws MyException {
  SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
  Pattern preficturePattern = Pattern.compile(PREFECTURE_FORMAT);
  AccountChangeForm accountChangeForm = new AccountChangeForm();
  RegistedAccountForm registedAccountForm = new RegistedAccountForm();
  String userName = ownerInfo.getUserName();
  accountChangeForm.setUserName(userName);
  registedAccountForm.setUserName(userName);
  String password = ownerInfo.getPassword();
  accountChangeForm.setPassword(password);
  registedAccountForm.setPassword(password);
  String sex = ownerInfo.getSex();
  accountChangeForm.setSex(sex);
  registedAccountForm.setSex(sex);
  String birthday = dateFormat.format(ownerInfo.getBirthday());
  accountChangeForm.setBirthday(birthday);
  registedAccountForm.setBirthday(birthday);
  String postalCode = ownerInfo.getPostalCode();
  accountChangeForm.setPostalCode(postalCode);
  registedAccountForm.setPostalCode(postalCode);
  String streetAddres = ownerInfo.getStreetAddress();
  Matcher macher = preficturePattern.matcher(streetAddres);
  boolean isExistPrefecture = macher.find();
  if(isExistPrefecture == false) {
   throw new MyException("msg.error.011",
    this.messageService.getMessage(MessageService.MessageType.ERROR, "011", "アカウント情報"));
  }
  String prefecture = macher.group();
  String prefectureKey = this.propertiesService.getKey(prefecture);
  if(prefectureKey == null) {
   throw new MyException("msg.error.011",
    this.messageService.getMessage(MessageService.MessageType.ERROR, "011", "アカウント情報"));
  }else {
   prefectureKey = prefectureKey.replace(PropertiesService.PREFECTURES_KEY_INIT_STR,"");
  }
  accountChangeForm.setPrefectures(prefectureKey);
  registedAccountForm.setPrefectures(prefectureKey);
  String cities = streetAddres.substring(macher.end());
  accountChangeForm.setCities(cities);
  registedAccountForm.setCities(cities);
  String emailAddres = ownerInfo.getEmailAddress();
  accountChangeForm.setEmailAddress(emailAddres);
  registedAccountForm.setEmailAddress(emailAddres);
  String telephoneNum = ownerInfo.getTelephoneNumber();
  accountChangeForm.setTelephoneNumber(telephoneNum);
  registedAccountForm.setTelephoneNumber(telephoneNum);
  String petNickName = ownerInfo.getPetNickName();
  accountChangeForm.setPetName(petNickName);
  registedAccountForm.setPetName(petNickName);
  String petSex = ownerInfo.getPetSex();
  accountChangeForm.setPetSex(petSex);
  registedAccountForm.setPetSex(petSex);
  String petType = ownerInfo.getPetType();
  accountChangeForm.setPetType(petType);
  registedAccountForm.setPetType(petType);
  Float tempPetWeight = ownerInfo.getPetWeight();
  String petWeight = tempPetWeight == null?null:String.valueOf(tempPetWeight);
  accountChangeForm.setPetWeight(petWeight);
  registedAccountForm.setPetWeight(petWeight);
  String petRemarks = ownerInfo.getPetRemarks();
  accountChangeForm.setPetRemarks(petRemarks);
  registedAccountForm.setPetRemarks(petRemarks);

  SessionService sessionService = new SessionService((HttpServletRequest)request);
  UserSession userSession = (UserSession)sessionService.getBindingKeySessionValue(AuthService.USER_SESSION_KEY_NAME);
  if(userSession == null) {
   throw new MyException("msg.error.009",
    this.messageService.getMessage(MessageService.MessageType.ERROR, "009", "ログイン"));
  }

  userSession.setRegistedAccountForm(registedAccountForm);
  sessionService.bindSession(AuthService.USER_SESSION_KEY_NAME, userSession);

  return accountChangeForm;
 }

 /**
  * トリマー変更情報設定
  * @param request リクエストオブジェクト
  * @param trimmerInfo トリマー情報オブジェクト
  * @return トリマー情報変更フォームオブジェクト
  */
 private AccountChangeForm setAccountChangeInfoWithTrimmerInfo(HttpServletRequest request, TrimmerInfo trimmerInfo)
   throws MyException {
  SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
  Pattern preficturePattern = Pattern.compile(PREFECTURE_FORMAT);
  AccountChangeForm accountChangeForm = new AccountChangeForm();
  RegistedAccountForm registedAccountForm = new RegistedAccountForm();
  String userName = trimmerInfo.getUserName();
  accountChangeForm.setUserName(userName);
  registedAccountForm.setUserName(userName);
  String password = trimmerInfo.getPassword();
  accountChangeForm.setPassword(password);
  registedAccountForm.setPassword(password);
  String sex = trimmerInfo.getSex();
  accountChangeForm.setSex(sex);
  registedAccountForm.setSex(sex);
  String birthday = dateFormat.format(trimmerInfo.getBirthday());
  accountChangeForm.setBirthday(birthday);
  registedAccountForm.setBirthday(birthday);
  String postalCode = trimmerInfo.getPostalCode();
  accountChangeForm.setPostalCode(postalCode);
  registedAccountForm.setPostalCode(postalCode);
  String streetAddres = trimmerInfo.getStreetAddress();
  Matcher macher = preficturePattern.matcher(streetAddres);
  boolean isExistPrefecture = macher.find();
  if(isExistPrefecture == false) {
   throw new MyException("msg.error.011",
   this.messageService.getMessage(MessageService.MessageType.ERROR, "011", "アカウント情報"));
  }
  String prefecture = macher.group();
  String prefectureKey = this.propertiesService.getKey(prefecture);
  if(prefectureKey == null) {
   throw new MyException("msg.error.011",
    this.messageService.getMessage(MessageService.MessageType.ERROR, "011", "アカウント情報"));
  }else {
   prefectureKey = prefectureKey.replace(PropertiesService.PREFECTURES_KEY_INIT_STR,"");
  }
  accountChangeForm.setPrefectures(prefectureKey);
  registedAccountForm.setPrefectures(prefectureKey);
  String cities = streetAddres.substring(macher.end());
  accountChangeForm.setCities(cities);
  registedAccountForm.setCities(cities);
  String emailAddres = trimmerInfo.getEmailAddress();
  accountChangeForm.setEmailAddress(emailAddres);
  registedAccountForm.setEmailAddress(emailAddres);
  String telephoneNum = trimmerInfo.getTelephoneNumber();
  accountChangeForm.setTelephoneNumber(telephoneNum);
  registedAccountForm.setTelephoneNumber(telephoneNum);
  String storeName = trimmerInfo.getStoreName();
  accountChangeForm.setStoreName(storeName);
  registedAccountForm.setStoreName(storeName);
  List<FormBusinessHours> formBusinessHoursList = new ArrayList<>();
  List<TrimmerInfoBusinessHours> trimmerInfoBusinessHoursList = trimmerInfo.getTrimmerInfoBusinessHoursList();
  String formBusinessHoursInputValue = null;
  for (TrimmerInfoBusinessHours trimmerInfoBusinessHours : trimmerInfoBusinessHoursList) {
   String businessDay = trimmerInfoBusinessHours.getBusinessDay();
   LocalTime startBusinessTime = trimmerInfoBusinessHours.getStartBusinessTime();
   LocalTime endBusinessTime = trimmerInfoBusinessHours.getEndBusinessTime();
   if (!StringUtils.isEmpty(businessDay) && startBusinessTime != null && endBusinessTime != null) {
    FormBusinessHours formBusinessHours = new FormBusinessHours();
    // 先頭0抜き
    int tempBusinessDay = Integer.parseInt(businessDay);
    businessDay = Integer.toString(tempBusinessDay);
    if(formBusinessHoursInputValue == null) {
     formBusinessHoursInputValue = businessDay;
    }else {
     String inputValue = "," + businessDay;
     formBusinessHoursInputValue += inputValue;
    }
    formBusinessHours.setBusinessHoursWeekdayNum(Integer.toString(tempBusinessDay));
    formBusinessHours.setBusinessHoursStartTime(startBusinessTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    formBusinessHours.setBusinessHoursEndTime(endBusinessTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    String complement = trimmerInfoBusinessHours.getComplement();
    formBusinessHours.setBusinessHoursRemarks(complement);
    formBusinessHoursList.add(formBusinessHours);
   }
  }
  accountChangeForm.setFormBusinessHoursInputValue(formBusinessHoursInputValue);
  registedAccountForm.setFormBusinessHoursInputValue(formBusinessHoursInputValue);
  accountChangeForm.setFormBusinessHoursList(formBusinessHoursList);
  registedAccountForm.setFormBusinessHoursList(formBusinessHoursList);
  Integer tempStoreEmployeesNum = trimmerInfo.getStoreEmployeesNumber();
  String storeEmployeesNum = tempStoreEmployeesNum == null?null:tempStoreEmployeesNum.toString();
  accountChangeForm.setStoreEmployees(storeEmployeesNum);
  registedAccountForm.setStoreEmployees(storeEmployeesNum);
  String storeCourseInfo = trimmerInfo.getStoreCourseInfo();
  accountChangeForm.setCourseInfo(storeCourseInfo);
  registedAccountForm.setCourseInfo(storeCourseInfo);
  String storeCommitment = trimmerInfo.getStoreCommitment();
  accountChangeForm.setCommitment(storeCommitment);
  registedAccountForm.setCommitment(storeCommitment);

  SessionService sessionService = new SessionService((HttpServletRequest)request);
  UserSession userSession = (UserSession)sessionService.getBindingKeySessionValue(AuthService.USER_SESSION_KEY_NAME);
  if(userSession == null) {
   throw new MyException("msg.error.011",
    this.messageService.getMessage(MessageService.MessageType.ERROR, "009", "ログイン"));
  }

  userSession.setRegistedAccountForm(registedAccountForm);
  sessionService.bindSession(AuthService.USER_SESSION_KEY_NAME, userSession);

  return accountChangeForm;
 }

 /**
  * 変更
  * @param request リクエストオブジェクト
  * @return 変更成功失敗
  */
 public boolean change(HttpServletRequest request) {
  try {
   SessionService sessionService = new SessionService((HttpServletRequest)request);
   UserSession userSession = (UserSession)sessionService.getBindingKeySessionValue(AuthService.USER_SESSION_KEY_NAME);
   if(userSession == null) {
    throw new MyException("msg.error.009",
    this.messageService.getMessage(MessageService.MessageType.ERROR, "009", "ログイン"));
   }

   AccountChangeForm accountChangeForm = getFormParameterDto(request);
   if (isValidate(request, accountChangeForm)) {
    changeDao(request, accountChangeForm, userSession);
   } else {
    setAttributeKeyWithCanNotValidate(request, accountChangeForm);
   }

  }catch (MyException | SQLException | ParseException | IOException | ServletException e) {
   e.printStackTrace();
  }

  return this.canRegist;
 }

 /**
  * フォームパラメータ取得
  * @param request リクエストオブジェクト
  * @return アカウントフォームオブジェクト
  */
 private AccountChangeForm getFormParameterDto(HttpServletRequest request)
   throws SQLException, ParseException, IOException, ServletException {
  AccountChangeForm accountChangeForm = new AccountChangeForm();
  accountChangeForm.setUserName(request.getParameter("user-name"));
  accountChangeForm.setPassword(request.getParameter("password"));
  accountChangeForm.setSex(request.getParameter("radio-user-sex"));
  accountChangeForm.setBirthday(request.getParameter("birthday"));
  accountChangeForm.setPostalCode(request.getParameter("postal-code"));
  accountChangeForm.setPrefectures(request.getParameter("prefectures"));
  accountChangeForm.setCities(request.getParameter("cities"));
  accountChangeForm.setEmailAddress(request.getParameter("email-address"));
  accountChangeForm.setTelephoneNumber(request.getParameter("telephone-number"));
  switch (this.registType) {
  //飼い主の場合
  case OWNER:
   accountChangeForm.setPetName(request.getParameter("pet-name"));
   accountChangeForm.setPetSex(request.getParameter("radio-pet-sex"));
   accountChangeForm.setPetType(request.getParameter("pet-type"));
   accountChangeForm.setPetWeight(request.getParameter("pet-weight"));
   accountChangeForm.setPetRemarks(request.getParameter("pet-remarks"));
   break;
  //トリマーの場合
  case TRIMMER:
   accountChangeForm.setStoreName(request.getParameter("store-name"));
   accountChangeForm.setFormBusinessHoursInputValue(request.getParameter("business-hours"));
   accountChangeForm.setFormBusinessHoursList(getFormParameterBusinessHoursDto(request));
   accountChangeForm.setStoreEmployees(request.getParameter("store-employees"));
   accountChangeForm.setCourseInfo(request.getParameter("course-info"));
   accountChangeForm.setCommitment(request.getParameter("commitment"));
   break;
  default:
   break;
  }

  return accountChangeForm;
 }

 /**
  * 営業時間登録フォームパラメータ取得
  * @param request リクエストオブジェクト
  * @return 営業時間登録フォームリスト
  */
 private List<FormBusinessHours> getFormParameterBusinessHoursDto(HttpServletRequest request)
   throws IOException, ServletException, ParseException {
  List<FormBusinessHours> formBusinessHoursList = new ArrayList<>();
  String formBusinessHoursWeek = request.getParameter("business-hours");
  if (!StringUtils.isEmpty(formBusinessHoursWeek)) {
   String formBusinessHoursWeekAry[] = formBusinessHoursWeek.split(",");
   for (int i = 0; i < formBusinessHoursWeekAry.length; i++) {
    FormBusinessHours formBusinessHours = new FormBusinessHours();
    formBusinessHours.setBusinessHoursWeekdayNum(formBusinessHoursWeekAry[i]);
    formBusinessHours.setBusinessHoursStartTime(
      request.getParameter("business-hours-start-time-" + formBusinessHoursWeekAry[i]));
    formBusinessHours.setBusinessHoursEndTime(
      request.getParameter("business-hours-end-time-" + formBusinessHoursWeekAry[i]));
    formBusinessHours.setBusinessHoursRemarks(
      request.getParameter("business-hours-remarks-" + formBusinessHoursWeekAry[i]));

    formBusinessHoursList.add(formBusinessHours);
   }
  }
  return formBusinessHoursList;
 }

 /**
  * バリデーションチェック
  * @param request リクエストオブジェクト
  * @param accountChangeForm アカウント変更フォームオブジェクト
  * @return 登録成功失敗
  */
 private boolean isValidate(HttpServletRequest request, AccountChangeForm accountChangeForm)
   throws ParseException, IOException, ServletException {
  //ユーザーネーム10文字以下チェック
  if (accountChangeForm.getUserName().length() > 10) {
   this.msgMap.put("001",
     this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "ユーザーネーム", "10文字以下"));
  }

  //パスワード20文字以下チェック
  if (accountChangeForm.getPassword().length() > 20) {
   this.msgMap.put("003",
     this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "パスワード", "20文字以下"));
  }

  //郵便番号形式チェック
  if (!Pattern.matches(POSTAL_CODE_FORMAT, accountChangeForm.getPostalCode())) {
   this.msgMap.put("004", this.messageService.getMessage(MessageService.MessageType.ERROR, "003", "郵便番号"));
  }

  //都道府県選択チェック
  if (accountChangeForm.getPrefectures().equals(DEFAULT_SELECT_OR_RADIO_VAL)) {
   this.msgMap.put("005", this.messageService.getMessage(MessageService.MessageType.ERROR, "004", "都道府県"));
  }

  //市区町村形式チェック
  if (!Pattern.matches(CITIES_FORMAT, accountChangeForm.getCities())) {
   this.msgMap.put("006", this.messageService.getMessage(MessageService.MessageType.ERROR, "003", "市区町村"));
  }

  //市区町村9文字以下チェック
  if (accountChangeForm.getCities().length() > 9) {
   this.msgMap.put("007",
     this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "市区町村", "9文字以下"));
  }

  //メールアドレス形式チェック
  if (!Pattern.matches(EMAIL_ADDRES_FORMAT, accountChangeForm.getEmailAddress())) {
   this.msgMap.put("008", this.messageService.getMessage(MessageService.MessageType.ERROR, "003", "メールアドレス"));
  }

  //メールアドレス254文字以下チェック
  if (accountChangeForm.getEmailAddress().length() > 254) {
   this.msgMap.put("009",
     this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "メールアドレス", "254文字以下"));
  }

  //電話番号形式チェック
  if (!Pattern.matches(TELEPHONE_NUMBER_FORMAT, accountChangeForm.getTelephoneNumber())) {
   this.msgMap.put("010", this.messageService.getMessage(MessageService.MessageType.ERROR, "003", "電話番号"));
  }

  switch (this.registType) {
  //飼い主の場合
  case OWNER:
   //ペットニックネーム20文字以下チェック
   if (accountChangeForm.getPetName().length() > 20) {
    this.msgMap.put("011",
      this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "ペットネーム", "20文字以下"));
   }

   //ペット体重数値チェック
   String accountChangeFormPetWeight = accountChangeForm.getPetWeight();
   //任意項目のため
   if (!accountChangeFormPetWeight.isEmpty()) {
    if (Pattern.matches(DECIMAL_FORMAT, accountChangeFormPetWeight)
      || Pattern.matches(INIT_ZERO_DECIMAL_FORMAT, accountChangeFormPetWeight)) {
     //ペット体重小数桁数チェック
     if (accountChangeFormPetWeight.equals("0") || Pattern.matches(ZERO_DECIMAL_FORMAT, accountChangeFormPetWeight)) {
      this.msgMap.put("012", this.messageService.getMessage(MessageService.MessageType.ERROR, "001",
        "ペット体重", "0以上の実数値"));
     } else {
      if (!Pattern.matches(END_ZERO_DECIMAL_FORMAT, accountChangeFormPetWeight)) {
       int pointIndex = accountChangeFormPetWeight.indexOf(".");
       if (pointIndex != -1) {
        BigDecimal registFormPetWeightVal = new BigDecimal(accountChangeFormPetWeight);
        if (registFormPetWeightVal.stripTrailingZeros().toString().substring(pointIndex + 1)
          .length() > 2) {
         this.msgMap.put("013", this.messageService
           .getMessage(MessageService.MessageType.ERROR, "001", "ペット体重", "小数第2位まで"));
        }
       }
      }
     }
    } else {
     this.msgMap.put("012", this.messageService.getMessage(MessageService.MessageType.ERROR, "001",
       "ペット体重", "0以上の実数値"));
    }
   }

   String accountChangeFormPetRemarks = accountChangeForm.getPetRemarks();
   //備考200文字以下チェック
   if (accountChangeFormPetRemarks.length() > 200) {
    this.msgMap.put("014",
      this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "備考", "200文字以下"));
   }

   //備考XSS対策
   if (accountChangeFormPetRemarks.contains(TEXTAREA_INIT_PART_TAG)
     || accountChangeFormPetRemarks.contains(TEXTAREA_END_TAG)) {
    this.msgMap.put("023", this.messageService.getMessage(MessageService.MessageType.ERROR, "007"));
   }

   break;

  //トリマーの場合
  case TRIMMER:
   //店名50文字以下チェック
   if (accountChangeForm.getStoreName().length() > 50) {
    this.msgMap.put("015",
      this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "店名", "50文字以下"));
   }

   int errorTimeCount = 0;
   int errorRemarksCount = 0;
   int errorXSSRemarksCount = 0;
   //営業時間チェック
   List<FormBusinessHours> formBusinessHoursList = accountChangeForm.getFormBusinessHoursList();
   //Multipickerで選択されている場合
   for (FormBusinessHours formBusinessHours : formBusinessHoursList) {
    boolean judgeTimeFlg = true;
    //営業時間形式チェック
    String formBusinessHoursStartTime = formBusinessHours.getBusinessHoursStartTime();
    if (!formBusinessHoursStartTime.isEmpty()) {
     if (!Pattern.matches(BUSINESS_TIME_FORMAT, formBusinessHoursStartTime)) {
      formBusinessHours.setIsErrBusinessHoursStartTime("1");
      judgeTimeFlg = false;
      if (errorTimeCount == 0) {
       this.msgMap.put("016",
         this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "営業時間"));
       errorTimeCount++;
      }
     }
    }

    String formBusinessHoursEndTime = formBusinessHours.getBusinessHoursEndTime();
    if (!formBusinessHoursEndTime.isEmpty()) {
     if (!Pattern.matches(BUSINESS_TIME_FORMAT, formBusinessHoursEndTime)) {
      formBusinessHours.setIsErrBusinessHoursEndTime("1");
      judgeTimeFlg = false;
      if (errorTimeCount == 0) {
       this.msgMap.put("016",
         this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "営業時間"));
       errorTimeCount++;
      }
     }
    }

    if (judgeTimeFlg) {
     if (!formBusinessHoursStartTime.isEmpty() && !formBusinessHoursEndTime.isEmpty()) {
      String[] startBusinessTimeAry = formBusinessHoursStartTime.split(":");
      String[] endBusinessTimeAry = formBusinessHoursEndTime.split(":");
      if (startBusinessTimeAry[0].equals(endBusinessTimeAry[0])) {
       if (Integer.parseInt(startBusinessTimeAry[1]) >= Integer.parseInt(endBusinessTimeAry[1])) {
        formBusinessHours.setIsErrBusinessHoursStartTime("1");
        if (errorTimeCount == 0) {
         this.msgMap.put("016", this.messageService
           .getMessage(MessageService.MessageType.ERROR, "005", "営業時間"));
         errorTimeCount++;
        }
       }
      } else {
       if (Integer.parseInt(startBusinessTimeAry[0]) > Integer.parseInt(endBusinessTimeAry[0])) {
        formBusinessHours.setIsErrBusinessHoursStartTime("1");
        if (errorTimeCount == 0) {
         this.msgMap.put("016", this.messageService
           .getMessage(MessageService.MessageType.ERROR, "005", "営業時間"));
         errorTimeCount++;
        }
       }
      }
     } else if (formBusinessHoursStartTime.isEmpty() && !formBusinessHoursEndTime.isEmpty()) {
      formBusinessHours.setIsErrBusinessHoursStartTime("1");
      if (errorTimeCount == 0) {
       this.msgMap.put("016",
         this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "営業時間"));
       errorTimeCount++;
      }
     } else if (!formBusinessHoursStartTime.isEmpty() && formBusinessHoursEndTime.isEmpty()) {
      formBusinessHours.setIsErrBusinessHoursStartTime("1");
      if (errorTimeCount == 0) {
       this.msgMap.put("016",
         this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "営業時間"));
       errorTimeCount++;
      }
     }
    }

    String formBusinessHoursRemarks = formBusinessHours.getBusinessHoursRemarks();
    //補足100文字以下チェック
    if (formBusinessHoursRemarks.length() > 100) {
     formBusinessHours.setIsErrLengthBusinessHoursRemarks("1");
     if (errorRemarksCount == 0) {
      this.msgMap.put("017", this.messageService.getMessage(MessageService.MessageType.ERROR, "001",
        "営業時間補足", "100文字以下"));
      errorRemarksCount++;
     }
    }

    //補足XSS対策
    if (formBusinessHoursRemarks.contains(TEXTAREA_INIT_PART_TAG)
      || formBusinessHoursRemarks.contains(TEXTAREA_END_TAG)) {
     formBusinessHours.setIsErrXSSBusinessHoursRemarks("1");
     if (errorXSSRemarksCount == 0) {
      this.msgMap.put("024", this.messageService.getMessage(MessageService.MessageType.ERROR, "007"));
      errorXSSRemarksCount++;
     }
    }
   }

   String accountChangeFormStoreEmployees = accountChangeForm.getStoreEmployees();
   //任意項目のため
   if (!accountChangeFormStoreEmployees.isEmpty()) {
    //従業員数数値チェック
    if (Pattern.matches(NUMBER_FORMAT, accountChangeFormStoreEmployees)) {
     //従業員数桁数チェック
     if (accountChangeFormStoreEmployees.length() > 8) {
      this.msgMap.put("019", this.messageService.getMessage(MessageService.MessageType.ERROR, "001",
        "従業員数", "8桁以下"));
     }
    } else {
     this.msgMap.put("018",
       this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "従業員数", "数値"));
    }
   }

   String accountChangeFormCourseInfo = accountChangeForm.getCourseInfo();
   //コース200文字以下チェック
   if (accountChangeFormCourseInfo.length() > 200) {
    this.msgMap.put("020",
      this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "コース", "200文字以下"));
   }

   //コースXSS対策
   if (accountChangeFormCourseInfo.contains(TEXTAREA_INIT_PART_TAG)
     || accountChangeFormCourseInfo.contains(TEXTAREA_END_TAG)) {
    this.msgMap.put("025", this.messageService.getMessage(MessageService.MessageType.ERROR, "007"));
   }

   String accountChangeFormCommitment = accountChangeForm.getCommitment();
   //こだわり200文字以下チェック
   if (accountChangeFormCommitment.length() > 200) {
    this.msgMap.put("021",
      this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "こだわり", "200文字以下"));
   }

   //こだわりXSS対策
   if (accountChangeFormCommitment.contains(TEXTAREA_INIT_PART_TAG)
     || accountChangeFormCommitment.contains(TEXTAREA_END_TAG)) {
    this.msgMap.put("026", this.messageService.getMessage(MessageService.MessageType.ERROR, "007"));
   }

   break;

  default:
   break;
  }

  //バリデーションエラーがある場合
  if (this.msgMap.size() > 0) {
   //画像添付している場合
   if (request.getPart("file").getSize() > 0) {
    this.msgMap.put("022", this.messageService.getMessage(MessageService.MessageType.ERROR, "006", "画像"));
   }
   this.canRegist = false;
  }
  return this.canRegist;
 }

 /**
  * バリデーションエラー属性設定
  * @param request リクエストオブジェクト
  * @param accountChangeForm アカウント変更フォームオブジェクト
  */
 private void setAttributeKeyWithCanNotValidate(HttpServletRequest request, AccountChangeForm accountChangeForm) {
  setInitPropertiesKey(request);
  String registTypeId = this.getRegistTypeId();
  String registTypeName = this.propertiesService
    .getValue(PropertiesService.REGIST_TYPE_KEY_INIT_STR + registTypeId);
  request.setAttribute("formRegistType", registTypeId);
  request.setAttribute("registTypeName", registTypeName);
  request.setAttribute("accountChangeForm", accountChangeForm);
  request.setAttribute("formBusinessHoursList", accountChangeForm.getFormBusinessHoursList());
  request.setAttribute("msgMap", this.msgMap);
 }

 /**
  * 変更DB処理
  * @param request リクエストオブジェクト
  * @param accountChangeForm アカウントフォームオブジェクト
  */
 private void changeDao(HttpServletRequest request, AccountChangeForm accountChangeForm, UserSession userSession) {
  DBConnection con = new DBConnection();
  Connection conSQL = con.getConnection();
  CreateDao createDao = new CreateDao(conSQL);
  UpdateDao updateDao = new UpdateDao(conSQL);
  DeleteDao deleteDao = new DeleteDao(conSQL);

  try {
   User user = getParameterUserDto(accountChangeForm, userSession);
   switch (this.registType) {
   //飼い主の場合
   case OWNER:
    Pet pet = getParameterPetDto(request, accountChangeForm, userSession);
    user.setPet(pet);
    if (this.canRegist) {
     //DB登録処理前に登録成功失敗リセット
     this.canRegist = false;
     updateDao.updateOwnerInfo(user, this.isUpdatePetWeight, this.isUpdateRemarks);
     setAttributeChangeOwner(request, user, userSession);
     conSQL.commit();
     this.canRegist = true;
    }
    break;

   //トリマーの場合
   case TRIMMER:
    Store store = getParameterStoreDto(request, accountChangeForm, userSession);
    user.setStore(store);

    ChangeBusinessHoursInfo changeBusinessHoursInfo = getParameterBusinessHoursDto(accountChangeForm, userSession);
    if (this.canRegist) {
     //DB登録処理前に登録成功失敗リセット
     this.canRegist = false;
     List<BusinessHours> updateBusinessHoursList = changeBusinessHoursInfo.getUpdateBusinessHoursList();
     List<BusinessHours> createBusinessHoursList = changeBusinessHoursInfo.getCreateBusinessHoursList();
     List<BusinessHours> deleteBusinessHoursList = changeBusinessHoursInfo.getDeleteBusinessHoursList();

     store.setBusinessHoursList(updateBusinessHoursList);
     user.setStore(store);
     List<Boolean> isUpdateBusinessHoursComplementList = new ArrayList<>(this.isUpdateBusinessHoursComplementMap.values());
     updateDao.updateTrimmerInfo(user, this.isUpdateEmployeesNum, this.isUpdateCourseInfo, this.isUpdateCommitment, isUpdateBusinessHoursComplementList);

     Integer storeId = userSession.getStoreId();
     if(createBusinessHoursList.size() > 0) {
      createDao.registBusinessHours(storeId, createBusinessHoursList);
     }

     if(deleteBusinessHoursList.size() > 0) {
      deleteDao.deleteBusinessHours(storeId, deleteBusinessHoursList);
     }

     setAttributeChangeTrimmer(request, user, changeBusinessHoursInfo, userSession);
     conSQL.commit();
     this.canRegist = true;
    }
    break;

   default:
    break;
   }
  } catch (SQLException | ParseException | IOException | ServletException e) {
   try {
    conSQL.rollback();
   } catch (SQLException e1) {
    e1.printStackTrace();
   }
   e.printStackTrace();
  } finally {
   con.close();
  }
 }

 /**
  * パラメータセットユーザオブジェクト取得
  * @param accountChangeForm アカウント変更フォームオブジェクト
  * @param userSession ユーザセッション
  * @return ユーザオブジェクト
  */
 private User getParameterUserDto(AccountChangeForm accountChangeForm, UserSession userSession) throws SQLException, ParseException {
  User user = new User();

  RegistedAccountForm registedAccountForm = userSession.getRegistedAccountForm();
  
  user.setUserId(userSession.getUserId());

  String userName = null;
  String tempUserName = accountChangeForm.getUserName();
  if(!tempUserName.equals(registedAccountForm.getUserName())) {
   userName = tempUserName;
  }
  user.setUserName(userName);

  String password = null;
  String tempPassword = accountChangeForm.getPassword();
  if(!tempPassword.equals(registedAccountForm.getPassword())) {
   password = tempPassword;
  }
  user.setPassword(password);

  String sex = null;
  String tempSex = getParameterData(accountChangeForm.getSex());
  String compSex = tempSex == null?DEFAULT_SELECT_OR_RADIO_VAL:tempSex;
  if(!compSex.equals(registedAccountForm.getSex())) {
   sex = tempSex;
  }
  user.setSex(sex);

  Date birthday = null;
  String tempBirthday = accountChangeForm.getBirthday();
  if(!tempBirthday.equals(registedAccountForm.getBirthday())) {
   SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
   birthday = dateFormat.parse(tempBirthday);
  }
  user.setBirthday(birthday);

  String postalCode = null;
  String tempPostalCode = accountChangeForm.getPostalCode();
  if(!tempPostalCode.equals(registedAccountForm.getPostalCode())) {
   postalCode = tempPostalCode;
  }
  user.setPostalCode(postalCode);

  String address = null;
  boolean changedPrefectures = false;
  boolean changedCities = false;
  String tempPrefectures = accountChangeForm.getPrefectures();
  if(!tempPrefectures.equals(registedAccountForm.getPrefectures())) {
   changedPrefectures = true;
  }
  String tempCities = accountChangeForm.getCities();
  if(!tempCities.equals(registedAccountForm.getCities())) {
   changedCities = true;
  }
  if(changedPrefectures || changedCities) {
   String prefectures = this.propertiesService
    .getValue(PropertiesService.PREFECTURES_KEY_INIT_STR + tempPrefectures);
   String cities = tempCities;
   address = prefectures + cities;
  }
  user.setStreetAddress(address);

  String emailAddress = null;
  String tempEmailAddress = accountChangeForm.getEmailAddress();
  if(!tempEmailAddress.equals(registedAccountForm.getEmailAddress())) {
   emailAddress = tempEmailAddress;
  }
  user.setEmailAddress(emailAddress);

  String telephoneNumber = null;
  String tempTelephoneNumber = accountChangeForm.getTelephoneNumber();
  if(!tempTelephoneNumber.equals(registedAccountForm.getTelephoneNumber())) {
   telephoneNumber = tempTelephoneNumber;
  }
  user.setTelephoneNumber(telephoneNumber);

  LocalDateTime now = LocalDateTime.now();
  user.setUpdatedTime(now);

  return user;
 }

 /**
  * パラメータセットペットオブジェクト取得
  * @param request リクエストオブジェクト
  * @param accountChangeForm アカウント変更フォームオブジェクト
  * @param userSession ユーザセッション
  * @return ペットオブジェクト
  */
 private Pet getParameterPetDto(HttpServletRequest request, AccountChangeForm accountChangeForm, UserSession userSession)
   throws SQLException, IOException, ServletException {
  Pet pet = new Pet();

  RegistedAccountForm registedAccountForm = userSession.getRegistedAccountForm();

  pet.setPetId(userSession.getPetId());

  Part part = request.getPart("file");
  if (part.getSize() > 0) {
   byte[] fileData = convertPartToByteArray(part);
   pet.setImage(fileData);
  }

  String nickName = null;
  String tempNickName = accountChangeForm.getPetName();
  if(!tempNickName.equals(registedAccountForm.getPetName())) {
   nickName = tempNickName;
  }
  pet.setNickName(nickName);

  String petSex = null;
  String tempPetSex = getParameterData(accountChangeForm.getPetSex());
  String compPetSex = tempPetSex == null?DEFAULT_SELECT_OR_RADIO_VAL:tempPetSex;
  if(!compPetSex.equals(registedAccountForm.getPetSex())) {
   petSex = tempPetSex;
  }
  pet.setSex(petSex);

  String petType = null;
  String tempPetType = getSelectParameterData(accountChangeForm.getPetType());
  if(!Objects.equals(tempPetType,registedAccountForm.getPetType())) {
   petType = tempPetType;
  }
  pet.setType(petType);

  Float petWeight = null;
  String tempPetWeight = accountChangeForm.getPetWeight();
  if(StringUtils.isEmpty(tempPetWeight)){
   tempPetWeight = null;
  }
  if(!Objects.equals(tempPetWeight,registedAccountForm.getPetWeight())) {
   this.isUpdatePetWeight = true;
   if (!StringUtils.isEmpty(tempPetWeight)) {
    petWeight = Float.parseFloat(tempPetWeight);
   }
  }
  pet.setWeight(petWeight);

  String remarks = null;
  String tempRemarks = getParameterData(accountChangeForm.getPetRemarks());
  if(!Objects.equals(tempRemarks,registedAccountForm.getPetRemarks())) {
   this.isUpdateRemarks = true;
   remarks = tempRemarks;
  }
  pet.setRemarks(remarks);

  pet.setIsDeleted(0);
  LocalDateTime now = LocalDateTime.now();
  pet.setInsertedTime(now);
  pet.setUpdatedTime(now);

  return pet;
 }

 /**
  * パラメータセット店舗オブジェクト取得
  * @param request リクエストオブジェクト
  * @param accountChangeForm アカウント変更フォームオブジェクト
  * @param userSession ユーザセッション
  * @return 店舗オブジェクト
  */
 private Store getParameterStoreDto(HttpServletRequest request, AccountChangeForm accountChangeForm, UserSession userSession)
   throws SQLException, IOException, ServletException {
  Store store = new Store();

  RegistedAccountForm registedAccountForm = userSession.getRegistedAccountForm();

  store.setStoreId(userSession.getStoreId());
  
  Part part = request.getPart("file");
  if (part.getSize() > 0) {
   byte[] fileData = convertPartToByteArray(part);
   store.setImage(fileData);
  }

  String storeName = null;
  String tempStoreName = accountChangeForm.getStoreName();
  if(!tempStoreName.equals(registedAccountForm.getStoreName())) {
   storeName = tempStoreName;
  }
  store.setStoreName(storeName);

  Integer storeEmployees = null;
  String tempStoreEmployees = accountChangeForm.getStoreEmployees();
  if(StringUtils.isEmpty(tempStoreEmployees)){
   tempStoreEmployees = null;
  }
  if(!Objects.equals(tempStoreEmployees,registedAccountForm.getStoreEmployees())) {
   this.isUpdateEmployeesNum = true;
   if (tempStoreEmployees != null) {
    storeEmployees = Integer.parseInt(tempStoreEmployees);
   }
  }
  store.setEmployeesNumber(storeEmployees);

  String courseInfo = null;
  String tempCourseInfo = getParameterData(accountChangeForm.getCourseInfo());
  if(!Objects.equals(tempCourseInfo,registedAccountForm.getCourseInfo())) {
   this.isUpdateCourseInfo = true;
   courseInfo = tempCourseInfo;
  }
  store.setCourseInfo(courseInfo);

  String commitment = null;
  String tempCommitment = getParameterData(accountChangeForm.getCommitment());
  if(!Objects.equals(tempCommitment,registedAccountForm.getCommitment())) {
   this.isUpdateCommitment = true;
   commitment = tempCommitment;
  }
  store.setCommitment(commitment);

  store.setIsDeleted(0);
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
 private byte[] convertPartToByteArray(Part part) throws IOException {
  ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  InputStream inputStream = part.getInputStream();

  try {
   byte[] data = new byte[BYTE_ARY_SIZE];
   int readByteSize;
   while ((readByteSize = inputStream.read(data, 0, data.length)) != -1) {
    buffer.write(data, 0, readByteSize);
   }
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
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
  if (!StringUtils.isEmpty(param)) {
   if (!param.equals(DEFAULT_SELECT_OR_RADIO_VAL)) {
    rtnVal = param;
   }
  }
  return rtnVal;
 }

 /**
  * パラメータセット営業時間オブジェクト取得
  * @param accountChangeForm アカウント変更フォームオブジェクト
  * @param userSession ユーザセッション
  * @return 変更営業時間情報
  */
 private ChangeBusinessHoursInfo getParameterBusinessHoursDto(AccountChangeForm accountChangeForm, UserSession userSession)
   throws IOException, ServletException, ParseException {
  ChangeBusinessHoursInfo changeBusinessHoursInfo = new ChangeBusinessHoursInfo();
  List<BusinessHours> createBusinessHoursList = new ArrayList<>();
  List<BusinessHours> updateBusinessHoursList = new ArrayList<>();
  List<BusinessHours> deleteBusinessHoursList = new ArrayList<>();
  List<FormBusinessHours> formBusinessHoursList = accountChangeForm.getFormBusinessHoursList();
  String formBusinessHoursWeekday = accountChangeForm.getFormBusinessHoursInputValue();
  if(StringUtils.isEmpty(formBusinessHoursWeekday)) {
   formBusinessHoursWeekday = null;
  }
  List<FormBusinessHours> registedFormBusinessHoursList = userSession.getRegistedAccountForm().getFormBusinessHoursList();
  String registedFormBusinessHoursWeekday = userSession.getRegistedAccountForm().getFormBusinessHoursInputValue();
  if(formBusinessHoursWeekday == null && registedFormBusinessHoursWeekday == null) {
   changeBusinessHoursInfo.setCreateBusinessHoursList(createBusinessHoursList);
   changeBusinessHoursInfo.setUpdateBusinessHoursList(updateBusinessHoursList);
   changeBusinessHoursInfo.setDeleteBusinessHoursList(deleteBusinessHoursList);

   return changeBusinessHoursInfo;
  }
  LocalDateTime now = LocalDateTime.now();
  for (var i = 0; i < 7; i++) {
   String weekNumStr = String.valueOf(i);
   boolean isContainsForm = formBusinessHoursWeekday == null?false:formBusinessHoursWeekday.contains(weekNumStr);
   boolean isContainsRegisted = registedFormBusinessHoursWeekday == null?false:registedFormBusinessHoursWeekday.contains(weekNumStr);
   if(isContainsForm && isContainsRegisted) {
    BusinessHours businessHours = new BusinessHours();
    FormBusinessHours formBusinessHours = formBusinessHoursList.stream().filter(e->e.getBusinessHoursWeekdayNum().equals(weekNumStr)).findFirst().orElse(new FormBusinessHours());
    FormBusinessHours registedFormBusinessHours = registedFormBusinessHoursList.stream().filter(e->e.getBusinessHoursWeekdayNum().equals(weekNumStr)).findFirst().orElse(new FormBusinessHours());
    String weekNum = "00" + i;
    businessHours.setBusinessDay(weekNum);
    LocalTime startBusinessTime = null;
    String tempStartBusinessTime = formBusinessHours.getBusinessHoursStartTime();
    if(!tempStartBusinessTime.equals(registedFormBusinessHours.getBusinessHoursStartTime())) {
     String[] startBusinessTimeAry = tempStartBusinessTime.split(":");
     startBusinessTime = LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1]));
    }
    businessHours.setStartBusinessTime(startBusinessTime);

    LocalTime endBusinessTime = null;
    String tempEndBusinessTime = formBusinessHours.getBusinessHoursEndTime();
    if(!tempEndBusinessTime.equals(registedFormBusinessHours.getBusinessHoursEndTime())) {
     String[] endBusinessTimeAry = tempEndBusinessTime.split(":");
     endBusinessTime = LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1]));
    }
    businessHours.setEndBusinessTime(endBusinessTime);

    String complement = null;
    boolean isUpdatedBusinessHoursComplement = false;
    String tempComplement = getParameterData(formBusinessHours.getBusinessHoursRemarks());
    if(!Objects.equals(tempComplement,registedFormBusinessHours.getBusinessHoursRemarks())) {
     isUpdatedBusinessHoursComplement = true;
     complement = tempComplement;
    }
    this.isUpdateBusinessHoursComplementMap.put(weekNum, isUpdatedBusinessHoursComplement);
    businessHours.setComplement(complement);

    businessHours.setIsDeleted(0);
    businessHours.setUpdatedTime(now);

    updateBusinessHoursList.add(businessHours);
   }else if(isContainsForm && !isContainsRegisted) {
    BusinessHours businessHours = new BusinessHours();
    FormBusinessHours formBusinessHours = formBusinessHoursList.stream().filter(e->e.getBusinessHoursWeekdayNum().equals(weekNumStr)).findFirst().orElse(new FormBusinessHours());
    businessHours.setBusinessDay("00" + i);
    String tempStartBusinessTime = formBusinessHours.getBusinessHoursStartTime();
    String[] startBusinessTimeAry = tempStartBusinessTime.split(":");
    LocalTime startBusinessTime = LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1]));
    businessHours.setStartBusinessTime(startBusinessTime);

    String tempEndBusinessTime = formBusinessHours.getBusinessHoursEndTime();
    String[] endBusinessTimeAry = tempEndBusinessTime.split(":");
    LocalTime endBusinessTime = LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1]));
    businessHours.setEndBusinessTime(endBusinessTime);

    String complement = getParameterData(formBusinessHours.getBusinessHoursRemarks());
    businessHours.setComplement(complement);

    businessHours.setIsDeleted(0);
    businessHours.setInsertedTime(now);
    businessHours.setUpdatedTime(now);

    createBusinessHoursList.add(businessHours);
   }else if(!isContainsForm && isContainsRegisted) {
    BusinessHours businessHours = new BusinessHours();
    businessHours.setBusinessDay("00" + i);

    deleteBusinessHoursList.add(businessHours);
   }
  }

  changeBusinessHoursInfo.setCreateBusinessHoursList(createBusinessHoursList);
  changeBusinessHoursInfo.setUpdateBusinessHoursList(updateBusinessHoursList);
  changeBusinessHoursInfo.setDeleteBusinessHoursList(deleteBusinessHoursList);

  return changeBusinessHoursInfo;
 }

 /**
  * パラメータデータ取得
  * @param param 文字列引数
  * @return 文字列引数がnullまたは空である場合、null<br>
  * そうでない場合、文字列引数
  */
 private String getParameterData(String param) {
  String rtnVal = null;
  if (!StringUtils.isEmpty(param)) {
   rtnVal = param;
  }
  return rtnVal;
 }

 /**
  * 飼い主用変更オブジェクト属性設定
  * @param request リクエストオブジェクト
  * @param user ユーザオブジェクト
  * @param userSession ユーザセッション
  */
 private void setAttributeChangeOwner(HttpServletRequest request, User user, UserSession userSession) {
  Pet pet = user.getPet();

  //変更完了画面表示文字セット
  String registTypeId = this.getRegistTypeId();
  String registTypeName = this.propertiesService
    .getValue(PropertiesService.REGIST_TYPE_KEY_INIT_STR + registTypeId);

  RegistedAccountForm beforeAccoutInfo = userSession.getRegistedAccountForm();

  String tempUserSex = user.getSex();
  if(tempUserSex != null) {  
   String userSex = this.propertiesService.getValue(PropertiesService.HUMAN_SEX_KEY_INIT_STR + tempUserSex);
   user.setSex(userSex);
  }
  tempUserSex = beforeAccoutInfo.getSex();
  if(tempUserSex != null) {  
   String userSex = this.propertiesService.getValue(PropertiesService.HUMAN_SEX_KEY_INIT_STR + tempUserSex);
   beforeAccoutInfo.setSex(userSex);
  }

  String tempPrefectures = beforeAccoutInfo.getPrefectures();
  if(tempPrefectures != null) {
   String prefectures = this.propertiesService.getValue(PropertiesService.PREFECTURES_KEY_INIT_STR + tempPrefectures);
   beforeAccoutInfo.setPrefectures(prefectures);
  }

  String tempPetSex = pet.getSex();
  if(tempPetSex != null) {  
   String petSex = this.propertiesService.getValue(PropertiesService.PET_SEX_KEY_INIT_STR + tempPetSex);
   pet.setSex(petSex);
  }
  tempPetSex = beforeAccoutInfo.getPetSex();
  if(tempPetSex != null) {  
   String petSex = this.propertiesService.getValue(PropertiesService.PET_SEX_KEY_INIT_STR + tempPetSex);
   beforeAccoutInfo.setPetSex(petSex);
  }

  String tempPetType = pet.getType();
  if(tempPetType != null) {  
   String petType = this.propertiesService.getValue(PropertiesService.PET_TYPE_KEY_INIT_STR + tempPetType);
   pet.setType(petType);
  }
  tempPetType = beforeAccoutInfo.getPetType();
  if(tempPetType != null) {  
   String petType = this.propertiesService.getValue(PropertiesService.PET_TYPE_KEY_INIT_STR + tempPetType);
   beforeAccoutInfo.setPetType(petType);
  }

  request.setAttribute("registTypeName", registTypeName);
  request.setAttribute("registType", registTypeId);
  request.setAttribute("user", user);
  request.setAttribute("pet", pet);
  //画像をBase64化
  request.setAttribute("petImage", convertByteAryToBase64(pet.getImage()));
  request.setAttribute("isUpdatePetWeight", this.isUpdatePetWeight);
  request.setAttribute("isUpdateRemarks", this.isUpdateRemarks);
  request.setAttribute("beforeAccoutInfo", beforeAccoutInfo);
 }

 /**
  * トリマー用変更オブジェクト属性設定
  * @param request リクエストオブジェクト
  * @param user ユーザオブジェクト
  * @param changeBusinessHoursInfo 変更営業時間情報
  * @param userSession ユーザセッション
  */
 private void setAttributeChangeTrimmer(HttpServletRequest request, User user, ChangeBusinessHoursInfo changeBusinessHoursInfo, UserSession userSession) {
  Store store = user.getStore();

  //変更完了画面表示文字セット
  String registTypeId = this.getRegistTypeId();
  String registTypeName = this.propertiesService
    .getValue(PropertiesService.REGIST_TYPE_KEY_INIT_STR + registTypeId);

  RegistedAccountForm beforeAccoutInfo = userSession.getRegistedAccountForm();

  String tempUserSex = user.getSex();
  if(tempUserSex != null) {  
   String userSex = this.propertiesService.getValue(PropertiesService.HUMAN_SEX_KEY_INIT_STR + tempUserSex);
   user.setSex(userSex);
  }
  tempUserSex = beforeAccoutInfo.getSex();
  if(tempUserSex != null) {  
   String userSex = this.propertiesService.getValue(PropertiesService.HUMAN_SEX_KEY_INIT_STR + tempUserSex);
   beforeAccoutInfo.setSex(userSex);
  }

  String tempPrefectures = beforeAccoutInfo.getPrefectures();
  if(tempPrefectures != null) {
   String prefectures = this.propertiesService.getValue(PropertiesService.PREFECTURES_KEY_INIT_STR + tempPrefectures);
   beforeAccoutInfo.setPrefectures(prefectures);
  }

  // 登録された営業時間リストを元に更新した営業時間リストの曜日番号に合わせて、表示営業時間リストを作成
  List<String> businessDayList = new ArrayList<>();
  List<BusinessHours> businessHoursList = new ArrayList<>();
  List<BusinessHours> beforeBusinessHoursList = new ArrayList<>();
  List<BusinessHours> createBusinessHoursList = changeBusinessHoursInfo.getCreateBusinessHoursList();
  List<BusinessHours> updateBusinessHoursList = changeBusinessHoursInfo.getUpdateBusinessHoursList();
  List<Boolean> isUpdateBusinessHoursComplementList = new ArrayList<>();
  List<BusinessHours> deleteBusinessHoursList = changeBusinessHoursInfo.getDeleteBusinessHoursList();
  for(var i = 0; i < 7; i++) {
   String weekNumStr = String.valueOf(i);
   String weekNum = "00" + weekNumStr;
   BusinessHours createBusinessHours = createBusinessHoursList.stream().filter(e->e.getBusinessDay().equals(weekNum)).findFirst().orElse(null);
   BusinessHours updateBusinessHours = updateBusinessHoursList.stream().filter(e->e.getBusinessDay().equals(weekNum)).findFirst().orElse(null);
   boolean isUpdateBusinessHoursComplement = false;
   BusinessHours deleteBusinessHours = deleteBusinessHoursList.stream().filter(e->e.getBusinessDay().equals(weekNum)).findFirst().orElse(null);
   if(createBusinessHours != null && updateBusinessHours == null && deleteBusinessHours == null) {
    businessDayList.add(this.propertiesService.getValue(PropertiesService.WEEKDAY_KEY_INIT_STR + weekNum));
    BusinessHours businessHours = new BusinessHours();
    businessHours.setStartBusinessTime(createBusinessHours.getStartBusinessTime());
    businessHours.setEndBusinessTime(createBusinessHours.getEndBusinessTime());
    businessHours.setComplement(createBusinessHours.getComplement());
    businessHoursList.add(businessHours);
    BusinessHours beforeBusinessHours = new BusinessHours();
    beforeBusinessHoursList.add(beforeBusinessHours);
   }else if(createBusinessHours == null && updateBusinessHours != null && deleteBusinessHours == null) {
    businessDayList.add(this.propertiesService.getValue(PropertiesService.WEEKDAY_KEY_INIT_STR + weekNum));
    List<FormBusinessHours> registedBusinessHoursList = beforeAccoutInfo.getFormBusinessHoursList();
    FormBusinessHours registedBusinessHours = registedBusinessHoursList.stream().filter(e->e.getBusinessHoursWeekdayNum().equals(weekNumStr)).findFirst().orElse(new FormBusinessHours());
    BusinessHours businessHours = new BusinessHours();
    LocalTime updateStartBusinessTime = updateBusinessHours.getStartBusinessTime();
    LocalTime updateEndBusinessTime = updateBusinessHours.getEndBusinessTime();
    businessHours.setStartBusinessTime(updateStartBusinessTime);
    businessHours.setEndBusinessTime(updateEndBusinessTime);
    isUpdateBusinessHoursComplement = this.isUpdateBusinessHoursComplementMap.get(weekNum);
    businessHours.setComplement(isUpdateBusinessHoursComplement?updateBusinessHours.getComplement():null);
    businessHoursList.add(businessHours);
    BusinessHours beforeBusinessHours = new BusinessHours();
    LocalTime startBusinessTime = null;
    String tempStartBusinessTime = registedBusinessHours.getBusinessHoursStartTime();
    if(tempStartBusinessTime != null) {
     String[] startBusinessTimeAry = tempStartBusinessTime.split(":");
     startBusinessTime = LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1]));
    }
    beforeBusinessHours.setStartBusinessTime(updateStartBusinessTime==null?null:startBusinessTime);
    LocalTime endBusinessTime = null;
    String tempEndBusinessTime = registedBusinessHours.getBusinessHoursEndTime();
    if(tempEndBusinessTime != null) {
     String[] endBusinessTimeAry = tempEndBusinessTime.split(":");
     endBusinessTime = LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1]));
    }
    beforeBusinessHours.setEndBusinessTime(updateEndBusinessTime==null?null:endBusinessTime);
    beforeBusinessHours.setComplement(isUpdateBusinessHoursComplement?registedBusinessHours.getBusinessHoursRemarks():null);
    beforeBusinessHoursList.add(beforeBusinessHours);
   }else if(createBusinessHours == null && updateBusinessHours == null && deleteBusinessHours != null) {
    businessDayList.add(this.propertiesService.getValue(PropertiesService.WEEKDAY_KEY_INIT_STR + weekNum));
    List<FormBusinessHours> registedBusinessHoursList = beforeAccoutInfo.getFormBusinessHoursList();
    FormBusinessHours registedBusinessHours = registedBusinessHoursList.stream().filter(e->e.getBusinessHoursWeekdayNum().equals(weekNumStr)).findFirst().orElse(new FormBusinessHours());
    BusinessHours businessHours = new BusinessHours();
    businessHoursList.add(businessHours);
    BusinessHours beforeBusinessHours = new BusinessHours();
    LocalTime startBusinessTime = null;
    String tempStartBusinessTime = registedBusinessHours.getBusinessHoursStartTime();
    if(tempStartBusinessTime != null) {
     String[] startBusinessTimeAry = tempStartBusinessTime.split(":");
     startBusinessTime = LocalTime.of(Integer.parseInt(startBusinessTimeAry[0]), Integer.parseInt(startBusinessTimeAry[1]));
    }
    beforeBusinessHours.setStartBusinessTime(startBusinessTime);
    LocalTime endBusinessTime = null;
    String tempEndBusinessTime = registedBusinessHours.getBusinessHoursEndTime();
    if(tempEndBusinessTime != null) {
     String[] endBusinessTimeAry = tempEndBusinessTime.split(":");
     endBusinessTime = LocalTime.of(Integer.parseInt(endBusinessTimeAry[0]), Integer.parseInt(endBusinessTimeAry[1]));
    }
    beforeBusinessHours.setEndBusinessTime(endBusinessTime);
    beforeBusinessHours.setComplement(registedBusinessHours.getBusinessHoursRemarks());
    beforeBusinessHoursList.add(beforeBusinessHours);
   }
   isUpdateBusinessHoursComplementList.add(isUpdateBusinessHoursComplement);
  }


  request.setAttribute("registTypeName", registTypeName);
  request.setAttribute("registType", registTypeId);
  request.setAttribute("user", user);
  request.setAttribute("store", store);
  //画像をBase64化
  request.setAttribute("storeImage", convertByteAryToBase64(store.getImage()));
  request.setAttribute("isUpdateEmployeesNum", this.isUpdateEmployeesNum);
  request.setAttribute("isUpdateCourseInfo", this.isUpdateCourseInfo);
  request.setAttribute("isUpdateCommitment", this.isUpdateCommitment);
  request.setAttribute("businessDayList", businessDayList);
  request.setAttribute("businessHoursList", businessHoursList);
  request.setAttribute("beforeBusinessHoursList", beforeBusinessHoursList);
  request.setAttribute("isUpdateBusinessHoursComplementList", isUpdateBusinessHoursComplementList);
  request.setAttribute("beforeAccoutInfo", beforeAccoutInfo);
 }

}
