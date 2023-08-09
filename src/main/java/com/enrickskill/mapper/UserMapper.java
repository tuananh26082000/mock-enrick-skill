package com.enrickskill.mapper;

import com.enrickskill.entity.User;
import com.enrickskill.request.user.CreateUserRequest;
import com.enrickskill.request.user.UpdateUserRequest;
import com.enrickskill.response.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User> {

    public User to(CreateUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        return user;
    }

    public User to(UpdateUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        return user;
    }

    public UserResponse to(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

}
