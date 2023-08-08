package com.enrickskill.controller;

import com.enrickskill.base.BaseResponse;
import com.enrickskill.entity.User;
import com.enrickskill.request.RegisterRequest;
import com.enrickskill.response.AuthenticationResponse;
import com.enrickskill.response.UserResponse;
import com.enrickskill.service.AuthenticationService;
import com.enrickskill.service.TokenServiceImpl;
import com.enrickskill.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    private final AuthenticationService service;

    private final TokenServiceImpl tokenService;

    public AdminController(UserService userService, AuthenticationService service, TokenServiceImpl tokenService) {
        this.userService = userService;
        this.service = service;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/info-user/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public BaseResponse<UserResponse> findById(@PathVariable Integer id) {
        return BaseResponse.ofSuccess(userService.findById(id));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        try {
            tokenService.delete(id);
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "15"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string",
                    paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. Multiple sort criteria are supported.")})
    public BaseResponse<?> findAll(@ApiIgnore Pageable pageable) {
        return BaseResponse.ofSuccess(userService.findALL(pageable));
    }
}
