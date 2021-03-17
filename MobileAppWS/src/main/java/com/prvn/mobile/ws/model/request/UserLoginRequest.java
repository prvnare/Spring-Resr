package com.prvn.mobile.ws.model.request;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String emailId;
  private String password;
}
