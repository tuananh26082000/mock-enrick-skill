package com.enrichskill.response;

import com.enrichskill.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private Role role;
}
