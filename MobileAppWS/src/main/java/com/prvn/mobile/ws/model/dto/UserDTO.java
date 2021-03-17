package com.prvn.mobile.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    public static final long serialVersionUID = 1L;
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String userId;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false ;

}
