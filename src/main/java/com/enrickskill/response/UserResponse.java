package com.enrickskill.response;

import com.enrickskill.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    // TODO need refactor response
    private Integer id;
    private String email;

    private String firstname;

    private String lastname;
    private Role role;
}
