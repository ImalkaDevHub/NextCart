package com.ecommerce.app.service;

import com.ecommerce.app.dto.UserProfileDto;
import com.ecommerce.app.dto.UserRegistrationDto;
import com.ecommerce.app.entity.User;
import java.util.List;

public interface UserService {
    User registerUser(UserRegistrationDto registrationDto);
    User findByEmail(String email);
    User findById(Long id);
    UserProfileDto getUserProfile(String email);
    User updateProfile(String email, UserProfileDto profileDto);
    List<User> getAllCustomers();
    User toggleUserStatus(Long userId);
    void changePassword(String email, String currentPassword, String newPassword);
}
