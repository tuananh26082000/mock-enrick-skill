package com.enrichskill.service.user;

import com.enrichskill.entity.User;
import com.enrichskill.base.BusinessCode;
import com.enrichskill.base.BusinessException;
import com.enrichskill.mapper.UserMapper;
import com.enrichskill.repository.UserRepository;
import com.enrichskill.request.user.UpdateUserRequest;
import com.enrichskill.response.UserResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }
    @Override
    public UserResponse findById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new BusinessException(BusinessCode.NOT_FOUND_USER)
        );
        return userMapper.to(user);
    }

    @Override
    public UserResponse update(UpdateUserRequest request) {
        User user = userMapper.to(request);
        return userMapper.to(userRepo.saveAndFlush(user));
    }

    @Override
    public void delete(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public Page<UserResponse> findALL(Pageable pageable) {
        Page<User> users = userRepo.findAll(pageable);
        return users.map(userMapper::to);
    }
}
