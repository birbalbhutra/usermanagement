package com.genesys.usermanangement.config;

import com.genesys.usermanangement.constants.AppConstants;
import com.genesys.usermanangement.exception.UserNotFoundException;
import com.genesys.usermanangement.repository.UserRepository;
import com.genesys.usermanangement.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Fetching user details from the database
 * If user exists, the credentials are passed to security config to verify and authenticate
 */
@Configuration
public class DBUserDetails implements UserDetailsService {

    private static Logger log = LoggerFactory.getLogger(DBUserDetails.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside User Details");
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(username));

        if(!user.isPresent()) {
            throw new UserNotFoundException(AppConstants.EXCEPTION_USER_DONT_EXIST);
        }

        Set<SimpleGrantedAuthority> authority = new HashSet<>();
        authority.add(new SimpleGrantedAuthority(user.get().getRole()));

        log.info("ROLE : " + user.get().getRole());

        String email = user.get().getEmail();
        String password = user.get().getPassword();

        org.springframework.security.core.userdetails.User dbUser =
                new org.springframework.security.core.userdetails.User(email, password, authority);

        return dbUser;
    }
}
