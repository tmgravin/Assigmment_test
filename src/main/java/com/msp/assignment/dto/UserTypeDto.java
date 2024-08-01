package com.msp.assignment.dto;

import com.msp.assignment.enumerated.UserType;
import lombok.Data;

@Data
public class UserTypeDto {
    private UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
