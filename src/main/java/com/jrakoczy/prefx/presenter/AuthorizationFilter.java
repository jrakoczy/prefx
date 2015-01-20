package com.jrakoczy.prefx.presenter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
public class AuthorizationFilter implements Filter {

	private String[] omittedPatterns;

	public AuthorizationFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String uri = req.getRequestURI();

		if (session == null && !isUnauthorized(uri))
			resp.sendRedirect("login.html");
		else
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		String omitted = config.getInitParameter("omitted");
		String delimiter = System.getProperty("line.separator");
		String[] delimitedArray = omitted.split(delimiter);
		omittedPatterns = new String[delimitedArray.length];

		for (int i = 0; i < delimitedArray.length; ++i)
			omittedPatterns[i] = delimitedArray[i].trim();
	}

	
	/**
	 * 
	 * @param uri
	 * @return
	 */
	private boolean isUnauthorized(String uri) {
		boolean unauthorized = false;

		for (String pattern : omittedPatterns)
			if (uri.contains(pattern))
				unauthorized = true;

		return unauthorized;
	}

}
