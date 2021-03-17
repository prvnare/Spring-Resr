package com.prvn.mobile.ws.controller;

import com.prvn.mobile.ws.model.dto.UserDTO;
import com.prvn.mobile.ws.model.request.UserRequest;
import com.prvn.mobile.ws.model.response.UserResponse;
import com.prvn.mobile.ws.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<String> getUsers() {
    return ResponseEntity.ok()
        .headers(httpHeaders -> httpHeaders.add("content-type", MediaType.APPLICATION_JSON_VALUE))
        .body("User Details");
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
    System.out.println(userRequest);
    UserDTO userDTO = new UserDTO();
    UserResponse userResponse = new UserResponse();
    // copy
    BeanUtils.copyProperties(userRequest, userDTO);
    UserDTO user = userService.createUser(userDTO);
    // copy
    BeanUtils.copyProperties(user, userResponse);
    return ResponseEntity.ok().body(userResponse);
  }
}
