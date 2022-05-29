$(document).ready(function(){
 //初期表示時、フォームを非表示
 $('.form-common,.form-owner,.form-trimmer,.form-trimmer-business-hours,.footer-top-content').hide();

 //飼い主区分
 const ownerType = '001';
 //トリマー区分
 const trimmerType = '002';

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
   //「都道府県」を表示
   // JIS X 0401都道府県コード
   $('#prefectures').val(('000' + res.results[0].prefcode).slice(-3));
   console.log(('000' + res.results[0].prefcode).slice(-3));
   console.log(res.results);

   //「市区町村」を表示
   $('#cities').val(res.results[0].address2);
  }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
   console.log(XMLHttpRequest);
  });
 });

 // ajax完了後
 $(document).ajaxComplete(function(){
  //登録されている「市区町村」の値以外に変更されたとき、入力欄を赤色にする
  let registedId = "#registed-";
  registedId += $('#cities').attr('id');
  if($('#cities').val() === $(registedId).val()){
   $('#cities').removeClass("test");
  }else{
   $('#cities').addClass("test");
  }
 });

 //アクション:入力値の値が変更される
 $('.check-changed-input').change(function() {
  console.log("変更");
  let targetEle = $(this);
  console.log(targetEle);
  let tarVal = "";
  let registedVal = "";

  //入力値を取得
  let tagName = targetEle[0].tagName;
  let inputType = targetEle.attr('type');
  let registedId = "#registed-";
  tarVal = targetEle.val();
  registedId += targetEle.attr('id');
  console.log(registedId);
  registedVal = $(registedId).val();
  console.log(tarVal);
  console.log(registedVal);
  console.log(targetEle.attr('type'));
  console.log(targetEle[0].tagName);
  //入力値が変更されたとき、入力欄を赤色にする
  if(tarVal === registedVal){
   targetEle.removeClass("test");
   console.log("削除");
  }else{
   targetEle.addClass("test");
   console.log("追加");
  }
 });

 //アクション:性別が変更される
 $('form.account-change-form input[name="radio-user-sex"]:radio').change(function() {
  console.log("変更");
  let targetEle = $(this);
  console.log(targetEle);

  //入力値を取得
  let registedId = "#registed-";
  tarVal = targetEle.val();
  registedId += targetEle.attr('name');
  console.log(registedId);
  registedVal = $(registedId).val();
  console.log(tarVal);
  console.log(registedVal);
  //入力値が変更されたとき、入力欄を赤色にする
  if(tarVal !== registedVal){
   targetEle.addClass("test");
   console.log("追加");
  }
  $('form.account-change-form input[name="radio-user-sex"]').each(function(i){
   let tarEle = $(this);
   let isChecked = tarEle.prop('checked');
   if(!isChecked){
    tarEle.removeClass("test");
   }
  });
  console.log("削除");
 });

 //アクション:ペット性別が変更される
 $('form.account-change-form input[name="radio-pet-sex"]:radio').change(function() {
  console.log("変更");
  let targetEle = $(this);
  console.log(targetEle);

  //入力値を取得
  let registedId = "#registed-";
  tarVal = targetEle.val();
  registedId += targetEle.attr('name');
  console.log(registedId);
  registedVal = $(registedId).val();
  console.log(tarVal);
  console.log(registedVal);
  //入力値が変更されたとき、入力欄を赤色にする
  if(tarVal !== registedVal){
   targetEle.addClass("test");
   console.log("追加");
  }
  $('form.account-change-form input[name="radio-pet-sex"]').each(function(i){
   let tarEle = $(this);
   let isChecked = tarEle.prop('checked');
   if(!isChecked){
    tarEle.removeClass("test");
   }
  });
  console.log("削除");
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