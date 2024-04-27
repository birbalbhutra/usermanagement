package com.genesys.usermanangement.service;

import com.genesys.usermanangement.dto.LoginDto;
import com.genesys.usermanangement.dto.UserDto;
import com.genesys.usermanangement.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public void createUser(@Valid UserDto userDto);

    public void updateUser(@Valid UserDto userDto);

    public void deleteUser(Long userId);

    public List<User> getAllUsers();

    public void userLogin(LoginDto loginDto);
}
