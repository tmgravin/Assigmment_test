package com.msp.assignment.dto;

import com.msp.assignment.enumerated.UserType;
import lombok.Data;

@Data
public class UsersDto {
    private UserType userType;
    public Long id;
    public String oldPassword;
    public String newPassword;
}
