package com.hello.world.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import javax.sql.DataSource;

public abstract class DAOFactory {
	
	public static String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	public static String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
    
	public static String MYSQL_DRIVER="com.mysql.jdbc.Driver";
	public static String  MYSQL_URL ="jdbc:mysql://"+host+":"+port+"/hwregistration";
	public static String MYSQL_USER ="adminP5xLIN7";
	public static String MYSQL_password="XTH5Pb8Wxylv";
	public static DAOFactory getInstance() {

		DAOFactory instance;

		try {
			Class.forName(MYSQL_DRIVER);

			instance = new DriverManagerDAOFactory(MYSQL_URL, MYSQL_USER, MYSQL_password);

		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * throw new DAOConfigurationException( "Driver class '" + dbInfo[0]
			 * + "' is missing in classpath.", e);
			 */
			return null;
		}

		try {

			// instance = new DriverManagerDAOFactory(url, userName, password);
			Connection con = instance.getConnection();
			if (instance.getConnection() == null) {
				return null;
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * throw new DAOConfigurationException( "Driver class '" + dbInfo[0]
			 * + "' is missing in classpath.", e);
			 */
			return null;
		}
		return instance;
	}



	abstract Connection getConnection() throws SQLException;

	public UserDAO getUserDAO() {
        return new DAOUserImp(this);
    }

}

// Default DAOFactory implementations
// -------------------------------------------------------------

/**
 * The DriverManager based DAOFactory.
 */
class DriverManagerDAOFactory extends DAOFactory {
	private String url;
	private String username;
	private String password;

	DriverManagerDAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}

/**
 * The DataSource based DAOFactory.
 */
class DataSourceDAOFactory extends DAOFactory {
	private DataSource dataSource;

	DataSourceDAOFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

/**
 * The DataSource-with-Login based DAOFactory.
 */
class DataSourceWithLoginDAOFactory extends DAOFactory {
	private DataSource dataSource;
	private String username;
	private String password;

	DataSourceWithLoginDAOFactory(DataSource dataSource, String username, String password) {
		this.dataSource = dataSource;
		this.username = username;
		this.password = password;
	}

	@Override
	Connection getConnection() throws SQLException {
		return dataSource.getConnection(username, password);
	}
}