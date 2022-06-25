const setChangeItemAttributeByInputOrSelect = function(targetEle, registedVal, className){
 if(typeof registedVal === "undefined"){
  registedVal = '';
 }
 let tarVal = targetEle.val();
 //入力値が変更されたとき、クラス属性付与
 if(tarVal === registedVal){
  targetEle.removeClass(className);
 }else{
  targetEle.addClass(className);
 }
}

const setChangeItemAttributeByInitDisplayBusinessHoursInput = function(targetEle, targetVal, registedVal, className){
 if(typeof registedVal === "undefined"){
  registedVal = '';
 }
 //入力値が変更されたとき、クラス属性付与
 if(targetVal === registedVal){
  targetEle.removeClass(className);
 }else{
  targetEle.addClass(className);
 }
}

const setChangeItemAttributeByInputPassword = function(targetEle, registedVal, className){
 if(typeof registedVal === "undefined"){
  registedVal = '';
 }
 let tarVal = targetEle.val();
 //入力値が変更されたとき、クラス属性を付与
 //パスワード表示要素も変更
 let tarTextEle = targetEle.next();
 let tarTextEleTagName = tarTextEle.get(0).tagName;
 if(tarTextEleTagName !== 'INPUT'){
  tarTextEle = targetEle.prev();
 }
 if(tarVal === registedVal){
  targetEle.removeClass(className);
  tarTextEle.removeClass(className);
 }else{
  targetEle.addClass(className);
  tarTextEle.addClass(className);
 }
}

const setChangeItemCssByWeekday = function(targetEle, registedIndexVal, attributeName, attributeValue){
 let classNameAry = [''];
 let className = targetEle.attr('class');
 if(typeof className !== 'undefined'){
  classNameAry = className.split(' ');
 }
 let targetEleIndex = targetEle.index();
 let isOn = false;
 if($.inArray('active',classNameAry)){
  isOn = true;
 }
 let registedIndexAry = [-1];
 if(typeof registedIndexVal !== "undefined"){
  let tempRegistedIndexAry = registedIndexVal.split(',');
  // 配列要素の文字列を数値に変換
  let tempWeekdayAry =['0','1','2','3','4','5','6'];
  let registedIndexAryIndex = 0;
  if(isOn){
   registedIndexAry = new Array(7-(tempRegistedIndexAry.length));
   $.each(tempWeekdayAry, function(index, val) {
    if($.inArray(val,tempRegistedIndexAry) === -1){
     registedIndexAry[registedIndexAryIndex] = Number(val);
     registedIndexAryIndex++;
    }
   });
  }else{
   registedIndexAry = new Array(tempRegistedIndexAry.length);
   $.each(tempWeekdayAry, function(index, val) {
    if($.inArray(val,tempRegistedIndexAry) !== -1){
     registedIndexAry[registedIndexAryIndex] = Number(val);
     registedIndexAryIndex++;
    }
   });
  }
 }

 //multipickerにより、class属性が削除されてしまうため、style属性で代用
 if($.inArray(targetEleIndex,registedIndexAry) !== -1){
  targetEle.css(attributeName,'');
 }else{
  targetEle.css(attributeName,attributeValue);
 }
}

const setChangeItemAttributeBySex = function(targetEle, registedVal, radioInputName, className){
 let tarVal = targetEle.val();
 let tarForName = targetEle.attr('id');
 let changeEle = $('label[for="' + tarForName + '"]');
 //入力値が変更されたとき、クラス属性を付与
 if(tarVal !== registedVal){
  changeEle.addClass(className);
 }
 $(radioInputName).each(function(i){
  let tarEle = $(this);
  let isChecked = tarEle.prop('checked');
  if(!isChecked){
   tarForName = tarEle.attr('id');
   changeEle = $('label[for="' + tarForName + '"]');
   changeEle.removeClass(className);
  }
 });
}

