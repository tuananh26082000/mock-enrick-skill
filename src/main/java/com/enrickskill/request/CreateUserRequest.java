package com.enrickskill.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;

    private String password;
}
