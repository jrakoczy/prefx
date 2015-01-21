package com.jrakoczy.prefx.presenter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrakoczy.prefx.model.UserManager;
import com.jrakoczy.prefx.utils.Encryption;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			registerUser(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPreferencesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registerUser(HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException,
			IOException, InvalidPreferencesFormatException, SQLException,
			NoSuchAlgorithmException, ServletException {
		ServletContext context = getServletContext();
		UserManager userManager = new UserManager(context);

		String email = request.getParameter(emailParam);
		String password = request.getParameter(passwordParam);

		if (email != null && password != null) {
			String passwordHash = Encryption.hashSha256(password);
			userManager.insertRecord(email, passwordHash);
		}

		RequestDispatcher rd = request.getRequestDispatcher("login.html");
		rd.forward(request, response);
	}

}
