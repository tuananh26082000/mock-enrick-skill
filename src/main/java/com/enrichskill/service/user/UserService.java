package com.enrichskill.service.user;

import com.enrichskill.request.user.UpdateUserRequest;
import com.enrichskill.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse findById(Integer id);

    UserResponse update(UpdateUserRequest request);

    void delete(Integer id);

    Page<UserResponse> findALL(Pageable pageable);

}
