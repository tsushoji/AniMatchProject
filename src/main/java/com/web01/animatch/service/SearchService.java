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
	/**
	 * 検索件数
	 */
	private Integer searchCount = 0;
	/**
	 * ページング成功可否
	 */
	private boolean isPaging = true;

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
	 * 表示ページ数
	 */
	// 1より大きい値設定
	private static final int DISPLAY_PAGE_COUNT = 5;

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
	 * ページング属性設定
	 * @param request リクエストオブジェクト
	 * @return ページング成功可否
	 */
	public boolean setPageAttribute(HttpServletRequest request, int tarPage, int startPageIndex) {
		setInitPropertiesKey(request);
		request.setAttribute("searchType", this.searchType);
		setSearchData(request, tarPage);
		if(this.isPaging) {
			setPageLink(request, tarPage, startPageIndex);
		}
		return this.isPaging;
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
		petTypeKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));
		weekdayKeyList.sort(Comparator.comparingInt(key -> Integer.parseInt(key.substring(key.length() - 3))));

		request.setAttribute("prefecturesKeyList", prefecturesKeyList);
		request.setAttribute("petTypeKeyList", petTypeKeyList);
		request.setAttribute("weekdayKeyList", weekdayKeyList);
	}

	/**
	 * 検索データ設定
	 * @param request リクエストオブジェクト
	 * @param tarPage 遷移するページ番号
	 */
	private void setSearchData(HttpServletRequest request, int tarPage) {
		DBConnection con = new DBConnection();
		ReadDao readDao = new ReadDao(con.getConnection());
		// 初期ページ番号より小さいパラメータページ番号が渡されたときの対策
		int searchStartDataPos = 1;
		if(searchStartDataPos > tarPage) {
			this.isPaging = false;
			return;
		}
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
					request.setAttribute("trimmerInfoList", trimmerInfoList);
					break;
				//トリマーの場合
				case "002":
					List<OwnerInfo> ownerInfoList = readDao.findOwnerInfoByPaging(this, searchStartDataPos, searchEndDataPos);
					//画像をBase64化し、map
					for(OwnerInfo ownerInfo:ownerInfoList) {
						ownerInfo.setPetImageBase64(convertByteAryToBase64(ownerInfo.getPetImage()));
					}
					request.setAttribute("ownerInfoList", ownerInfoList);
					break;
				default:
					break;
			}
			request.setAttribute("searchCount", this.searchCount);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			con.close();
		}
	}

	/**
	 * ページリンク設定
	 * @param request リクエストオブジェクト
	 * @param tarPage 遷移するページ番号
	 */
	private void setPageLink(HttpServletRequest request, int tarPage, int startPageIndex) {
		int endPage = this.searchCount == 0?1:(this.searchCount%DISPLAY_DATA_NUM == 0?this.searchCount/DISPLAY_DATA_NUM:this.searchCount/DISPLAY_DATA_NUM+1);
		// 最終ページ番号より大きいパラメータページ番号が渡されたときの対策
		if(endPage < tarPage) {
			this.isPaging = false;
			return;
		}
		// 最終ページ番号以上のパラメータ開始ページインデックス番号が渡されたまたは1より小さい開始ページインデックス番号が渡されたときの対策
		if(endPage < startPageIndex || startPageIndex < 1) {
			this.isPaging = false;
			return;
		}
		int displayStartPageIndex = startPageIndex;
		int displayEndPageIndex = displayStartPageIndex + DISPLAY_PAGE_COUNT - 1;
		if(displayEndPageIndex > endPage) {
			int diffIndex = displayEndPageIndex - endPage;
			displayEndPageIndex -= diffIndex;
		}
		// ページリンクに使用
		request.setAttribute("displayStartPageIndex", displayStartPageIndex);
		request.setAttribute("displayEndPageIndex", displayEndPageIndex);
		request.setAttribute("currentPage", tarPage);
		request.setAttribute("endPage", endPage);
	}
}
