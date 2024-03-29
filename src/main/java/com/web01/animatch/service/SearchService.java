package com.web01.animatch.service;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mysql.cj.util.StringUtils;
import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.ReadDao;
import com.web01.animatch.dto.OwnerInfo;
import com.web01.animatch.dto.SearchForm;
import com.web01.animatch.dto.TrimmerInfo;
import com.web01.animatch.dto.TrimmerInfoBusinessHours;

/**
 * 検索サービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class SearchService extends BaseService {

 //メンバー
 /**
  * 検索区分
  */
 private UserType searchType;
 /**
  * プロパティーサービスオブジェクト
  */
 private PropertiesService propertiesService;
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
  this.propertiesService = new PropertiesService();
  switch (UserType.getEnumFromEnumName(searchType.toUpperCase())) {
  case OWNER:
   this.searchType = UserType.OWNER;
   break;
  case TRIMMER:
   this.searchType = UserType.TRIMMER;
   break;
  default:
   break;
  }
 }

 /**
  * ページング属性設定
  * @param request リクエストオブジェクト
  * @param targetPage 遷移するページ番号
  * @param startPageIndex ページリンク開始番号
  * @param searchForm 検索フォームオブジェクト
  * @return ページング成功可否
  */
 public boolean setPageAttribute(HttpServletRequest request, int targetPage, int startPageIndex, SearchForm searchForm) {
  setInitPropertiesKey(request);
  String searchTypeId = null;
  switch (this.searchType) {
  case OWNER:
   searchTypeId = UserType.OWNER.getId();
   break;
  case TRIMMER:
   searchTypeId = UserType.TRIMMER.getId();
   break;
  default:
   break;
  }
  request.setAttribute("searchType", searchTypeId);
  setSearchData(request, targetPage, searchForm);
  if (this.isPaging) {
   setPageLink(request, targetPage, startPageIndex, searchForm);
  }
  return this.isPaging;
 }

 /**
  * 初期プロパティ設定
  * @param request リクエストオブジェクト
  */
 private void setInitPropertiesKey(HttpServletRequest request) {
  Map<String, String> prefecturesMap = new HashMap<>();
  Map<String, String> petTypeMap = new HashMap<>();
  Map<String, String> petSexMap = new HashMap<>();
  Map<String, String> weekdayMap = new HashMap<>();
  prefecturesMap = this.propertiesService.getValues(PropertiesService.PREFECTURES_KEY_INIT_STR);
  petTypeMap = this.propertiesService.getValues(PropertiesService.PET_TYPE_KEY_INIT_STR);
  petSexMap = this.propertiesService.getValues(PropertiesService.PET_SEX_KEY_INIT_STR);
  weekdayMap = this.propertiesService.getValues(PropertiesService.WEEKDAY_KEY_INIT_STR);

  request.setAttribute("prefecturesMap", prefecturesMap);
  request.setAttribute("petTypeMap", petTypeMap);
  request.setAttribute("petSexMap", petSexMap);
  request.setAttribute("weekdayMap", weekdayMap);
 }

 /**
  * 検索データ設定
  * @param request リクエストオブジェクト
  * @param targetPage 遷移するページ番号
  * @param searchForm 検索フォームオブジェクト
  */
 private void setSearchData(HttpServletRequest request, int targetPage, SearchForm searchForm) {
  DBConnection con = new DBConnection();
  ReadDao readDao = new ReadDao(con.getConnection());
  // 初期ページ番号より小さいパラメータページ番号が渡されたときの対策
  int searchStartDataPos = 1;
  if (searchStartDataPos > targetPage) {
   this.isPaging = false;
   return;
  }
  int searchEndDataPos = targetPage * DISPLAY_DATA_NUM;
  if (targetPage > 1) {
   searchStartDataPos = (targetPage - 1) * DISPLAY_DATA_NUM + 1;
  }
  try {
   switch (this.searchType) {
   //飼い主の場合
   case OWNER:
    List<TrimmerInfo> trimmerInfoList = readDao
      .findTrimmerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm(this, searchStartDataPos,
        searchEndDataPos, searchForm);

    for (TrimmerInfo trimmerInfo : trimmerInfoList) {
     //画像をBase64化
     trimmerInfo.setStoreImageBase64(convertByteAryToBase64(trimmerInfo.getStoreImage()));

     //表示する営業時間文字列作成
     List<TrimmerInfoBusinessHours> trimmerInfoBusinessHoursList = trimmerInfo
       .getTrimmerInfoBusinessHoursList();
     for (TrimmerInfoBusinessHours trimmerInfoBusinessHours : trimmerInfoBusinessHoursList) {
      //曜日、時間が設定されているデータのみ文字列結合
      String businessDay = trimmerInfoBusinessHours.getBusinessDay();
      LocalTime startBusinessTime = trimmerInfoBusinessHours.getStartBusinessTime();
      LocalTime endBusinessTime = trimmerInfoBusinessHours.getEndBusinessTime();
      if (!StringUtils.isNullOrEmpty(businessDay) && startBusinessTime != null
        && endBusinessTime != null) {
       trimmerInfoBusinessHours.setDisplayBusinessHours(this.propertiesService
         .getValue(PropertiesService.WEEKDAY_KEY_INIT_STR + businessDay));
       trimmerInfoBusinessHours.setDisplayStartBusinessTime(
         startBusinessTime.format(DateTimeFormatter.ofPattern("HH:mm")));
       trimmerInfoBusinessHours.setDisplayEndBusinessTime(
         endBusinessTime.format(DateTimeFormatter.ofPattern("HH:mm")));
      }
     }
    }
    request.setAttribute("trimmerInfoList", trimmerInfoList);
    break;
   //トリマーの場合
   case TRIMMER:
    List<OwnerInfo> ownerInfoList = readDao.findOwnerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm(
      this, searchStartDataPos, searchEndDataPos, searchForm);

    for (OwnerInfo ownerInfo : ownerInfoList) {
     //画像をBase64化
     ownerInfo.setPetImageBase64(convertByteAryToBase64(ownerInfo.getPetImage()));
     //会員検索画面表示文字セット
     ownerInfo.setPetType(this.propertiesService
       .getValue(PropertiesService.PET_TYPE_KEY_INIT_STR + ownerInfo.getPetType()));
     ownerInfo.setPetSex(this.propertiesService
       .getValue(PropertiesService.PET_SEX_KEY_INIT_STR + ownerInfo.getPetSex()));
    }
    request.setAttribute("ownerInfoList", ownerInfoList);
    break;
   default:
    break;
   }
   request.setAttribute("searchCount", this.searchCount);
   request.setAttribute("searchForm", searchForm);
  } catch (SQLException e) {
   e.printStackTrace();
  } finally {
   con.close();
  }
 }

 /**
  * ページリンク設定
  * @param request リクエストオブジェクト
  * @param targetPage 遷移するページ番号
  * @param searchForm 検索フォームオブジェクト
  */
 private void setPageLink(HttpServletRequest request, int targetPage, int startPageIndex, SearchForm searchForm) {
  int endPage = this.searchCount == 0 ? 1
    : (this.searchCount % DISPLAY_DATA_NUM == 0 ? this.searchCount / DISPLAY_DATA_NUM
      : this.searchCount / DISPLAY_DATA_NUM + 1);
  // 最終ページ番号より大きいパラメータページ番号が渡されたときの対策
  if (endPage < targetPage) {
   this.isPaging = false;
   return;
  }
  // 最終ページ番号以上のパラメータ開始ページインデックス番号が渡されたまたは1より小さい開始ページインデックス番号が渡されたときの対策
  if (endPage < startPageIndex || startPageIndex < 1) {
   this.isPaging = false;
   return;
  }
  int displayStartPageIndex = startPageIndex;
  int displayEndPageIndex = displayStartPageIndex + DISPLAY_PAGE_COUNT - 1;
  if (displayEndPageIndex > endPage) {
   int diffIndex = displayEndPageIndex - endPage;
   displayEndPageIndex -= diffIndex;
  }
  // ページリンクに使用
  request.setAttribute("displayStartPageIndex", displayStartPageIndex);
  request.setAttribute("displayEndPageIndex", displayEndPageIndex);
  request.setAttribute("currentPage", targetPage);
  request.setAttribute("endPage", endPage);
 }
}
