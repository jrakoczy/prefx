package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

import com.jrakoczy.prefx.credentials.DBCredentials;

/**
 * Class enabling an access to a database. Uses JDBC to establish a connection
 * and execute a query.
 * 
 * @author kuba
 */
class DatabaseAccess {

	private static final String unameKey = "username";
	private static final String passwordKey = "password";
	private static final String credentialsPath = "/classified/dbcredentials.xml";

	private static final String driverName = "org.postgresql.Driver";
	private static final String urlPrefix = "jdbc:postgresql://";
	private static final String hostname = "localhost";
	private static final String port = "8080";
	private static final String dbname = "prefxdb";

	private ServletContext context;

	/**
	 * 
	 * @param context
	 */
	public DatabaseAccess(ServletContext context) {
		this.context = context;
	}

	
	/**
	 * 
	 * @param sqlQuery
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 */
	public void update(String sqlQuery) throws ClassNotFoundException,
			IOException, InvalidPreferencesFormatException, SQLException {

		Statement statement = prepareStatement(sqlQuery);
		statement.executeUpdate(sqlQuery);
	}

	/**
	 * 
	 * @param sqlQuery
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 */
	public void query(String sqlQuery) throws ClassNotFoundException,
			IOException, InvalidPreferencesFormatException, SQLException {

		Statement statement = prepareStatement(sqlQuery);
		statement.executeQuery(sqlQuery);
	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 */
	private Connection connect() throws ClassNotFoundException, IOException,
			InvalidPreferencesFormatException, SQLException {

		// Retrieve credentials
		InputStream inStream = context.getResourceAsStream(credentialsPath);
		DBCredentials dbCredentials = new DBCredentials(inStream);
		String username = dbCredentials.get(unameKey);
		String password = dbCredentials.get(username);

		// Compose url
		String url = composeDBUrl();

		// Establish connection
		Class.forName(driverName);
		Connection connection = DriverManager.getConnection(url, username,
				password);
		return connection;
	}

	/**
	 * 
	 * @param sqlQuery
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 */
	private Statement prepareStatement(String sqlQuery)
			throws ClassNotFoundException, IOException,
			InvalidPreferencesFormatException, SQLException {
		Connection connection = connect();
		Statement statement = connection.prepareStatement(sqlQuery);
		return statement;

	}

	/**
	 * 
	 * @return
	 */
	private String composeDBUrl() {
		return urlPrefix + hostname + ":" + port + "/" + dbname;
	}
}