$(document).ready(function(){
 //初期表示時、フォームを非表示
 $('.form-common,.form-owner,.form-trimmer,.form-trimmer-business-hours,.footer-top-content').hide();

 //飼い主区分
 const ownerType = '001';
 //トリマー区分
 const trimmerType = '002';

 //登録されたタグID先頭
 const registedIdPrefix = "#registed-";
 //背景色赤色クラス属性名
 const redBackGroundClassName = "bg-warning";

 const userNameId = 'user-name';
 const passwordId = 'password';
 const birthdayId = 'birthday';
 const postalCodeId = 'postal-code';
 const prefecturesId = 'prefectures';
 const citiesId = 'cities';
 const emailAddressId = 'email-address';
 const telephoneNumId = 'telephone-number';
 const userSexEleName = 'form.account-change-form input[name="radio-user-sex"]:radio';
 const userSexRadioInputName = 'form.account-change-form input[name="radio-user-sex"]';
 const petSexEleName = 'form.account-change-form input[name="radio-pet-sex"]:radio';
 const petSexRadioInputName = 'form.account-change-form input[name="radio-pet-sex"]';

 const petNameId = 'pet-name';
 const petTypeId = 'pet-type';
 const petWeightId = 'pet-weight';
 const petRemarksId = 'pet-remarks';

 const storeNameId = 'store-name';
 const storeEmployeesId = 'store-employees';
 const courseInfoId = 'course-info';
 const commitmentId = 'commitment';

 const formBusinessHoursId = 'form-business-hours';
 const businessStartHoursPartId = 'business-hours-start-time-';
 const businessEndHoursPartId = 'business-hours-end-time-';
 const businessHoursRemarksPartId = 'business-hours-remarks-';

 //アクション:「郵便番号」を入力する
 $("#postal-code").change(function(){
  const successStatus = 200;
  let paramPostalCode = $(this).val();
  let urlZipCloud = 'https://zip-cloud.appspot.com/api/search?zipcode=';

  $.ajax({
   type: 'GET',
   cache: false,
   url: urlZipCloud + paramPostalCode,
   dataType: 'jsonp',
  }).done(function (res) {
   //エラーだった時
   if (res.status !== successStatus) {
    //エラー内容を表示
    console.log(res.message);
    return;
   }
   //処理が成功したとき
   if(res.results !== null){
    //「都道府県」を表示
    // JIS X 0401都道府県コード
    $('#prefectures').val(('000' + res.results[0].prefcode).slice(-3));

    //「市区町村」を表示
    $('#cities').val(res.results[0].address2);
   }
  }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
   console.log(XMLHttpRequest);
  });
 });

 // ajax完了後
 $(document).ajaxComplete(function(){
  //登録されている「都道府県」の値以外に変更されたとき、入力欄を赤色にする
  let registedPrefecturesId = registedIdPrefix;
  registedPrefecturesId += "prefectures";
  if($('#prefectures').val() === $(registedPrefecturesId).val()){
   $('#prefectures').removeClass(redBackGroundClassName);
  }else{
   $('#prefectures').addClass(redBackGroundClassName);
  }
  //登録されている「市区町村」の値以外に変更されたとき、入力欄を赤色にする
  let registedCitiesId = registedIdPrefix;
  registedCitiesId += "cities";
  if($('#cities').val() === $(registedCitiesId).val()){
   $('#cities').removeClass(redBackGroundClassName);
  }else{
   $('#cities').addClass(redBackGroundClassName);
  }
 });

 //アクション:入力値(input、select パスワード以外)の値が変更される
 $('.check-changed-input').change(function() {
  let targetEle = $(this);

  //入力値を取得
  let registedId = registedIdPrefix;
  registedId += targetEle.attr('id');
  let registedVal = $(registedId).val();
  setChangeItemAttributeByInputOrSelect(targetEle, registedVal, redBackGroundClassName);
 });

 //アクション:入力値(パスワード)の値が変更される
 $('#password').change(function() {
  let targetEle = $(this);

  //入力値を取得
  let registedId = registedIdPrefix;
  // パスワード表示ボタン押下対策
  registedId += 'password';
  let registedVal = $(registedId).val();
  setChangeItemAttributeByInputPassword(targetEle,registedVal,redBackGroundClassName);
 });

 //アクション:営業曜日入力値の値が変更される
 $('#check-changed-weekday-0, #check-changed-weekday-1, #check-changed-weekday-2, #check-changed-weekday-3, #check-changed-weekday-4, #check-changed-weekday-5, #check-changed-weekday-6').click(function() {
  let targetEle = $(this);
  let registedIndexVal = $(registedIdPrefix + formBusinessHoursId).val();
  setChangeItemCssByWeekday(targetEle,registedIndexVal,'background-color','#ffc107');
 });

 //アクション:性別が変更される
 $(userSexEleName).change(function() {
  let targetElements = $(this);
  let targetEle;
  for(let i = 0; i < targetElements.length; i++){
   if(targetElements[i].checked){
    let targetEleId = targetElements[i].getAttribute('id');
    targetEle = $('#'+targetEleId);
    //入力値を取得
    let registedId = registedIdPrefix;
    registedId += targetEle.attr('name');
    let registedVal = $(registedId).val();
    setChangeItemAttributeBySex(targetEle,registedVal,userSexRadioInputName,redBackGroundClassName);
    break;
   }
  }

 });

 //アクション:ペット性別が変更される
 $(petSexEleName).change(function() {
  let targetElements = $(this);
  let targetEle;
  for(let i = 0; i < targetElements.length; i++){
   if(targetElements[i].checked){
    let targetEleId = targetElements[i].getAttribute('id');
    targetEle = $('#'+targetEleId);
    //入力値を取得
    let registedId = registedIdPrefix;
    registedId += targetEle.attr('name');
    let registedVal = $(registedId).val();
    setChangeItemAttributeBySex(targetEle,registedVal,petSexRadioInputName,redBackGroundClassName);
    break;
   }
  }
 });

 //共通変更項目チェック
 let userNameEle = $('#'+userNameId);
 let registedUserNameVal = $(registedIdPrefix+userNameId).val();
 setChangeItemAttributeByInputOrSelect(userNameEle, registedUserNameVal, redBackGroundClassName);

 let passwordEle = $('#'+passwordId);
 let registedPasswordVal = $(registedIdPrefix+passwordId).val();
 setChangeItemAttributeByInputPassword(passwordEle, registedPasswordVal, redBackGroundClassName);

 let userSexElements = $(userSexEleName);
 let userSexEle = '';
 for(let i = 0; i < userSexElements.length; i++){
  if(userSexElements[i].checked){
   let userSexEleId = userSexElements[i].getAttribute('id');
   userSexEle = $('#'+userSexEleId);
   let registedUserSexVal = $(registedIdPrefix+'radio-user-sex').val();
   setChangeItemAttributeBySex(userSexEle, registedUserSexVal, userSexRadioInputName, redBackGroundClassName);
   break;
  }
 }

 let birthdayEle = $('#'+birthdayId);
 let registedBirthdayVal = $(registedIdPrefix+birthdayId).val();
 setChangeItemAttributeByInputOrSelect(birthdayEle, registedBirthdayVal, redBackGroundClassName);

 let postalCodeEle = $('#'+postalCodeId);
 let registedPostalCodeVal = $(registedIdPrefix+postalCodeId).val();
 setChangeItemAttributeByInputOrSelect(postalCodeEle, registedPostalCodeVal, redBackGroundClassName);

 let prefecturesEle = $('#'+prefecturesId);
 let registedPrefecturesVal = $(registedIdPrefix+prefecturesId).val();
 setChangeItemAttributeByInputOrSelect(prefecturesEle, registedPrefecturesVal, redBackGroundClassName);

 let citiesEle = $('#'+citiesId);
 let registedCitiesVal = $(registedIdPrefix+citiesId).val();
 setChangeItemAttributeByInputOrSelect(citiesEle, registedCitiesVal, redBackGroundClassName);

 let emailAddressEle = $('#'+emailAddressId);
 let registedEmailAddressVal = $(registedIdPrefix+emailAddressId).val();
 setChangeItemAttributeByInputOrSelect(emailAddressEle, registedEmailAddressVal, redBackGroundClassName);

 let telephoneNumEle = $('#'+telephoneNumId);
 let registedTelephoneNumVal = $(registedIdPrefix+telephoneNumId).val();
 setChangeItemAttributeByInputOrSelect(telephoneNumEle, registedTelephoneNumVal, redBackGroundClassName);

 let formRegistType = $('#formRegistType').val();
 if(formRegistType == ownerType){
  //飼い主用フォームを表示
  $('.form-common,.form-owner,.footer-top-content').show();

  //post送信時、必須項目未入力対応
  $('#'+petNameId).prop('required', true);
  $('#'+storeNameId).prop('required', false);

  //飼い主変更項目チェック
  let petNameEle = $('#'+petNameId);
  let registedPetNameVal = $(registedIdPrefix+petNameId).val();
  setChangeItemAttributeByInputOrSelect(petNameEle, registedPetNameVal, redBackGroundClassName);

  let petSexElements = $(petSexEleName);
  let petSexEle = '';
  for(let i = 0; i < petSexElements.length; i++){
   if(petSexElements[i].checked){
    let petSexEleId = petSexElements[i].getAttribute('id');
    petSexEle = $('#'+petSexEleId);
    let registedPetSexVal = $(registedIdPrefix+'radio-pet-sex').val();
    setChangeItemAttributeBySex(petSexEle, registedPetSexVal, petSexRadioInputName, redBackGroundClassName);
    break;
   }
  }

  let petTypeEle = $('#'+petTypeId);
  let registedPetTypeVal = $(registedIdPrefix+petTypeId).val();
  setChangeItemAttributeByInputOrSelect(petTypeEle, registedPetTypeVal, redBackGroundClassName);

  let petWeightEle = $('#'+petWeightId);
  let registedPetWeightVal = $(registedIdPrefix+petWeightId).val();
  setChangeItemAttributeByInputOrSelect(petWeightEle, registedPetWeightVal, redBackGroundClassName);

  let petRemarksEle = $('#'+petRemarksId);
  let registedPetRemarksVal = $(registedIdPrefix+petRemarksId).val();
  setChangeItemAttributeByInputOrSelect(petRemarksEle, registedPetRemarksVal, redBackGroundClassName);

 }else if(formRegistType == trimmerType){
  //トリマー用フォームを表示
  $('.form-common,.form-trimmer,.footer-top-content').show();

  //post送信時、必須項目未入力対応
  $('#'+petNameId).prop('required', false);
  $('#'+storeNameId).prop('required', true);

  //トリマー変更項目チェック
  let storeNameEle = $('#'+storeNameId);
  let registedStoreNameVal = $(registedIdPrefix+storeNameId).val();
  setChangeItemAttributeByInputOrSelect(storeNameEle, registedStoreNameVal, redBackGroundClassName);

  let storeEmployeesEle = $('#'+storeEmployeesId);
  let registedStoreEmployeesVal = $(registedIdPrefix+storeEmployeesId).val();
  setChangeItemAttributeByInputOrSelect(storeEmployeesEle, registedStoreEmployeesVal, redBackGroundClassName);

  let courseInfoEle = $('#'+courseInfoId);
  let registedCourseInfoVal = $(registedIdPrefix+courseInfoId).val();
  setChangeItemAttributeByInputOrSelect(courseInfoEle, registedCourseInfoVal, redBackGroundClassName);

  let commitmentEle = $('#'+commitmentId);
  let registedCommitmentVal = $(registedIdPrefix+commitmentId).val();
  setChangeItemAttributeByInputOrSelect(commitmentEle, registedCommitmentVal, redBackGroundClassName);

  let formInputVal = $('#'+formBusinessHoursId).val();
  for(let i = 0; i <= 6; i++){
   let targetWeekdayEle = $('#check-changed-weekday-'+i);
   let registedWeekdayIndexVal = $(registedIdPrefix + formBusinessHoursId).val();
   setChangeItemCssByWeekday(targetWeekdayEle,registedWeekdayIndexVal,'background-color','#ffc107');

   let businessStartHoursEle = $('#'+businessStartHoursPartId+i);
   let registedFormBusinessStartHoursVal = $(registedIdPrefix+businessStartHoursPartId+i).val();

   let businessEndHoursEle = $('#'+businessEndHoursPartId+i);
   let registedFormBusinessEndHoursVal = $(registedIdPrefix+businessEndHoursPartId+i).val();

   let businessHoursRemarksEle = $('#'+businessHoursRemarksPartId+i);
   let registedFormBusinessHoursRemarksVal = $(registedIdPrefix+businessHoursRemarksPartId+i).val();

   if(formInputVal && $.inArray(String(i),formInputVal.split(',')) !== -1){
    let formBusinessStartHoursVal = $('#' + formBusinessHoursId + '-start-time-' + i).val();
    setChangeItemAttributeByInitDisplayBusinessHoursInput(businessStartHoursEle, formBusinessStartHoursVal, registedFormBusinessStartHoursVal, redBackGroundClassName);

    let formBusinessEndHoursVal = $('#' + formBusinessHoursId + '-end-time-' + i).val();
    setChangeItemAttributeByInitDisplayBusinessHoursInput(businessEndHoursEle, formBusinessEndHoursVal, registedFormBusinessEndHoursVal, redBackGroundClassName);

    let formBusinessHoursRemarksVal = $('#' + formBusinessHoursId + '-remarks-' + i).val();
    setChangeItemAttributeByInitDisplayBusinessHoursInput(businessHoursRemarksEle, formBusinessHoursRemarksVal, registedFormBusinessHoursRemarksVal, redBackGroundClassName);
   }else{
    setChangeItemAttributeByInputOrSelect(businessStartHoursEle,registedFormBusinessStartHoursVal,redBackGroundClassName);
    setChangeItemAttributeByInputOrSelect(businessEndHoursEle,registedFormBusinessEndHoursVal,redBackGroundClassName);
    setChangeItemAttributeByInputOrSelect(businessHoursRemarksEle,registedFormBusinessHoursRemarksVal,redBackGroundClassName);
   }
  }
 }

 //入力間違いの場合、入力値を「multipicker」にセット
 let formInputVal = $('#'+formBusinessHoursId).val();
 if(formInputVal){
  let formInputValAry = formInputVal.split(',');
  $.each(formInputValAry, function(index, value){
   $('.day-val-' + value).show();
   $('#' + businessStartHoursPartId + value).val($('#' + formBusinessHoursId + '-start-time-' + value).val());
   $('#' + businessEndHoursPartId + value).val($('#' + formBusinessHoursId + '-end-time-' + value).val());
   $('#' + businessHoursRemarksPartId + value).val($('#' + formBusinessHoursId + '-remarks-' + value).val());

   if($('#err-' + formBusinessHoursId + '-start-time-' + value).val() === '1'){
    $('#' + businessStartHoursPartId  + value + '-err-msg').removeClass('is-hidden');
   }

   if($('#err-' + formBusinessHoursId + '-end-time-' + value).val() === '1'){
    $('#' + businessEndHoursPartId + value + '-err-msg').removeClass('is-hidden');
   }

   if($('#err-length-' + formBusinessHoursId + '-remarks-' + value).val() === '1'){
    $('#' + businessHoursRemarksPartId + value + '-err-length-msg').removeClass('is-hidden');
   }

   if($('#err-xss-' + formBusinessHoursId + '-remarks-' + value).val() === '1'){
    $('#' + businessHoursRemarksPartId + value + '-err-xss-msg').removeClass('is-hidden');
   }
  });
 }

});