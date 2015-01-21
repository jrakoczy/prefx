package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

import com.jrakoczy.prefx.model.dto.SurveyDTO;

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
	public void insertRecord(SurveyDTO survey)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {

		String query = "INSERT INTO surveys (id, title, user_id) VALUES (DEFAULT, ?, ?);";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, survey.getTitle());
				statement.setLong(2, survey.getUid());
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
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws InvalidPreferencesFormatException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ResultSet selectUserSurveys(long userId) throws SQLException,
			ClassNotFoundException, IOException,
			InvalidPreferencesFormatException {

		String query = "SELECT id FROM surveys WHERE user_id = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setLong(1, userId);
				return statement.executeQuery();
			} catch (Exception e) {
				throw new SQLException();
			}
		};

		return select(query, stLambda);
	}

}
