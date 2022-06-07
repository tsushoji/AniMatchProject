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
  let tagName = targetEle[0].tagName;
  let inputType = targetEle.attr('type');
  let registedId = registedIdPrefix;
  let tarVal = targetEle.val();
  registedId += targetEle.attr('id');
  let registedVal = $(registedId).val();
  if(typeof registedVal === "undefined"){
   registedVal = '';
  }
  //入力値が変更されたとき、入力欄を赤色にする
  if(tarVal === registedVal){
   targetEle.removeClass(redBackGroundClassName);
  }else{
   targetEle.addClass(redBackGroundClassName);
  }
 });

 //アクション:入力値(パスワード)の値が変更される
 $('#password').change(function() {
  let targetEle = $(this);

  //入力値を取得
  let tagName = targetEle[0].tagName;
  let inputType = targetEle.attr('type');
  let registedId = registedIdPrefix;
  let tarVal = targetEle.val();
  // パスワード表示ボタン押下対策
  registedId += 'password';
  let registedVal = $(registedId).val();
  if(typeof registedVal === "undefined"){
   registedVal = '';
  }
  //入力値が変更されたとき、入力欄を赤色にする
  //パスワード表示要素も変更
  let tarTextEle = targetEle.next();
  if(typeof tarTextEle === "undefined"){
   tarTextEle = targetEle.prev();
  }
  if(tarVal === registedVal){
   targetEle.removeClass(redBackGroundClassName);
   tarTextEle.removeClass(redBackGroundClassName);
  }else{
   targetEle.addClass(redBackGroundClassName);
   tarTextEle.addClass(redBackGroundClassName);
  }
 });

 //アクション:営業曜日入力値の値が変更される
 $('#check-changed-weekday-0, #check-changed-weekday-1, #check-changed-weekday-2, #check-changed-weekday-3, #check-changed-weekday-4, #check-changed-weekday-5, #check-changed-weekday-6').click(function() {
  let targetEle = $(this);
  let classNameAry = targetEle.attr('class').split(' ');
  let targetEleIndex = targetEle.index();
  let isOn = false;
  if($.inArray('active',classNameAry)){
   isOn = true;
  }
  let registedIndexVal = $(registedIdPrefix + 'form-business-hours').val();
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
   targetEle.css('background-color','');
  }else{
   targetEle.css('background-color','#ffc107');
  }
 });

 //アクション:性別が変更される
 $('form.account-change-form input[name="radio-user-sex"]:radio').change(function() {
  let targetEle = $(this);

  //入力値を取得
  let registedId = registedIdPrefix;
  let tarVal = targetEle.val();
  registedId += targetEle.attr('name');
  let registedVal = $(registedId).val();
  let tarForName = targetEle.attr('id');
  let changeEle = $('label[for="' + tarForName + '"]');
  //入力値が変更されたとき、入力欄を赤色にする
  if(tarVal !== registedVal){
   changeEle.addClass(redBackGroundClassName);
  }
  $('form.account-change-form input[name="radio-user-sex"]').each(function(i){
   let tarEle = $(this);
   let isChecked = tarEle.prop('checked');
   if(!isChecked){
    tarForName = tarEle.attr('id');
    changeEle = $('label[for="' + tarForName + '"]');
    changeEle.removeClass(redBackGroundClassName);
   }
  });
 });

 //アクション:ペット性別が変更される
 $('form.account-change-form input[name="radio-pet-sex"]:radio').change(function() {
  let targetEle = $(this);

  //入力値を取得
  let registedId = registedIdPrefix;
  let tarVal = targetEle.val();
  registedId += targetEle.attr('name');
  let registedVal = $(registedId).val();
  let tarForName = targetEle.attr('id');
  let changeEle = $('label[for="' + tarForName + '"]');
  //入力値が変更されたとき、入力欄を赤色にする
  if(tarVal !== registedVal){
   changeEle.addClass(redBackGroundClassName);
  }
  $('form.account-change-form input[name="radio-pet-sex"]').each(function(i){
   let tarEle = $(this);
   let isChecked = tarEle.prop('checked');
   if(!isChecked){
    tarForName = tarEle.attr('id');
    changeEle = $('label[for="' + tarForName + '"]');
    changeEle.removeClass(redBackGroundClassName);
   }
  });
 });

 let formRegistType = $('#formRegistType').val();
 if(formRegistType == ownerType){
  //飼い主用フォームを表示
  $('.form-common,.form-owner,.footer-top-content').show();
 }else if(formRegistType == trimmerType){
  //トリマー用フォームを表示
  $('.form-common,.form-trimmer,.footer-top-content').show();
 }

 //入力間違いの場合、入力値を「multipicker」にセット
 let formInputVal = $('#form-business-hours').val();
 if(formInputVal){
  let formInputValAry = formInputVal.split(',');
  $.each(formInputValAry, function(index, value){
   $('.day-val-' + value).show();
   $('#business-hours-start-time-' + value).val($('#form-business-hours-start-time-' + value).val());
   $('#business-hours-end-time-' + value).val($('#form-business-hours-end-time-' + value).val());
   $('#business-hours-remarks-' + value).val($('#form-business-hours-remarks-' + value).val());

   if($('#err-form-business-hours-start-time-' + value).val() === '1'){
    $('#business-hours-start-time-' + value + '-err-msg').removeClass('is-hidden');
   }

   if($('#err-form-business-hours-end-time-' + value).val() === '1'){
    $('#business-hours-end-time-' + value + '-err-msg').removeClass('is-hidden');
   }

   if($('#err-length-form-business-hours-remarks-' + value).val() === '1'){
    $('#business-hours-remarks-' + value + '-err-length-msg').removeClass('is-hidden');
   }

   if($('#err-xss-form-business-hours-remarks-' + value).val() === '1'){
    $('#business-hours-remarks-' + value + '-err-xss-msg').removeClass('is-hidden');
   }
  });
 }

});