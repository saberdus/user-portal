package com.github.userportal.controller;

import com.github.userportal.entity.User;
import com.github.userportal.service.UserService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Rest controller allows cross origin requests from "http://localhost:4200"
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(UserController.BASE_URI)
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public static final String BASE_URI = "/user-portal";

    private final UserService userService;

    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public User createUser(@RequestBody User user) {
        LOGGER.info("Create user: "+user.getEMail());
        return userService.createUser(user);
    }

    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasAuthority('admin')")
    public User deleteUser(@PathVariable("userId") int userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping
//    @PreAuthorize("hasAuthority('admin')")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

//    @GetMapping("/{userId}")
//    public User findUser(@PathVariable("userId") int userId) {
//        return userService.findUser(userId);
//    }

    @GetMapping
//    @PreAuthorize("hasAuthority('admin')")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{eMail}")
//    @PreAuthorize("hasAuthority('admin')")
    public User findUser(@PathVariable("eMail") String eMail) {
        LOGGER.info("Value of user eMail: "+eMail);

        User user = userService.findUserByEMail(eMail);

//        LOGGER.info("ID of user: "+user.getUserId());
        return user;
    }

    @GetMapping("/{username}/{password}")
//    @ResponseBody
//    @PreAuthorize("hasAuthority('admin') or hasAuthority('customer')")
    public User findLoggedUser(@PathVariable("username") String username,
                               @PathVariable("password") String password) throws Exception {
        LOGGER.info("Received login credentials: "+username+" & "+password);

        if((username != null) && (password != null)) {
            User user = userService.findUserByUsername(username);

            if(user != null) {
                String pwd = user.getPassword();
                LOGGER.info("+++++++++++++ "+pwd);

                String hashPassword = signPasswordWithHash256(password);
                LOGGER.info("////////////////// "+hashPassword);

                if(pwd.equals(hashPassword)) {
                    LOGGER.info("User is logged with valid credentials: "+"username= "+username+" password= "+password);

                    LOGGER.info("STATUS CODE OF LOGGING: "+new ResponseEntity<>(HttpStatus.GONE).getStatusCode());
//                    return new ResponseEntity<>(HttpStatus.GONE).getStatusCode();
                    return user;
                } else {
                    LOGGER.info("Given password is wrong: "+password);

                    LOGGER.info("STATUS CODE OF LOGGING: "+new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode());
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode();
                    return null;
                }
            } else {
                LOGGER.info("Given username is wrong: "+username);

                LOGGER.info("STATUS CODE OF LOGGING: "+new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode());
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode();
                return null;
            }
        } else {
            LOGGER.info("STAUS CODE OF LOGGING: "+new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode();
            return null;
        }
    }

    private String signPasswordWithHash256(String pwd) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(pwd.getBytes());

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
