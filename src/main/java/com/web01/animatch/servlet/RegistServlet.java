package com.web01.animatch.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.logic.RegistLogic;

@MultipartConfig
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegistLogic registLogic = new RegistLogic();
		registLogic.setProperties(request);
		String path = "/WEB-INF/jsp/regist/regist.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String registType = request.getParameter("regist-type");
		RegistLogic registLogic = new RegistLogic(registType);
		if(!registLogic.regist(request)) {
			doGet(request, response);
		}else {
			String path = "/WEB-INF/jsp/regist/regist_complete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
	}
}
