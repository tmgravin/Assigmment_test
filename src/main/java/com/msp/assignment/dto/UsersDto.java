package com.msp.assignment.dto;

import com.msp.assignment.model.UsersContact;
import lombok.Data;

import java.util.List;

@Data
public class UsersDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String userRole;
    private String loginType;
    private List<UsersContact> usersContacts;
}
