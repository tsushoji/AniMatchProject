$(document).ready(function(){
    //アクション:「絞り込みクリア」ボタン押下
    $('.main-search-left-clear').click(function() {
        let $form = $(this).parents('.container-fluid').find('.main-search-left-form');
        $form.find('select').val('000');

        $form.find('input[name="form-start-time"],input[name="form-end-time"]').val('');

        $form.find('ul li').removeClass('active');
    });

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-details-owner').click(function () {
        // 同じタグで表示
        location.href = '/animatch/member/detail/owner';
    });

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-details-trimmer').click(function () {
        // 同じタグで表示
        location.href = '/animatch/member/detail/trimmer';
    });

    //アクション:「都道府県」を入力する
    //都道府県コード https://postcode.teraren.com/prefectures
    $("#prefectures").change(function(){
    	let paramPrefectures = $(this).val().replace(/^0+/, '');
    	//追加したDomを削除
    	$('.api-append-cities').remove();
    	$.getJSON("https://postcode.teraren.com/prefectures/" + paramPrefectures + ".json", function(json){
    		console.log('成功');
    		console.log(json);
    		targetSelect = $('#cities');
    		//追加したDomを区別するため、追加したDomに「api-append-cities」クラスを付与
    		$.each(json, function (index, val) {
			    appendOption = $('<option>')
			        			.val(val.city)
			        			.text(val.city)
			        			.addClass("api-append-cities")
			    targetSelect.append(appendOption);
			});
	    }).fail(function(jqXHR, textStatus, errorThrown) {
	    	console.log(XMLHttpRequest);
	    });
    });

	//画像サイズを調整
	adjustImgbase64(80);

});