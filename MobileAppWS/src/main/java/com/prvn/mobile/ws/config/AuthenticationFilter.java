package com.prvn.mobile.ws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prvn.mobile.ws.constants.SecurityConst;
import com.prvn.mobile.ws.model.request.UserLoginRequest;
import com.prvn.mobile.ws.service.UserService;
import com.prvn.mobile.ws.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UserLoginRequest user =
        new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            user.getEmailId(), user.getPassword(), new ArrayList<>()));
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication auth) {
    String username = ((User) auth.getPrincipal()).getUsername();

    String token =
        Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConst.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConst.SECRET_CODE)
            .compact();
    UserService userService =
        (UserService) SpringApplicationContextAware.getBean(UserServiceImpl.class);
    response.addHeader(SecurityConst.TOKEN_AUTH_HEADER, SecurityConst.TOKEN_BEARER + token);
    response.addHeader("UserId", userService.getUser(username).getUserId());
  }
}
