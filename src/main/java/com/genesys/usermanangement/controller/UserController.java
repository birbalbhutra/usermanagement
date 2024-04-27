package com.genesys.usermanangement.controller;

import com.genesys.usermanangement.constants.AppConstants;
import com.genesys.usermanangement.dto.LoginDto;
import com.genesys.usermanangement.dto.UserDto;
import com.genesys.usermanangement.entity.User;
import com.genesys.usermanangement.exception.ApiResponse;
import com.genesys.usermanangement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * /login endpoint verifies the credentials given by the user
 *
 * /signup is not secured and used to create new users
 *
 * /users/update/{userId} only put method has been created for updation, separate PATCH requests are not implemented
 *
 * /users/deleted/{userId} once logged in, user can delete their profile
 *
 * /users list all users, only ADMIN will be able to access this endpoint, manual entry needed for ADMIN account in DB
 */
@RestController
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(@Valid @RequestBody LoginDto loginDto) {
        log.info("Login User");
        userService.userLogin(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(), AppConstants.USER_SIGN_IN_SUCCESS, LocalDateTime.now()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Create User");
        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(), AppConstants.USER_CREATE_SUCCESS, LocalDateTime.now()));
    }

    @PutMapping("/users/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDto userDto) {
        log.info("Update User");
        userDto.setId(userId);
        userService.updateUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(), AppConstants.USER_UPDATE_SUCCESS, LocalDateTime.now()));
    }

    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {
        log.info("Delete User");
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(), AppConstants.USER_DELETE_SUCCESS, LocalDateTime.now()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Get List of All Users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
