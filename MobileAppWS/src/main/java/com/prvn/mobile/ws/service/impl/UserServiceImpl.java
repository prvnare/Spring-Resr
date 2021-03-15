package com.prvn.mobile.ws.service.impl;

import com.prvn.mobile.ws.model.dto.UserDTO;
import com.prvn.mobile.ws.model.entity.User;
import com.prvn.mobile.ws.repository.UserRepository;
import com.prvn.mobile.ws.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl( UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        //Check if User is Existed with the same Email ID :
        User existingUserByEmailId = userRepository.findByEmailId(userDTO.getEmailId());
        if(existingUserByEmailId!=null){
           throw new RuntimeException("User is already existed with the same email Id");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);

        user.setEncryptedPassword("test");
        user.setUserId(UUID.randomUUID());
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser,userDTO);
        return userDTO;
    }
}
