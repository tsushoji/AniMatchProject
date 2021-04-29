<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<body>
		<!--ナビゲーショントグル-->
		<nav class="navbar navbar-expand-md navbar-light bg-white">
	         <a class="navbar-brand" href="/animatch/index">
	             <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">
	         </a>
	         <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-supported-content" aria-controls="navbar-supported-content" aria-expanded="false" aria-label="Toggle navigation">
	             <span class="navbar-toggler-icon"></span>
	         </button>

	         <div class="collapse navbar-collapse collapse-item" id="navbar-supported-content">
	             <ul class="navbar-nav mr-auto">
	                 <li class="nav-item"><a class="nav-link" href="/animatch/member/search/owner?targetPage=1&startPage=1">お店を探す<img src="/animatch/images/icon_search.png" alt="検索アイコン"></a></li>
	                 <li class="nav-item"><a class="nav-link" href="/animatch/member/search/trimmer?targetPage=1&startPage=1">飼い主を探す<img src="/animatch/images/icon_search.png" alt="検索アイコン"></a></li>
	             </ul>

	             <ul class="navbar-nav">
	                 <li class="nav-item ml-auto mr-md-3 collapse-item-btn-search"><a class="nav-link btn btn-outline-info" type="submit" href="/animatch/signup/">新規会員登録</a></li>
	                 <li class="nav-item ml-auto mr-md-2 collapse-item-btn-login"><a class="nav-link btn btn-outline-success" type="submit" target="_blank" href="/animatch/login/">ログイン</a></li>
	             </ul>
	         </div>
	    </nav>
	</body>

</html>