package com.prvn.mobile.ws.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;


@Entity(name = "USERS")
@Data
public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LAST_NAME" ,nullable = false, length = 50)
    private String lastName;

    @Column(name = "EMAIL_ID", nullable = false,length = 130,unique = true)
    private String emailId;

    @Column(name = "USER_ID", nullable = false,unique = true)
    private UUID userId;

    @Column(name = "PASSWORD",nullable = false)
    private String encryptedPassword;

    @Column(name = "EMAIL_VERIFICATION_TOKEN")
    private String emailVerificationToken;

    @Column(name = "EMAIL_VERIFICATION_STATUS", nullable = false)
    private Boolean emailVerificationStatus = false;


}
