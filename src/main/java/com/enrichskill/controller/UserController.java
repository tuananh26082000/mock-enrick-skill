package com.enrichskill.controller;

import com.enrichskill.entity.User;
import com.enrichskill.mapper.UserMapper;
import com.enrichskill.repository.UserRepository;
import com.enrichskill.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
    public UserResponse getInfo(Authentication authentication) {
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        return userMapper.to(user.orElseThrow());
    }
}
