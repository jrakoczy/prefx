package com.jrakoczy.prefx.presenter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jrakoczy.prefx.model.UserManager;
import com.jrakoczy.prefx.utils.Encryption;

/**
 * Servlet implementation class LoginServlet
 */
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String emailParam = "email";
	private static final String passwordParam = "password";

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			loginUser(request, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.getMessage());
		} catch (InvalidPreferencesFormatException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.getMessage());
		}
	}

	private void loginUser(HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException,
			ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException, ServletException {
		String email = request.getParameter(emailParam);
		String password = request.getParameter(passwordParam);

		if (email != null && password != null
				&& verifyUser(email, password, response)) {
			response.getWriter().println("ok");
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			response.sendRedirect("overview.html");
		} else {
			response.sendRedirect("login.html");
		}
	}

	private boolean verifyUser(String email, String password,
			HttpServletResponse response) throws NoSuchAlgorithmException,
			ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		ServletContext context = getServletContext();
		UserManager userManager = new UserManager(context);
		String rqPasswordHash = Encryption.hashSha256(password);

		ResultSet passwordResSet = userManager.selectPasswordHash(email);
		passwordResSet.next();
		String dbPasswordHash = passwordResSet.getString("password_hash");

		return rqPasswordHash.equals(dbPasswordHash);
	}
}
