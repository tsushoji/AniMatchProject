const setCitiesSelectBox = function(paramPrefectures){
	//追加したDomを削除
	$('.api-append-cities').remove();
	$.getJSON("https://postcode.teraren.com/prefectures/" + paramPrefectures + ".json", function(json){
		console.log('成功');
		console.log(json);
		targetSelect = $('#cities');
		//追加したDomを区別するため、追加したDomに「api-append-cities」クラスを付与
		$.each(json, function (index, val) {
			//入力されている市区町村に一致している場合、「selected」属性を付与
			if(val.city == $('#form-cities').val()){
				appendOption = $('<option>')
		        			.val(val.city)
		        			.text(val.city)
		        			.prop('selected', true)
		        			.addClass("api-append-cities")
			}else{
				appendOption = $('<option>')
		        			.val(val.city)
		        			.text(val.city)
		        			.addClass("api-append-cities")
			}
		    targetSelect.append(appendOption);
		});
    }).fail(function(jqXHR, textStatus, errorThrown) {
    	console.log(XMLHttpRequest);
    });
}


$(document).ready(function(){
    //アクション:「絞り込みクリア」ボタン押下
    $('#main-search-left-clear').click(function() {
        let $leftForm = $(this).parents('.container-fluid').find('.main-search-left-form');
        $leftForm.find('select').val('000');

        $leftForm.find('#user-id, #businessHours-start-time, #businessHours-end-time').val('');

        $leftForm.find('ul li').removeClass('active');
        $leftForm.find('ul li').removeClass('left-side');
        $leftForm.find('ul li').removeClass('right-side');
        $leftForm.find('ul li').removeClass('center-side');
        $leftForm.find('input[name="business-hours"]').val('');
    });

	//アクション:「検索」、「絞り込み」ボタン押下
    $('.filter-btn').click(function() {
		//飼い主区分
    	const owner = '001';
    	//トリマー区分
    	const trimmer = '002';

		let reqURL = "/animatch/member/search/";
		let inputCount = 0;

		let searchType = $('#search-type').val();

		if(searchType === owner){
			reqURL += "owner";
			let businessHoursWeekday = $('input[name="business-hours"]').val();
			if(businessHoursWeekday){
				reqURL = setReqURLParam(reqURL, 'businessHoursWeekday', businessHoursWeekday);
				inputCount++;
			}
			let businessHoursStartTime = $('#businessHours-start-time').val();
			if(businessHoursStartTime){
				reqURL = setReqURLParam(reqURL, 'businessHoursStartTime', businessHoursStartTime);
				inputCount++;
			}
			let businessHoursEndTime = $('#businessHours-end-time').val();
			if(businessHoursEndTime){
				reqURL = setReqURLParam(reqURL, 'businessHoursEndTime', businessHoursEndTime);
				inputCount++;
			}
		}else if(searchType === trimmer){
			reqURL += "trimmer";
			let petType = $('#type-pet option:selected').val();
			if(petType){
				reqURL = setReqURLParam(reqURL, 'petType', petType);
				inputCount++;
			}
			let petSex = $('#sex-pet option:selected').val();
			if(petSex){
				reqURL = setReqURLParam(reqURL, 'petSex', petSex);
				inputCount++;
			}
		}

		let userId = $('#user-id').val();
		if(userId){
			reqURL = setReqURLParam(reqURL, 'userId', userId);
			inputCount++;
		}
		let prefectures = $('#prefectures option:selected').val();
		if(prefectures){
			reqURL = setReqURLParam(reqURL, 'prefectures', prefectures)
			inputCount++;;
		}
		let cities = $('#cities option:selected').val();
		if(prefectures){
			reqURL = setReqURLParam(reqURL, 'cities', cities);
			inputCount++;
		}

		let searchContents = $('search-contents').val();
		if(searchContents){
			reqURL = setReqURLParam(reqURL, 'searchContents', searchContents);
			inputCount++;
		}

		if(inputCount > 0){
			location.href = reqURL;
		}
    });

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-details-owner').click(function () {
    	trimmerUserId = $(this).find('.trimmer-user-id').val();
    	if(trimmerUserId === undefined){
        	return;
        }
        // 同じタグで表示
        location.href = '/animatch/member/detail/owner/' + trimmerUserId;
    });

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-details-trimmer').click(function () {
    	ownerUserId = $(this).find('.owner-user-id').val();
    	if(ownerUserId === undefined){
        	return;
        }
        // 同じタグで表示
        location.href = '/animatch/member/detail/trimmer/' + ownerUserId;
    });

    //アクション:「都道府県」を入力する
    //都道府県コード https://postcode.teraren.com/prefectures
    $("#prefectures").change(function(){
    	setCitiesSelectBox($(this).val().replace(/^0+/, ''));
    });

	const targetPageParamName = '?targetPage=';
	const startPageParamName = '&startPage=';
	let targetPageParamVal = 1;
	let startPageParamVal = 1;

	let currentPage = Number($("#current-page").val());

	let startPageIndex = Number($("#display-start-page-index").val());
	let endPageIndex = Number($("#display-end-page-index").val());
	let endPage = Number($("#end-page").val());

	//アクション:ページリンク前へボタンをクリック
    $('#page-item-pre').click(function () {
    	let pageItemPreURL = $("#request-url").val();
    	if(currentPage > 0){
    		targetPageParamVal = currentPage;
    		startPageParamVal = startPageIndex;
    		if(currentPage > 1){
	    		targetPageParamVal = currentPage - 1;
	    	}
    		if(startPageIndex > targetPageParamVal){
	    		startPageParamVal = startPageIndex - 1;
	    	}
    		pageItemPreURL += targetPageParamName + targetPageParamVal + startPageParamName + startPageParamVal;
    		console.log(pageItemPreURL);
	        // 同じタグで表示
	        location.href = pageItemPreURL;
    	}
    });

	//アクション:ページリンク次へボタンをクリック
    $('#page-item-next').click(function () {
    	let pageItemNextURL = $("#request-url").val();
    	if(currentPage < endPage){
    		targetPageParamVal = currentPage + 1;
    		startPageParamVal = startPageIndex;
    		if(endPageIndex < targetPageParamVal){
    			startPageParamVal = startPageIndex + 1;
    		}
    		pageItemNextURL += targetPageParamName + targetPageParamVal + startPageParamName + startPageParamVal;
    		console.log(pageItemNextURL);
	        // 同じタグで表示
	        location.href = pageItemNextURL;
    	}
    });

	//ページリンクactive状態設定
	$('#page-item-' + currentPage).addClass('active');

	//ページリンクdisable状態設定
	if(currentPage === 1){
		$('#page-item-pre').addClass('disabled');
	}
	if(currentPage === endPage){
		$('#page-item-next').addClass('disabled');
	}

	//「都道府県」がデフォルト値以外で入力された場合
	let targetPrefecturesVal = $("#prefectures").val();
	if(targetPrefecturesVal != '000'){
    	setCitiesSelectBox(targetPrefecturesVal.replace(/^0+/, ''));
	}
});