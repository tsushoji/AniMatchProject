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
	 * 引数付きコンストラクタ
	 * @param searchDetailType 検索詳細区分
	 */
	public DetailService(String searchDetailType) {
		this.propertiesService = new PropertiesService();
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
	 * @return 設定可否
	 */
	private boolean setSearchDetailData(HttpServletRequest request, String userId) throws SQLException {
		DBConnection con = new DBConnection();
		ReadDao readDao = new ReadDao(con.getConnection());
		try {
			if(!StringUtils.isNumeric(userId)) {
				return false;
			}

			switch(this.searchDetailType) {
				//飼い主の場合
				case OWNER:
					TrimmerInfo trimmerInfo = readDao.findTrimmerInfoByUserId(Integer.parseInt(userId));

					if(trimmerInfo == null) {
						return false;
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
						return false;
					}

					//画像をBase64化
					ownerInfo.setPetImageBase64(convertByteAryToBase64(ownerInfo.getPetImage()));
					//会員検索画面表示文字セット
					ownerInfo.setPetType(this.propertiesService.getValue(PropertiesService.PET_TYPE_KEY_INIT_STR + ownerInfo.getPetType()));
					ownerInfo.setPetSex(this.propertiesService.getValue(PropertiesService.PET_SEX_KEY_INIT_STR + ownerInfo.getPetSex()));
					request.setAttribute("ownerInfo", ownerInfo);
					break;
				default:
					return false;
			}
		}catch(SQLException e) {
			throw e;
		}finally {
			con.close();
		}

		return true;
	}

	/**
	 * 画面属性設定
	 * @param request リクエストオブジェクト
	 * @param userId ユーザID
	 * @return 設定可否
	 */
	public boolean setDisplayAttribute(HttpServletRequest request, String userId) {
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
			if(setSearchDetailData(request, userId)) {
				request.setAttribute("searchDetailTypeId", searchDetailTypeId);
				return true;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return false;
	}
}
