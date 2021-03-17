package com.prvn.mobile.ws.service;

import com.prvn.mobile.ws.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
}
