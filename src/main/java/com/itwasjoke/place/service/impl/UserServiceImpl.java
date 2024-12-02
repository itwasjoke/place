package com.itwasjoke.place.service.impl;

import com.itwasjoke.place.DTO.UserRequestDTO;
import com.itwasjoke.place.DTO.CoupleResponseDTO;
import com.itwasjoke.place.DTO.mapper.UserMapper;
import com.itwasjoke.place.customEnum.Role;
import com.itwasjoke.place.entity.User;
import com.itwasjoke.place.exception.user.UserAlreadyExistsException;
import com.itwasjoke.place.exception.user.UserNotFoundException;
import com.itwasjoke.place.repository.UserRepository;
import com.itwasjoke.place.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserRequestDTO userRequestDTO, String password) {
        if (userRepository.existsByLogin(userRequestDTO.login())){
            throw new UserAlreadyExistsException("With this login user already exists in createUser() func");
        } else {
            User user = userMapper.userRequestToUser(userRequestDTO);
            user.setPassword(password);
            user.setRole(Role.USER);
            return userRepository.save(user);
        }
    }

    @Override
    public User getUser(String login) {
        Optional<User> userOptional = userRepository.findUserByLogin(login);
        if (userOptional.isPresent()){
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User not found in getUser() func");
        }
    }


    @Override
    public UserDetailsService userDetailsService() {
        return this::getUser;
    }
}
