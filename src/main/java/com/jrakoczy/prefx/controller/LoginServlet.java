package com.jrakoczy.prefx.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.RepositoryIdHelper;

import com.jrakoczy.prefx.model.UserManager;
import com.jrakoczy.prefx.utils.Encryption;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String emailParam = "email";
	private static final String passwordParam = "password";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void loginUser(HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException,
			ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException, ServletException {
		String email = request.getParameter(emailParam);
		String password = request.getParameter(passwordParam);

		if (verifyUser(email, password)) {
			Cookie sessionCookie = new Cookie("user", email);
			sessionCookie.setMaxAge(30 * 60);
			response.addCookie(sessionCookie);
			response.sendRedirect("overview.html");
		} else {
			response.sendRedirect("index.html");
		}
	}

	private boolean verifyUser(String email, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException,
			SQLException, IOException, InvalidPreferencesFormatException {
		ServletContext context = getServletContext();
		UserManager userManager = new UserManager(context);
		String rqPasswordHash = Encryption.hashSha256(password);

		ResultSet dbResult = userManager.findPasswordHash(email);
		String dbPasswordHash = dbResult.getString("password_hash");

		return rqPasswordHash.equals(dbPasswordHash);
	}
}
