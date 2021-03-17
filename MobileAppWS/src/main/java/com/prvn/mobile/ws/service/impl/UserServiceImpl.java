package com.prvn.mobile.ws.service.impl;

import com.prvn.mobile.ws.model.dto.UserDTO;
import com.prvn.mobile.ws.model.entity.UserEntity;
import com.prvn.mobile.ws.repository.UserRepository;
import com.prvn.mobile.ws.service.UserService;
import com.prvn.mobile.ws.utils.RandomStringGeneratorUtility;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RandomStringGeneratorUtility generatorUtility;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    UserRepository userRepository;

    public UserServiceImpl( UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        //Check if User is Existed with the same Email ID :
        UserEntity existingUserEntityByEmailId = userRepository.findByEmailId(userDTO.getEmailId());
        if(existingUserEntityByEmailId !=null){
           throw new RuntimeException("User is already existed with the same email Id");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);

        userEntity.setEncryptedPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setUserId(generatorUtility.generateUserId(30));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        BeanUtils.copyProperties(savedUserEntity,userDTO);
        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity byEmailId = userRepository.findByEmailId(email);
        if(Objects.isNull(byEmailId)){
            throw new UsernameNotFoundException(email);
        }
        return  new User(byEmailId.getEmailId(), byEmailId.getEncryptedPassword(), new ArrayList<>());
    }
}
