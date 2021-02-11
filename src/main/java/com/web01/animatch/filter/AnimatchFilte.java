package com.web01.animatch.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * AnimatchHTTPフィルタークラス
 * @author Tsuji
 * @version 1.0
 */
public class AnimatchFilte implements Filter {

	/**
	 * デフォルトコンストラクタ
	 */
    public AnimatchFilte() {
    }

    /**
	 * doFilterメソッド終了、タイムアウト後処理
	 */
	public void destroy() {
	}

	/**
	 * リクエスト/レスポンスのペアがチェーンを通過するたびにコンテナによって、呼び出される処理
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 * @param chain フィルターチェーンオブジェクト
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String path = ((HttpServletRequest)request).getServletPath();
		//静的ファイルである場合
		if (judgeStaticContent(path)) {
			chain.doFilter(request, response);
		}
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    chain.doFilter(request, response);
	}

	/**
	 * フィルターがサービスに組み込まれるときに処理
	 * @param chain フィルター設定オブジェクト
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**
	 * 静的ファイル判定
	 * @param path パス
	 * @return パスファイルが静的ファイルである場合、true<br>
	 * そうでない場合、false
	 */
	public boolean judgeStaticContent(String path) throws ServletException {
		if (path.startsWith("/images/")) {
			return true;
		}
		if (path.startsWith("/scripts/")) {
			return true;
		}
		if (path.startsWith("/styles/")) {
			return true;
		}
		if (path.startsWith("/webjars/")) {
			return true;
		}
		return false;
	}
}
