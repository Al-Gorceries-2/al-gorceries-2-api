package com.algorceries.api.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.algorceries.api.security.JwtUserDetails;
import com.algorceries.api.service.IUserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserService userService;

    public JwtUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findById(username)
            .map(user -> new JwtUserDetails(username, user.getHousehold() != null ? user.getHousehold().getId() : null))
            .getOrElseThrow(() -> new UsernameNotFoundException("User not found with id: " + username));
    }
}
