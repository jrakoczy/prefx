package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

/**
 * A class used to perform operations on account data stored in a database.
 * 
 * @author kuba
 */
public class UserManager {

	ServletContext context;

	/**
	 * Creates a new {@code AccountManager} instance using a given
	 * {@code ServletContext}.
	 * 
	 * @param context
	 */
	public UserManager(ServletContext context) {
		this.context = context;
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
	public void addRecord(String email, String passwordHash)
			throws ClassNotFoundException, IOException,
			InvalidPreferencesFormatException, SQLException {

		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String insert = "INSERT INTO users (id, email, password_hash) VALUES (DEFAULT, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);

			statement.setString(1, email);
			statement.setString(2, passwordHash);
			statement.executeUpdate();
		}
	}

	/**
	 * 
	 * @param email
	 * @throws InvalidPreferencesFormatException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet findPasswordHash(String email)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {

		ResultSet results = null;
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			String select = "SELECT password_hash FROM users WHERE email = ?;";
			PreparedStatement statement = connection.prepareStatement(select);

			statement.setString(1, email);
			results = statement.executeQuery();
		}

		return results;
	}
}
