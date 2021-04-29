$(document).ready(function(){

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-search-owner').click(function () {
        // 同じタグで表示
        location.href = '/animatch/member/search/owner?targetPage=1&startPage=1';
    });

    //アクション:「main-center-block」の検索ブロックをクリック
    $('.is-show-search-trimmer').click(function () {
        // 同じタグで表示
        location.href = '/animatch/member/search/trimmer?targetPage=1&startPage=1';
    });

    //アクション:「main-center-block」のダイレクトメッセージブロックをクリック
    $('.is-show-dmessage').click(function () {
        // 同じタグで表示
        location.href = '/animatch/member/dmessage/list';
    });


});