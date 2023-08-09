package com.enrickskill.request.user;

import com.enrickskill.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}