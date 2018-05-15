package com.devglan.userportal.service;

import com.devglan.userportal.entity.User;

import java.util.List;

/**
 * This interface models a service layer that will be between REST API (RESTController)
 * and the RESTFUL CLIENT (RestTemplate) as a good practice to invoke the data access
 * API`s from our REST.
 */

public interface UserService {
    User createUser(User user);
    User deleteUser(int id);
    User updateUser(User user);
    List<User> findAll();
    User findUser(int userId);
    User findUserByEMail(String eMail);
    User findUserByUsername(String username);
}
