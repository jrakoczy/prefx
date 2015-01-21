package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

import com.jrakoczy.prefx.model.dto.ResultDTO;

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
	public void insertRecord(ResultDTO result) throws ClassNotFoundException, SQLException,
			IOException, InvalidPreferencesFormatException {
		
		String query = "INSERT INTO surveys (product1_id, product2_id, recipent_id, value) VALUES (?, ?, ?);";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setLong(1, result.getFirstProductId());
				statement.setLong(2, result.getSecondProductId());
				statement.setLong(3, result.getRid());
				statement.setLong(3, result.getValue());
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
	 * @param firstProductId
	 * @param secondProductId
	 * @param surveyId
	 * @param value
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public void insertValue(long firstProductId, long secondProductId,
			long surveyId, int value) throws ClassNotFoundException,
			SQLException, IOException, InvalidPreferencesFormatException {
		
		String query = "UPDATE surveys SET value = ? WHERE product1_id = ? "
				+ "AND product2_id = ? AND survey_id = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setLong(1, value);
				statement.setLong(2, firstProductId);
				statement.setLong(3, secondProductId);
				statement.setLong(4, surveyId);
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
	 * @param firstProductId
	 * @param secondProductId
	 * @param surveyId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public ResultSet selectValue(long firstProductId, long secondProductId,
			long surveyId) throws ClassNotFoundException, SQLException,
			IOException, InvalidPreferencesFormatException {
		
		String query = "SELECT value FROM surveys WHERE product1_id = ? "
				+ "AND product2_id = ? AND survey_id = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setLong(1, firstProductId);
				statement.setLong(2, secondProductId);
				statement.setLong(3, surveyId);
				return statement.executeQuery();
			} catch (Exception e) {
				throw new SQLException();
			}
		};
		
		return select(query, stLambda);
	}

}
