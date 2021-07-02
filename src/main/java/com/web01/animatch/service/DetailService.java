package com.web01.animatch.service;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.ReadDao;
import com.web01.animatch.dto.OwnerInfo;
import com.web01.animatch.dto.TrimmerInfo;
import com.web01.animatch.dto.TrimmerInfoBusinessHours;
import com.web01.animatch.exception.MyException;

/**
 * 詳細サービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class DetailService extends BaseService {

	//メンバー
	/**
	 * 検索区分
	 */
	private UserType searchDetailType;
	/**
	 * プロパティーサービスオブジェクト
	 */
	private PropertiesService propertiesService;
	/**
	 * メッセージサービスオブジェクト
	 */
	private MessageService messageService;

	/**
	 * 引数付きコンストラクタ
	 * @param searchDetailType 検索詳細区分
	 */
	public DetailService(String searchDetailType) {
		this.propertiesService = new PropertiesService();
		this.messageService = new MessageService();
		switch(UserType.getEnumFromEnumName(searchDetailType.toUpperCase())) {
			case OWNER:
				this.searchDetailType = UserType.OWNER;
				break;
			case TRIMMER:
				this.searchDetailType = UserType.TRIMMER;
				break;
			default:
				break;
		}
	}

	/**
	 * 検索詳細データ設定
	 * @param request リクエストオブジェクト
	 * @param userId ユーザID
	 */
	private void setSearchDetailData(HttpServletRequest request, String userId) throws MyException {
		DBConnection con = new DBConnection();
		ReadDao readDao = new ReadDao(con.getConnection());
		try {
			if(!StringUtils.isNumeric(userId)) {
				throw new MyException("msg.error.003", this.messageService.getMessage(MessageService.MessageType.ERROR, "003", "パラメータのユーザID"));
			}

			switch(this.searchDetailType) {
				//飼い主の場合
				case OWNER:
					TrimmerInfo trimmerInfo = readDao.findTrimmerInfoByUserId(Integer.parseInt(userId));

					if(trimmerInfo == null) {
						throw new MyException("msg.error.008", this.messageService.getMessage(MessageService.MessageType.ERROR, "008"));
					}

					//画像をBase64化
					trimmerInfo.setStoreImageBase64(convertByteAryToBase64(trimmerInfo.getStoreImage()));

					//表示する営業時間文字列作成
					List<TrimmerInfoBusinessHours> trimmerInfoBusinessHoursList = trimmerInfo.getTrimmerInfoBusinessHoursList();
					for(TrimmerInfoBusinessHours trimmerInfoBusinessHours:trimmerInfoBusinessHoursList) {
						//曜日、時間が設定されているデータのみ文字列結合
						String businessDay = trimmerInfoBusinessHours.getBusinessDay();
						LocalTime startBusinessTime = trimmerInfoBusinessHours.getStartBusinessTime();
						LocalTime endBusinessTime = trimmerInfoBusinessHours.getEndBusinessTime();
						if(!StringUtils.isEmpty(businessDay) && startBusinessTime != null && endBusinessTime != null) {
							trimmerInfoBusinessHours.setDisplayBusinessHours(this.propertiesService.getValue(PropertiesService.WEEKDAY_KEY_INIT_STR + businessDay));
							trimmerInfoBusinessHours.setDisplayStartBusinessTime(startBusinessTime.format(DateTimeFormatter.ofPattern("HH:mm")));
							trimmerInfoBusinessHours.setDisplayEndBusinessTime(endBusinessTime.format(DateTimeFormatter.ofPattern("HH:mm")));
						}
					}
					request.setAttribute("trimmerInfo", trimmerInfo);

					break;
				//トリマーの場合
				case TRIMMER:
					OwnerInfo ownerInfo = readDao.findOwnerInfoByUserId(Integer.parseInt(userId));

					if(ownerInfo == null) {
						throw new MyException("msg.error.008", this.messageService.getMessage(MessageService.MessageType.ERROR, "008"));
					}

					//画像をBase64化
					ownerInfo.setPetImageBase64(convertByteAryToBase64(ownerInfo.getPetImage()));
					//会員検索画面表示文字セット
					ownerInfo.setPetType(this.propertiesService.getValue(PropertiesService.PET_TYPE_KEY_INIT_STR + ownerInfo.getPetType()));
					ownerInfo.setPetSex(this.propertiesService.getValue(PropertiesService.PET_SEX_KEY_INIT_STR + ownerInfo.getPetSex()));
					request.setAttribute("ownerInfo", ownerInfo);
					break;
				default:
					break;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			con.close();
		}
	}

	/**
	 * 画面属性設定
	 * @param request リクエストオブジェクト
	 * @param userId ユーザID
	 */
	public void setDisplayAttribute(HttpServletRequest request, String userId) throws MyException {
		String searchDetailTypeId = null;
		switch(this.searchDetailType) {
		case OWNER:
			searchDetailTypeId = UserType.OWNER.getId();
			break;
		case TRIMMER:
			searchDetailTypeId = UserType.TRIMMER.getId();
			break;
		default:
			break;
		}
		try {
			setSearchDetailData(request, userId);
			request.setAttribute("searchDetailTypeId", searchDetailTypeId);
		} catch (MyException e) {
			throw e;
		}
	}
}
