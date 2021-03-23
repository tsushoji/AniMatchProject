$(document).ready(function(){

    // contact-inf.html
    //アクション:連絡先のアカウントブロック部分をクリック
    $('.main-center-content-account-block').click(function () {
        // 別タグで表示
        window.open('/animatch/member/dmessage/detail/');

    });

    // message.html
    // ファンクション：トーク履歴最下部へ移動
    const chatFrameSceollDown = function (){
        // スクロールの速度
        const speed = 10; // ミリ秒
        // 移動先を数値で取得
        let position = $('html').offset().top + parseInt($('#chat-frame').css('height'));
        // スムーススクロール
        $('#chat-frame').animate({scrollTop:position}, speed, 'swing');
    };

    //アクション:入力フォームの「メッセージ画像」をクリック
    $('.message-form-send-icon-send').click(function () {
        //入力フォームの内容取得
        //メッセージの入力値取得
        let inputMsg = $('.message-form-area').val().replace(/\r?\n/g,"<br/>");
        //添付したファイルオブジェクトを取得
        let inputFile = $('.message-form-send-file')[0].files[0];
        const sendMsgNotRead = "未読"
        //上記で取得した値が空白の場合、メッセージを出力しない
        if(inputMsg){
            //追加するタグを「addEleMsg」にセット
            let addEleMsg = '<p class="chat-talk mytalk">';
            addEleMsg += '<span class="talk-content">';
            addEleMsg += inputMsg;
            addEleMsg += '</span>';
            addEleMsg += '<span class="talk-date">';
            addEleMsg += sendMsgNotRead;
            addEleMsg += '</span>';
            addEleMsg += '</p>';
            //タグを追加する
            $('#chat-frame').append(addEleMsg);
            //トーク履歴最下部へ移動
            chatFrameSceollDown();
            //入力したメッセージを初期化
            $('.message-form-area').val('');

        }

        if(inputFile){
            const fileReader = new FileReader();
            //読み込み処理が終了した場合
            fileReader.onloadend = function() {
                let addEleFile = '<p class="chat-talk mytalk">';
                addEleFile += '<span class="talk-content-file">';
                addEleFile += '<img src="';
                addEleFile += fileReader.result;
                addEleFile += '" alt="スタンプ絵文字" class="img-responsive thumbnail" data-toggle="modal" data-target="#image_Modal">';
                addEleFile += '</span>';
                addEleFile += '<span class="talk-date">';
                addEleFile += sendMsgNotRead;
                addEleFile += '</span>';
                addEleFile += '<div class="modal fade" id="image_Modal" role="dialog">';
                addEleFile += '<div class="modal-dialog modal-xl">';
                addEleFile += '<div class="modal-content">';
                addEleFile += '<div class="modal-body">';
                addEleFile += '<img src="';
                addEleFile += fileReader.result;
                addEleFile += '" alt="スタンプ絵文字" class="modal-body-img">';
                addEleFile += '</div>';
                addEleFile += '</div>';
                addEleFile += '</div>';
                addEleFile += '</div>';
                addEleFile += '</p>';
                //タグを追加する
                $('#chat-frame').append(addEleFile);
                //トーク履歴最下部へ移動
                chatFrameSceollDown();
                //添付済みファイルエリアを非表示
                $('.message-form-send-file-area').hide();
                //「file.name」のクリア
                $('.message-form-send-file').after('<input type="file" accept=".jpg,.jpeg,.png,.gif" class="message-form-send-new-file"/>');
                $('.message-form-send-file').remove();
                $('.message-form-send-new-file').attr('class','message-form-send-file');
            }
            //Fileオブジェクトを読み込む
            fileReader.readAsDataURL(inputFile);
        }


    });

    //アクション:入力フォームの「ファイル画像」をクリック
    $('.message-form-send-icon-file').click(function () {
        // ファイルダイアログを表示する
        $('.message-form-send-file').click();
        return false;
    });

    //アクション:「ファイル添付」タグの属性が変更された場合
    //file.name初期化で新しく作成した動的な要素に対してもイベントを発生させたいため、「on()」を使用
    $('.footer-block').on('change','.message-form-send-file',function () {
        // 選択されたファイル名を取得する
        if($(this)[0].files[0]){
            let fileName = $(this)[0].files[0].name;
            //上記で取得した値が空白の場合、メッセージを出力しない
            if(fileName){
                //添付済みファイルエリアを表示
                $('.message-form-send-file-area').show();
                // スクロールバーを下部に表示
                // スクロールの速度
                const speedVal = 10;
                // 移動先を数値でセット
                const position = 80;
                $('html').animate({scrollTop:position}, speedVal, "swing");
                //添付済みファイルエリアのファイル名に添付ファイル名をセット
                $('.message-form-send-file-area-name').text(fileName);
            }
        }
    });

    $('.message-form-send-file-area-icon-close').click(function(){
        // 選択されたファイル名を取得する
        if($('.message-form-send-file')[0].files[0]){
            let fileName = $('.message-form-send-file')[0].files[0].name;
            //上記で取得した値が空白の場合、メッセージを出力しない
            if(fileName){
                //添付済みファイルエリアを非表示
                $('.message-form-send-file-area').hide();
                //「file.name」のクリア
                $('.message-form-send-file').after('<input type="file" accept=".jpg,.jpeg,.png,.gif" class="message-form-send-new-file"/>');
                $('.message-form-send-file').remove();
                $('.message-form-send-new-file').attr('class','message-form-send-file');
            }
        }
    });

    //アクション:入力フォームの「スタンプ画像」をクリック
    $('.message-form-send-icon-stamp').click(function(){
        // ダイアログの表示・非表示処理
        $('.message-form-send-stamp-area').toggle();
        // スクロールバーを下部に表示
        // スクロールの速度
        const speedVal = 10;
        // 移動先を数値でセット
        const position = 470;
        $('html').animate({scrollTop:position}, speedVal, "swing");
    });

    //アクション:スタンプダイアログの「スタンプ画像」をクリック
    $('.message-form-send-stamp-area-img').click(function(){
        //スタンプのURL取得
        let inputStmpUrl = $('.message-form-send-stamp-area-img').attr('src');
        //追加するタグを「addEleFile」にセット
        if(inputStmpUrl){
            const sendMsgNotRead = "未読"
            let addEleStmp = '<p class="chat-talk mytalk">';
            addEleStmp += '<span class="talk-content-stamp">';
            addEleStmp += '<img src="';
            addEleStmp += inputStmpUrl;
            addEleStmp += '" alt="スタンプ絵文字">';
            addEleStmp += '</span>';
            addEleStmp += '<span class="talk-date">';
            addEleStmp += sendMsgNotRead;
            addEleStmp += '</span>';
            addEleStmp += '</p>';
            //タグを追加する
            $('#chat-frame').append(addEleStmp);
            //トーク履歴最下部へ移動
            chatFrameSceollDown();
        }
    });


});