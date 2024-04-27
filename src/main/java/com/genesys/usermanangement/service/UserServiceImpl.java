package com.genesys.usermanangement.service;

import com.genesys.usermanangement.constants.AppConstants;
import com.genesys.usermanangement.dto.LoginDto;
import com.genesys.usermanangement.dto.UserDto;
import com.genesys.usermanangement.entity.User;
import com.genesys.usermanangement.exception.UserAlreadyExistsException;
import com.genesys.usermanangement.exception.UserNotFoundException;
import com.genesys.usermanangement.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * All exceptions are handled in service class itself
 * Assumptions has been made regarding User Schema, additional column like role has been added for more role based control
 * Passwords are encrypted using BCryptPasswordEncoder before storing in DB
 * Except listing all users which is accessed only by ADMIN, all other APIs can be accessed by a normal user
 */
@Service
public class UserServiceImpl implements UserService{

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void userLogin(@Valid LoginDto loginDto) {
        log.info("Login User");

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(loginDto.getEmail()));

        if(existingUser.get() == null) {
            throw new UserAlreadyExistsException(AppConstants.EXCEPTION_USER_DONT_EXIST);
        }

        User user = existingUser.get();
        user.setLastLogin(LocalDate.now());
        userRepository.save(user);
    }

    @Override
    public void createUser(@Valid UserDto userDto) {
        log.info("Create User");

        User user = modelMapper.map(userDto, User.class);
        user.setLastLogin(LocalDate.now());
        user.setRole(AppConstants.ROLE_USER);

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        existingUser.ifPresent(u -> {
            throw new UserAlreadyExistsException(AppConstants.EXCEPTION_USER_EXIST);
        });
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(@Valid UserDto userDto) {
        log.info("User : " + userDto);

        if (!userRepository.existsById(userDto.getId())) {
            throw new UserNotFoundException(AppConstants.EXCEPTION_USER_DONT_EXIST);
        }

        User user = userRepository.findById(userDto.getId()).get();
        user.setLastLogin(LocalDate.now());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("UserId : " + userId);
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(AppConstants.EXCEPTION_USER_DONT_EXIST);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new UserNotFoundException(AppConstants.EXCEPTION_USER_DONT_EXIST);
        }
        return users;
    }
}
