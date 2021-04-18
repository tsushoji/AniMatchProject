const adjustImgbase64 = function(imgSize) {
	$('.resize-img-base64').each(function(){
	  	//画像サイズを調整
	  	let tarDom = $(this);
		let targetImgbase64 = tarDom.attr('src');
		//デバッグ用
		console.log(targetImgbase64);
	    if(targetImgbase64 == undefined){
	    	return;
	    }
	    //調整画像サイズ
	    const minSize = imgSize;
	    //CanvasAPI
	    let canvas = document.createElement('canvas');
	    let ctx = canvas.getContext('2d');
	    let image = new Image();
	    image.crossOrigin = 'Anonymous';
	    //画像読み込み後、実行
	    image.onload = function() {
	    	let dstWidth, dstHeight;
	    	if (this.width > this.height) {
			    dstWidth = minSize;
			    dstHeight = this.height * minSize / this.width;
			} else {
			    dstHeight = minSize;
			    dstWidth = this.width * minSize / this.height;
			}
			canvas.width = dstWidth;
			canvas.height = dstHeight;
			ctx.drawImage(this, 0, 0, this.width, this.height, 0, 0, dstWidth, dstHeight);
			//画像サイズ調整済みbase64文字列に更新
			tarDom.attr('src', canvas.toDataURL());
	    };
	    //画像読み込み
	    image.src = targetImgbase64;
    });
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
