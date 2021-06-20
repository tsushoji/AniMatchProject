const setReqURLParam = function(reqURL, paramName, paramValue){
	if(!paramName || !paramValue){
		return reqURL;
	}

	let appendStr = paramName + '=' + paramValue;
	if(reqURL.indexOf('?') != -1){
		return reqURL += '&' + appendStr;
	}else{
		return reqURL += '?' + appendStr;
	}
}

$(document).ready(function(){
    //アクション:「上部へ戻る」をクリック
    $('.move-page-top').click(function() {
        // スクロールの速度
        const speedVal = 400;
        let speed = speedVal; // ミリ秒
        // 移動先を数値で取得
        let position = $('html').offset().top;
        // スムーススクロール
        $('body,html').animate({scrollTop:position}, speed, 'swing');
    });
});
