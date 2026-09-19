package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.UserProfileDto;
import com.ecommerce.app.dto.UserRegistrationDto;
import com.ecommerce.app.entity.Role;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.exception.UserAlreadyExistsException;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("An account with email " + dto.getEmail() + " already exists.");
        }

        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .role(Role.ROLE_CUSTOMER)
                .enabled(true)
                .build();

        log.info("Registering new customer: {}", dto.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public UserProfileDto getUserProfile(String email) {
        User user = findByEmail(email);
        return UserProfileDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

    @Override
    @Transactional
    public User updateProfile(String email, UserProfileDto dto) {
        User user = findByEmail(email);
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        log.info("Updated profile for user: {}", email);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllCustomers() {
        return userRepository.findByRole(Role.ROLE_CUSTOMER);
    }

    @Override
    @Transactional
    public User toggleUserStatus(Long userId) {
        User user = findById(userId);
        user.setEnabled(!user.getEnabled());
        log.info("Toggled status for user ID {}: enabled={}", userId, user.getEnabled());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = findByEmail(email);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password does not match.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Changed password for user: {}", email);
    }
}
