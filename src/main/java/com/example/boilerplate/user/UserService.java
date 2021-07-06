package com.example.boilerplate.user;

import com.example.boilerplate.entity.User;
import com.example.boilerplate.exception.ResourceNotFoundException;
import com.example.boilerplate.user.dto.UpdateUserNameRequest;
import com.example.boilerplate.user.dto.UserInfoResponse;
import com.example.boilerplate.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserInfoResponse updateUserName(UUID userId, UpdateUserNameRequest updateUserNameRequest) {
        User user = getUserById(userId);
        user.setName(updateUserNameRequest.getName());
        userRepository.save(user);

        return userMapper.toUserInfoResponse(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    public List<UserInfoResponse> getAllUsers(){
        return userRepository.findAll().stream().map(user -> userMapper.toUserInfoResponse(user)).collect(Collectors.toList());
    }
}
