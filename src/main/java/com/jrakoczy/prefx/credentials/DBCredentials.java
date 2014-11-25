package com.jrakoczy.prefx.credentials;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

/**
 * Class enabling to access database credentials stored in an external file.
 * 
 * @author kuba
 */
public final class DBCredentials {

	private static final String unameKey = "username";
	private static final String passwordKey = "password";

	private Preferences credentials;

	/**
	 * Creates a {@code DBCredentials} for a given external file.
	 * 
	 * @param name
	 *            absolute path to a credentials file
	 * @throws IOException
	 * @throws InvalidPreferencesFormatException
	 */
	public DBCredentials(InputStream inStream) throws IOException,
			InvalidPreferencesFormatException {


		Preferences.importPreferences(inStream);
		credentials = Preferences.userNodeForPackage(DBCredentials.class);
	}

	/**
	 * 
	 * @return a database user name
	 */
	public String getUname() {
		return credentials.get(unameKey, null);
	}

	/**
	 * 
	 * @return a database password
	 */
	public String getPassword() {
		return credentials.get(passwordKey, null);
	}

}
