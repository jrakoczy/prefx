package com.jrakoczy.prefx.presenter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.jrakoczy.prefx.model.UserManager;

/**
 * Servlet implementation class SurveyServlet
 */
public class CreateSurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		UserManager umanager = new UserManager(context);
		ResultSet idResSet;

		try {
			idResSet = umanager.selectId(email);
			idResSet.next();
			long id = idResSet.getLong("id");
		} catch (ClassNotFoundException | SQLException
				| InvalidPreferencesFormatException e) {
			response.getWriter().println("error");
		}
		try {
			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field (input
					// type="text|radio|checkbox|etc", select, etc).
					String fieldName = item.getFieldName();
					String fieldValue = item.getString();
					// ... (do your job here)
				} else {
					// Process form file field (input type="file").
					String fieldName = item.getFieldName();
					String fileName = FilenameUtils.getName(item.getName());
					InputStream fileContent = item.getInputStream();
					// ... (do your job here)
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		}

	}

}
