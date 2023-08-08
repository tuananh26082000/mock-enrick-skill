package com.enrickskill.response;

import com.enrickskill.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String email;

    private String firstname;

    private String lastname;
    private Role role;
}
