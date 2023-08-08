package com.enrickskill.service;

import com.enrickskill.entity.User;
import com.enrickskill.base.BusinessCode;
import com.enrickskill.base.BusinessException;
import com.enrickskill.mapper.UserMapper;
import com.enrickskill.request.CreateUserRequest;
import com.enrickskill.repository.UserRepository;
import com.enrickskill.response.UserResponse;
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
    public UserResponse save(CreateUserRequest request) {
        User user = userMapper.to(request);
        return userMapper.to(userRepo.saveAndFlush(user));
    }

    @Override
    public UserResponse findById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new BusinessException(BusinessCode.NOT_FOUND_USER)
        );
        return userMapper.to(user);
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
