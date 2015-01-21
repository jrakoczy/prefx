package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

import com.jrakoczy.prefx.model.dto.ProductDTO;

public class ProductManager extends DataManager {

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
	public void insertRecord(ProductDTO product) throws ClassNotFoundException,
			SQLException, IOException, InvalidPreferencesFormatException {

		String query = "INSERT INTO products (id, name, description, picture, survey_id) VALUES (DEFAULT, ?, ?, ?, ?);";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, product.getName());
				statement.setString(2, product.getDescription());
				statement.setBinaryStream(3, product.getPicutre());
				statement.setLong(4, product.getSid());
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
	 * @param surveyId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public ResultSet selectInfo(long surveyId) throws ClassNotFoundException,
			SQLException, IOException, InvalidPreferencesFormatException {
		
		String query = "SELECT name, description, picture FROM products WHERE survey_id = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setLong(1, surveyId);
				return statement.executeQuery();
			} catch (Exception e) {
				throw new SQLException();
			}
		};


		return select(query, stLambda);
	}
}
