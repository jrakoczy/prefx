package com.jrakoczy.prefx.model;

import java.io.IOException;
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
	public void insertRecord(String email, int surveyId)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		
		String query = "INSERT INTO recipents (id, email, survey_id) VALUES (DEFAULT, ?, ?);";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, email);
				statement.setLong(2, surveyId);
				statement.executeUpdate();
				return statement.getResultSet();
			} catch (Exception e) {
				throw new SQLException();
			}
		};
		
		alter(query, stLambda);
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
	public ResultSet selectId(String email, long surveyId)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		
		String query = "SELECT id FROM recipents WHERE email = ? AND survey_id = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, email);
				statement.setLong(2, surveyId);
				return statement.executeQuery();
			} catch (Exception e) {
				throw new SQLException();
			}
		};
		
		return select(query, stLambda);
	}

}
