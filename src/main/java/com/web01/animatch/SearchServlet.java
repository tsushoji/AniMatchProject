package com.web01.animatch;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = getJspPath(request);
		if(path.isEmpty()) {
			return;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	String getJspPath(HttpServletRequest request) {
		String urlPattern = request.getHttpServletMapping().getPattern();
		String path;
		if(urlPattern.contains("owner")) {
			path = "/WEB-INF/jsp/search/search_owner.jsp";
		}else if(urlPattern.contains("trimmer")) {
			path = "/WEB-INF/jsp/search/search_trimmer.jsp";
		}else {
			path = "";
		}
		return path;
	}
}
