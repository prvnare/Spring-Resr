package com.prvn.mobile.ws.model.request;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
