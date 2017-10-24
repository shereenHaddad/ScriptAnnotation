package com.hello.world.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static com.hello.world.DAO.DAOUtil.*;
import com.hello.world.beans.User;

public class DAOUserImp implements UserDAO {

	private static final String SQL_FIND_ALL = "SELECT  firstName, lastName, address1 , address2 , city , state , zipCode, country, creationDate FROM hwregistration.Users order by creationDate desc";

	private static final String SQL_FIND_If_EXIST = "SELECT  * FROM hwregistration.Users where firstName like ? and  lastName like ? and zipCode like ?";

	private static final String SQL_INSERT = "INSERT INTO hwregistration.Users (firstName, lastName, address1 , address2 , city , state , zipCode, country ) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
	
	
	private static final String SQL_create = "CREATE TABLE hwregistration.Users ( id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT, "

			+ " firstName VARCHAR(100) NOT NULL,   lastName VARCHAR(100) NOT NULL,    address1 VARCHAR(100) NOT NULL,    address2 VARCHAR(100) , "
			+ " city VARCHAR(100) NOT NULL,    state VARCHAR(100) NOT NULL,    country VARCHAR(100) NOT NULL,    zipCode VARCHAR(100) NOT NULL, "
			+ " creationDate  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   PRIMARY KEY (id))";

	public void createTable() throws DAOException{
		try {
			Connection connection = daoFactory.getConnection();
			Statement stmt = connection.createStatement();
		//	stmt.executeUpdate("DROP TABLE  hwregistration.Users");
		  stmt.execute(SQL_create);
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List list() throws DAOException {
		List users = new ArrayList();

		try {
			Connection connection = daoFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
			ResultSet resultSet = statement.executeQuery();
			{
				while (resultSet.next()) {
					users.add(map(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return users;
	}

	public void create(User user) throws IllegalArgumentException, DAOException {
		Object[] values = { user.getFirstName(), user.getLastName(), user.getAddress1(), user.getAddress2(),
				user.getCity(), user.getState(), user.getZipCode(), user.getCountry(),
				// user.getRegDate()
				 };

		try {
			Connection connection = daoFactory.getConnection();
			PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating user failed, no rows affected.");
			}

			ResultSet generatedKeys = statement.getGeneratedKeys();

			if (generatedKeys.next()) {
				user.setId(generatedKeys.getLong(1));
			} else {
				throw new DAOException("Creating user failed, no generated key obtained.");
			}

		} catch (SQLException e)

		{
			throw new DAOException(e);
		}

	}

	private DAOFactory daoFactory;

	DAOUserImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setFirstName(resultSet.getString("firstName"));
		user.setLastName(resultSet.getString("lastName"));
		user.setAddress1(resultSet.getString("address1"));
		user.setAddress2(resultSet.getString("address2"));
		user.setZipCode(resultSet.getString("zipCode"));
		user.setCity(resultSet.getString("city"));
		user.setState(resultSet.getString("state"));
		user.setCountry(resultSet.getString("country"));
		user.setRegDate(resultSet.getDate("creationDate"));
		return user;
	}

	public boolean userExists(String firstName, String lastName, String zipCode) throws DAOException {
		zipCode = zipCode.substring(0,5)+"%";
		int count = 0;
		try {
			Connection connection = daoFactory.getConnection();

			PreparedStatement statement = connection.prepareStatement(SQL_FIND_If_EXIST);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, zipCode);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count++;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		if (count == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void EmptyTable() throws DAOException {
		try {
			Connection connection = daoFactory.getConnection();
			Statement stmt = connection.createStatement();
		stmt.executeUpdate("Truncate table  hwregistration.Users");
		 
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
	}

}
