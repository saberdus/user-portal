package com.github.userportal.service;

import com.github.userportal.entity.User;
import com.github.userportal.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements the interface UserService
 */
@Service
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public User createUser(User user) {
        userRepository.save(user);

        LOGGER.info("Created user with email address: "+user.getEMail());
        return user;
    }

    @Override
    public User deleteUser(int userId) {
        User user = userRepository.findOne(userId);

        if(user != null) {
            userRepository.delete(user);
        }
        LOGGER.info("Deleted user with id: "+user.getUserId()+" and email address: "+user.getEMail());
        return user;
    }

    @Override
    public User updateUser(User user) {
        userRepository.save(user);
        LOGGER.info("Updated user with id: "+user.getUserId()+" and email address: "+user.getEMail());

        return user;
    }

    @Override
    public User findUser(int userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findUserByEMail(String eMail) {
        return userRepository.findByEMail(eMail);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
