$(document).ready(function(){
    //初期表示時、フォームを非表示
    $('.form-common,.form-owner,.form-trimmer,.form-trimmer-business-hours,.footer-top-content').hide();

	//飼い主区分
    const ownerType = '001';
    //トリマー区分
    const trimmerType = '002';
    //アクション:「登録区分」の値が変更される
    $('#regist-type').change(function() {
        //登録区分の値を取得
        let registType = $(this).val();

        //登録区分の値に応じて、formタグを表示・非表示にする
        if(registType === ownerType){
            //飼い主用フォームを表示
            $('.form-common,.form-owner,.footer-top-content').show();
            $('.form-trimmer,.form-trimmer-business-hours').hide();
            //post送信時、必須項目未入力対応
            $('#pet-name').prop('required', true);
            $('#store-name').prop('required', false);
        }else if(registType === trimmerType){
            //トリマー用フォームを表示
            $('.form-common,.form-trimmer,.footer-top-content').show();
            $('.form-owner,.form-trimmer-business-hours').hide();
            //post送信時、必須項目未入力対応
            $('#pet-name').prop('required', false);
            $('#store-name').prop('required', true);
        }else{
            //フォームを非表示
            $('.form-common,.form-owner,.form-trimmer,.form-trimmer-business-hours,.footer-top-content').hide();
            //post送信時、必須項目未入力対応
            $('#pet-name').prop('required', false);
            $('#store-name').prop('required', false);
        }
      });

    //アクション:「郵便番号」を入力する
    $("#postal-code").change(function(){
        const successStatus = 200
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

$(document).ready(function(){
	//画像サイズを調整
    let targetImgbase64 = $('.resize-img-base64').attr('src');
    if(targetImgbase64 == undefined){
    	return;
    }
    //調整画像サイズ
    const minSize = 300;
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
		$('.resize-img-base64').attr('src', canvas.toDataURL());
    };
    //画像読み込み
    image.src = targetImgbase64;


});