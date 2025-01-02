package com.myl.electronicsignatureservice.auth.service;

import com.myl.electronicsignatureservice.auth.dao.UserDao;
import com.myl.electronicsignatureservice.auth.entity.User;
import com.myl.electronicsignatureservice.auth.model.SecureUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class SecureUserService implements UserDetailsService {
    private final UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(SecureUserService.class);

    /**
     * Loads a user by the given username.
     *
     * @param username The username to load the user by.
     * @return The loaded UserDetails object.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
            return new SecureUser(user);
        }catch (Exception e){
            logger.error("Error loading user by username: {}", username, e);
            throw new UsernameNotFoundException(username);
        }
    }
}
