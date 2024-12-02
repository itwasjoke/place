package com.itwasjoke.place.service;

import com.itwasjoke.place.DTO.UserRequestDTO;
import com.itwasjoke.place.DTO.CoupleResponseDTO;
import com.itwasjoke.place.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User createUser(UserRequestDTO userRequestDTO, String password);
    User getUser(String login);
    UserDetailsService userDetailsService();
}
