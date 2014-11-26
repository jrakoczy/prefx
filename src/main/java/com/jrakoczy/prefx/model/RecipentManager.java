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
public class RecipentManager extends DataManager {

	/**
	 * Creates a new {@code RecipentManager} instance using a given
	 * {@code ServletContext}.
	 * 
	 * @param context
	 */
	public RecipentManager(ServletContext context) {
		super(context);
	}

	/**
	 * 
	 * @param email
	 * @param surveyId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public void addRecord(String email, int surveyId)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String insert = "INSERT INTO recipents (id, email, survey_id) VALUES (DEFAULT, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);

			statement.setString(1, email);
			statement.setInt(2, surveyId);
			statement.executeUpdate();
		}
	}

	/**
	 * 
	 * @param email
	 * @param surveyId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public ResultSet findId(String email, int surveyId)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		ResultSet results = null;
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String select = "SELECT id FROM recipents WHERE email = ? AND survey_id = ?;";
			PreparedStatement statement = connection.prepareStatement(select);

			statement.setString(1, email);
			statement.setInt(2, surveyId);
			results = statement.executeQuery();
		}

		return results;
	}

}
