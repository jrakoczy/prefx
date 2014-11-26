package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

/**
 * A class used to perform operations on surveys data stored in a database.
 * 
 * @author kuba
 */
public class ResultManager extends DataManager {

	public ResultManager(ServletContext context) {
		super(context);
	}

	/**
	 * 
	 * @param firstProductId
	 * @param secondProductId
	 * @param surveyId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public void addRecord(long firstProductId, long secondProductId,
			long surveyId) throws ClassNotFoundException, SQLException,
			IOException, InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String insert = "INSERT INTO surveys (product1_id, product2_id, survey_id) VALUES (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);

			statement.setLong(1, firstProductId);
			statement.setLong(2, secondProductId);
			statement.setLong(3, surveyId);
			statement.executeUpdate();
		}
	}

	
	/**
	 * 
	 * @param firstProductId
	 * @param secondProductId
	 * @param surveyId
	 * @param value
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public void addValue(long firstProductId, long secondProductId,
			long surveyId, int value) throws ClassNotFoundException,
			SQLException, IOException, InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String update = "UPDATE surveys SET value = ? WHERE product1_id = ? "
					+ "AND product2_id = ? AND survey_id = ?;";
			PreparedStatement statement = connection.prepareStatement(update);

			statement.setLong(1, value);
			statement.setLong(2, firstProductId);
			statement.setLong(3, secondProductId);
			statement.setLong(4, surveyId);
			statement.executeUpdate();
		}
	}

	/**
	 * 
	 * @param firstProductId
	 * @param secondProductId
	 * @param surveyId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public ResultSet findValue(long firstProductId, long secondProductId,
			long surveyId) throws ClassNotFoundException, SQLException,
			IOException, InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);
		ResultSet results = null;
		
		try (Connection connection = dbAccess.connect();) {
			String select = "SELECT value FROM surveys WHERE product1_id = ? "
					+ "AND product2_id = ? AND survey_id = ?;";
			PreparedStatement statement = connection.prepareStatement(select);
		
			statement.setLong(1, firstProductId);
			statement.setLong(2, secondProductId);
			statement.setLong(3, surveyId);
			results = statement.executeQuery();
		}
		
		return results;
	}

}
