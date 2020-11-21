$(document).ready(function(){

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-search-owner').click(function () {
        // 同じタグで表示
        location.href = '/animatch/search/owner';
    });

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-search-trimmer').click(function () {
        // 同じタグで表示
        location.href = '/animatch/search/trimmer';
    });

    //アクション:「main-center-block」のダイレクトメッセージブロックをクリック
    $('.is-show-dmessage').click(function () {
        // 同じタグで表示
        location.href = '/animatch/dmessage/contact-inf';
    });


});