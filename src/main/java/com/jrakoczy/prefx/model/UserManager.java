package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

import com.jrakoczy.prefx.model.dto.UserDTO;

/**
 * A class used to perform operations on user data stored in a database.
 * 
 * @author kuba
 */
public class UserManager extends DataManager {

	/**
	 * Creates a new {@code AccountManager} instance using a given
	 * {@code ServletContext}.
	 * 
	 * @param context
	 */
	public UserManager(ServletContext context) {
		super(context);
	}

	/**
	 * 
	 * @param email
	 * @param passwordHash
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 */
	public void insertRecord(UserDTO user)
			throws ClassNotFoundException, IOException,
			InvalidPreferencesFormatException, SQLException {

		String query = "INSERT INTO users (id, email, password_hash) VALUES (DEFAULT, ?, ?);";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, user.getEmail());
				statement.setString(2, user.getPasswordHash());
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
	 * @throws InvalidPreferencesFormatException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet selectPasswordHash(String email)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {

		String query = "SELECT password_hash FROM users WHERE email = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, email);
				return statement.executeQuery();
			} catch (Exception e) {
				throw new SQLException();
			}
		};

		return select(query, stLambda);
	}

	/**
	 * 
	 * @param email
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public ResultSet selectId(String email) throws ClassNotFoundException,
			SQLException, IOException, InvalidPreferencesFormatException {

		String query = "SELECT id FROM users WHERE email = ?;";
		StatementLambda stLambda = (statement) -> {
			try {
				statement.setString(1, email);
				return statement.executeQuery();
			} catch (Exception e) {
				throw new SQLException();
			}
		};

		return select(query, stLambda);
	}
}
