package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.Pagination;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public ApiResponse<UserResponse> createUser(UserRequest userRequest) {
        try {
            // Check if username or email already exists
            if (userRepository.existsByUsername(userRequest.getUsername())) {
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Username already exists", null);
            }

            if (userRepository.existsByEmail(userRequest.getEmail())) {
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email already exists", null);
            }

            // Create new user entity from request
            User user = User.builder()
                    .username(userRequest.getUsername())
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword()) // In a real app, you would hash the password
                    .fullName(userRequest.getFullName())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .gender(userRequest.getGender())
                    .dateOfBirth(userRequest.getDateOfBirth())
                    .profilePictureUrl(userRequest.getProfilePictureUrl())
                    .role("USER") // Default role
                    .isActive(true)
                    .isEmailVerified(false)
                    .build();
            // Save user to database
            User savedUser = userRepository.save(user);

            // Convert to response DTO using MapStruct
            UserResponse userResponse = userMapper.userToUserResponse(savedUser);

            return new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully", userResponse);
        } catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error creating user: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<UserResponse>> getAllUsers(int page, int size, String sortBy, String direction, String fullName) {
        try {
            // Create Pageable instance
            Sort sort = direction.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() :
                    Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);

            // Get page of users
            Page<User> userPage = userRepository.getUserList(fullName, pageable);

            // Convert to list of UserResponse using MapStruct
            List<UserResponse> userResponses = userPage.getContent().stream()
                    .map(userMapper::userToUserResponse)
                    .collect(Collectors.toList());

            // Create pagination info
            Pagination pagination = new Pagination(
                    userPage.getNumber(),
                    userPage.getSize(),
                    userPage.getTotalElements(),
                    userPage.getTotalPages(),
                    userPage.isLast()
            );

            // Create response with pagination info
            ApiResponse<List<UserResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Users retrieved successfully"
            );
            response.setData(userResponses);

            // Set pagination directly on the response
            response.setPagination(pagination);

            return response;
        } catch (Exception e) {
            return new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving users: " + e.getMessage()
            );
        }
    }

    @Transactional
    public ApiResponse<UserResponse> updateUser(Long userId, UserRequest userRequest) {
        try {
            // Find user by ID
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found", null);
            }

            User user = userOptional.get();

            // Check if username is being changed and if it already exists
            if (!user.getUsername().equals(userRequest.getUsername()) &&
                    userRepository.existsByUsername(userRequest.getUsername())) {
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Username already exists", null);
            }

            // Check if email is being changed and if it already exists
            if (!user.getEmail().equals(userRequest.getEmail()) &&
                    userRepository.existsByEmail(userRequest.getEmail())) {
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Email already exists", null);
            }

            // Update user fields
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(userRequest.getPassword()); // In a real app, you would hash the password
            }
            user.setFullName(userRequest.getFullName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setGender(userRequest.getGender());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user.setProfilePictureUrl(userRequest.getProfilePictureUrl());

            // Save updated user
            User updatedUser = userRepository.save(user);

            // Convert to response DTO using MapStruct
            UserResponse userResponse = userMapper.userToUserResponse(updatedUser);

            return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", userResponse);
        } catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error updating user: " + e.getMessage(), null);
        }
    }

    /**
     * Delete a user by ID
     *
     * @param userId the ID of the user to delete
     * @return ApiResponse containing the result of the operation
     */
    @Transactional
    public ApiResponse<Void> deleteUser(Long userId) {
        try {
            // Find user by ID
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found", null);
            }

            // Delete the user
            userRepository.deleteById(userId);

            return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error deleting user: " + e.getMessage(), null);
        }
    }
}
