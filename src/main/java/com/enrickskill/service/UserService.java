package com.enrickskill.service;

import com.enrickskill.request.CreateUserRequest;
import com.enrickskill.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse save(CreateUserRequest request);

    UserResponse findById(Integer id);

    void delete(Integer id);

    Page<UserResponse> findALL(Pageable pageable);

}
