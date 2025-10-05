package com.dailyhaul.order.dto;

import com.dailyhaul.order.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserRole role = UserRole.CUSTOMER;

    private AddressDTO address;

    public UserResponse(String id, String firstName, String lastName, String email, String phone, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
