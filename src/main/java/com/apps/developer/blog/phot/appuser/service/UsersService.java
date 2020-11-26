package com.apps.developer.blog.phot.appuser.service;

import com.apps.developer.blog.phot.appuser.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
}
