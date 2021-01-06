package com.web01.animatch.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AnimatchFilte implements Filter {

    public AnimatchFilte() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String path = ((HttpServletRequest)request).getServletPath();
		if (excludeStaticContent(path)) {
			chain.doFilter(request, response);
		}
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public boolean excludeStaticContent(String path) throws ServletException {
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
