package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

public class ProductManager extends DataManager{

	public ProductManager(ServletContext context) {
		super(context);
	}
	
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param picture
	 * @param surveyId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public void addRecord(String name,
			String description, InputStream picture, long surveyId) throws ClassNotFoundException, SQLException,
			IOException, InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String insert = "INSERT INTO products (id, name, description, picture, survey_id) VALUES (DEFAULT, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);

			
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setBinaryStream(3, picture);
			statement.setLong(4, surveyId);
			statement.executeUpdate();
		}
	}
	
	/**
	 * 
	 * @param surveyId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public ResultSet findInfo(long surveyId) throws ClassNotFoundException, SQLException,
			IOException, InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);
		ResultSet results = null;
		
		try (Connection connection = dbAccess.connect();) {
			String select = "SELECT name, description, picture FROM products WHERE survey_id = ?;";
			PreparedStatement statement = connection.prepareStatement(select);
		
			statement.setLong(1, surveyId);
			results = statement.executeQuery();
		}
		
		return results;
	}
}
