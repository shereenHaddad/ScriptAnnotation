package com.hello.world.DAO;
import java.util.List;

import com.hello.world.beans.User;


public interface UserDAO {


    public List<User> list() throws DAOException;

    public boolean userExists(String firstName, String lastName  , String zipCode) throws DAOException;
    
    public void create(User user) throws IllegalArgumentException, DAOException;


    public void createTable() throws DAOException ;

	public void EmptyTable()throws DAOException ;
}