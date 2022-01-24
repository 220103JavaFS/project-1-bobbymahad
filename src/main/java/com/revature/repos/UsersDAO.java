package com.revature.repos;

import com.revature.models.users.User;

public interface UsersDAO {

    //GET METHODS
    public User getUser(String username); //gets all the basic information in the database about the user with the passed username
    public boolean availableUsernameEmail(String username, String email); //used in new employee creation, makes sure that the given username and emails don't already exist in the database

    //POST METHODS
    public boolean hireEmployee(User newEmployee);

    //DELETE METHODS
    public boolean fireEmployee(User exEmployee);
}
