package com.techelevator.pokertracker.dao;

import com.techelevator.pokertracker.model.RegisterUserDto;
import com.techelevator.pokertracker.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);
}
