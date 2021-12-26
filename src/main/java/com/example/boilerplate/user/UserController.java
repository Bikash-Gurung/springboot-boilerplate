package com.example.boilerplate.user;

import com.example.boilerplate.security.UserPrincipal;
import com.example.boilerplate.annotation.CurrentUser;
import com.example.boilerplate.annotation.VerifiedEmail;
import com.example.boilerplate.user.dto.UserInfoResponse;
import com.example.boilerplate.user.dto.UpdateUserNameRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping
    @PreAuthorize("hasRole('USER')")
    @VerifiedEmail
    public UserInfoResponse updateUserName(@CurrentUser UserPrincipal userPrincipal,
                                           @Valid @RequestBody UpdateUserNameRequest updateUserNameRequest) {
        return userService.updateUserName(userPrincipal.getId(), updateUserNameRequest);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserInfoResponse> listAllUsers() {
        return userService.getAllUsers();
    }

}
