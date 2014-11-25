package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

	/**
	 * A key of user name stored in an external file.
	 */
	private static final String unameKey = "username";
	
	/**
	 * A key of password name stored in an external file.
	 */
	private static final String passwordKey = "password";
	
	/**
	 * A path to an external file containing database credentials. 
	 */
	private static final String credentialsPath = "/classified/dbcredentials.xml";

	private static final String driverName = "org.postgresql.Driver";
	private static final String urlPrefix = "jdbc:postgresql://";
	private static final String hostname = "localhost";
	private static final String port = "8080";
	private static final String dbname = "prefxdb";

	private ServletContext context;

	/**
	 * Creates a new {@code DatabaseAccess} instance using a given
	 * {@code ServletContext}.
	 * 
	 * @param context
	 *            a {@code SerlvetContext} of a servlet that invoked constructor
	 */
	public DatabaseAccess(ServletContext context) {
		this.context = context;
	}

	/**
	 * Performs an upadte on a database.
	 * 
	 * @param sqlQuery
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 * @see com.jrakoczy.prefx.credentials.DatabaseAccess#query() query()
	 */
	public void update(String sqlQuery) throws ClassNotFoundException,
			IOException, InvalidPreferencesFormatException, SQLException {

		Statement statement = prepareStatement(sqlQuery);
		statement.executeUpdate(sqlQuery);
	}

	/**
	 * Performs an SQL query on a database. Returns {@code ResultsSet} of found
	 * results.
	 * 
	 * @param sqlQuery
	 *            an SQL query
	 * @return a {@code ResultSet} containing results of a query
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 * @throws SQLException
	 * @see com.jrakoczy.prefx.credentials.DatabaseAccess#update() update()
	 */
	public ResultSet query(String sqlQuery) throws ClassNotFoundException,
			IOException, InvalidPreferencesFormatException, SQLException {

		Statement statement = prepareStatement(sqlQuery);
		return statement.executeQuery(sqlQuery);
	}

	/**
	 * Establishes a connection to a database. Retrieves credentials and
	 * composes a proper URL. Then creates a connection using prepared values.
	 * 
	 * 
	 * @return a new {@code Connection} to a database
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
	 * Returns a {@code PreparedStatement} for given {@code sqlQuery}.
	 * 
	 * @param sqlQuery
	 * @return a new {@code PreparedStatement}
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
	 * Composes JDBC URL using consts defined in the class.
	 * 
	 * @return a JDBC URL
	 */
	private String composeDBUrl() {
		return urlPrefix + hostname + ":" + port + "/" + dbname;
	}
}
