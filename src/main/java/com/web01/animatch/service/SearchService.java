package com.web01.animatch.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
		setInitAttribute(request);
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
	 * 初期属性設定
	 * @param request リクエストオブジェクト
	 */
	private void setInitAttribute(HttpServletRequest request) {
		DBConnection con = new DBConnection();
		ReadDao readDao = new ReadDao(con.getConnection());
		int searchCount = 0;
		try {
			switch(this.searchType) {
				//飼い主の場合
				case "001":
					List<TrimmerInfo> trimmerInfoList = readDao.findTrimmerInfo();
					searchCount = trimmerInfoList.size();
					//画像をBase64化
					for(TrimmerInfo trimmerInfo:trimmerInfoList) {
						trimmerInfo.setStoreImageBase64(convertByteAryToBase64(trimmerInfo.getStoreImage()));
					}
					request.setAttribute("trimmerInfoList", trimmerInfoList);
					break;
				//トリマーの場合
				case "002":
					List<OwnerInfo> ownerInfoList = readDao.findOwnerInfo();
					searchCount = ownerInfoList.size();
					//画像をBase64化
					for(OwnerInfo ownerInfo:ownerInfoList) {
						ownerInfo.setPetImageBase64(convertByteAryToBase64(ownerInfo.getPetImage()));
					}
					request.setAttribute("ownerInfoList", ownerInfoList);
					break;
				default:
					break;
			}
			request.setAttribute("searchCount", searchCount);
			request.setAttribute("searchType", this.searchType);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			con.close();
		}
	}
}
