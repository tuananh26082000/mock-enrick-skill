package com.enrickskill.controller;

import com.enrickskill.base.BaseResponse;
import com.enrickskill.request.user.RegisterRequest;
import com.enrickskill.request.user.UpdateUserRequest;
import com.enrickskill.response.AuthenticationResponse;
import com.enrickskill.response.UserResponse;
import com.enrickskill.service.auth.AuthenticationService;
import com.enrickskill.service.user.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final AuthenticationService service;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public BaseResponse<UserResponse> findById(@PathVariable Integer id) {
        return BaseResponse.ofSuccess(userService.findById(id));
    }
    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id,
                                                   @RequestBody UpdateUserRequest request
    ) {
        Optional<UserResponse> user = Optional.ofNullable(userService.findById(id));
        return user.map(user1 -> {
            request.setId(user1.getId());
            return new ResponseEntity<>(userService.update(request), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        try {
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
