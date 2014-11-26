package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

/**
 * A class used to perform operations on recipients data stored in a database.
 * 
 * @author kuba
 */
public class SurveyManager extends DataManager {

	public SurveyManager(ServletContext context) {
		super(context);
	}

	/**
	 * 
	 * @param title
	 * @param userId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public void addRecord(String title, long userId)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String insert = "INSERT INTO surveys (id, title, user_id) VALUES (DEFAULT, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);

			statement.setString(1, title);
			statement.setLong(2, userId);
			statement.executeUpdate();
		}
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws InvalidPreferencesFormatException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ResultSet findSurveys(long userId) throws SQLException, ClassNotFoundException, IOException, InvalidPreferencesFormatException {
		ResultSet results = null;
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String select = "SELECT id FROM surveys WHERE user_id = ?;";
			PreparedStatement statement = connection.prepareStatement(select);

			statement.setLong(1, userId);
			results = statement.executeQuery();
		}

		return results;
	}

}
