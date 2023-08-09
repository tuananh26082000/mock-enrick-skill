package com.enrickskill.controller;

import com.enrickskill.entity.User;
import com.enrickskill.mapper.UserMapper;
import com.enrickskill.repository.UserRepository;
import com.enrickskill.request.user.UpdateUserRequest;
import com.enrickskill.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping(value = "/info")
    @PreAuthorize("hasAnyAuthority('admin:read','user:read')")
    public UserResponse getInfo(Authentication authentication, Principal principal) {
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        return userMapper.to(user.orElseThrow());
    }
}
