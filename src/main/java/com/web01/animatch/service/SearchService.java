package com.web01.animatch.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.ReadDao;
import com.web01.animatch.dto.OwnerInfo;
import com.web01.animatch.dto.TrimmerInfo;

/**
 * 検索サービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class SearchService extends BaseService{

	//メンバー
	/**
	 * 検索区分
	 */
	private String searchType;
	/**
	 * リソース・バンドルオブジェクト
	 */
	private ResourceBundle resBundle;
	/**
	 * 検索件数
	 */
	private Integer searchCount = 0;

	//定数
	/**
	 * プロパティファイル名
	 */
	private static final String PROPERTIES_NAME = "animatch";
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
	 * ページ表示検索データ数
	 */
	private static final int DISPLAY_DATA_NUM = 5;

	/**
	 * 検索件数setter
	 * @param searchCount 検索件数
	 */
	public void setSearchCount(Integer searchCount) {
		this.searchCount = searchCount;
	}

	/**
	 * 引数付きコンストラクタ
	 * @param registType 登録区分
	 */
	public SearchService(String searchType) {
		this.resBundle = ResourceBundle.getBundle(PROPERTIES_NAME);
		if(searchType.equals("owner")) {
			this.searchType = "001";
		}else if(searchType.equals("trimmer")) {
			this.searchType = "002";
		}
	}

	/**
	 * 初期設定
	 * @param request リクエストオブジェクト
	 */
	public void setInit(HttpServletRequest request) {
		setInitPropertiesKey(request);
		request.setAttribute("searchType", this.searchType);
	}

	/**
	 * 初期プロパティ設定
	 * @param request リクエストオブジェクト
	 */
	private void setInitPropertiesKey(HttpServletRequest request) {
		List<String> prefecturesKeyList = new ArrayList<>();
		List<String> petTypeKeyList = new ArrayList<>();
		List<String> weekdayKeyList = new ArrayList<>();
		Collections.list(this.resBundle.getKeys()).forEach(key -> {
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
		prefecturesKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));

		request.setAttribute("prefecturesKeyList", prefecturesKeyList);
		request.setAttribute("petTypeKeyList", petTypeKeyList);
		request.setAttribute("weekdayKeyList", weekdayKeyList);
	}

	/**
	 * 検索データ取得
	 * @param request リクエストオブジェクト
	 * @param searchType 検索区分
	 * @param tarPage 遷移するページ番号
	 * @return 検索データMap
	 */
	public Map<String, Object> getSearchData(HttpServletRequest request, String searchType, int tarPage) {
		DBConnection con = new DBConnection();
		ReadDao readDao = new ReadDao(con.getConnection());
		Map<String, Object> searchDataMap = new HashMap<>();
		int searchStartDataPos = 1;
		int searchEndDataPos = tarPage * DISPLAY_DATA_NUM;
		if(tarPage > 1) {
			searchStartDataPos = (tarPage - 1) * DISPLAY_DATA_NUM + 1;
		}
		try {
			switch(searchType) {
				//飼い主の場合
				case "001":
					List<TrimmerInfo> trimmerInfoList = readDao.findTrimmerInfoByPaging(this, searchStartDataPos, searchEndDataPos);
					//画像をBase64化し、map
					for(TrimmerInfo trimmerInfo:trimmerInfoList) {
						trimmerInfo.setStoreImageBase64(convertByteAryToBase64(trimmerInfo.getStoreImage()));
					}
					searchDataMap.put("searchData", trimmerInfoList);
					break;
				//トリマーの場合
				case "002":
					List<OwnerInfo> ownerInfoList = readDao.findOwnerInfoByPaging(this, searchStartDataPos, searchEndDataPos);
					//画像をBase64化し、map
					for(OwnerInfo ownerInfo:ownerInfoList) {
						ownerInfo.setPetImageBase64(convertByteAryToBase64(ownerInfo.getPetImage()));
					}
					searchDataMap.put("searchData", ownerInfoList);
					break;
				default:
					break;
			}
			searchDataMap.put("searchCount", this.searchCount);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			con.close();
		}

		return searchDataMap;
	}
}
