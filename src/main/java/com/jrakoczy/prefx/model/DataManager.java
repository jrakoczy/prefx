package com.jrakoczy.prefx.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.InvalidPreferencesFormatException;

import javax.servlet.ServletContext;

abstract class DataManager {

	protected ServletContext context;

	/**
	 * 
	 * @author kuba
	 *
	 */
	public interface StatementLambda {
		public ResultSet query(PreparedStatement statement) throws SQLException;
	}

	public DataManager(ServletContext context) {
		this.context = context;
	}

	public ResultSet select(String query, StatementLambda stLambda)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		return executeStatement(query, stLambda);
	}

	public void alter(String query, StatementLambda stLambda)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		executeStatement(query, stLambda);
	}

	/**
	 * 
	 * @param query
	 * @param stLambda
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	private ResultSet executeStatement(String query, StatementLambda stLambda)
			throws ClassNotFoundException, SQLException, IOException,
			InvalidPreferencesFormatException {
		ResultSet results = null;
		DatabaseAccess dbAccess = new DatabaseAccess(context);

		try (Connection connection = dbAccess.connect();) {
			PreparedStatement statement = connection.prepareStatement(query);
			return stLambda.query(statement);
		}
	}

}
