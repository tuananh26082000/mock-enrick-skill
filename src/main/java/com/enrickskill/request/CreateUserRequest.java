package com.enrickskill.request;

import com.enrickskill.entity.Role;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
