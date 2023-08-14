package com.enrickskill.service.user;

import com.enrickskill.request.user.UpdateUserRequest;
import com.enrickskill.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse findById(Integer id);

    UserResponse update(UpdateUserRequest request);

    void delete(Integer id);

    Page<UserResponse> findALL(Pageable pageable);

}
