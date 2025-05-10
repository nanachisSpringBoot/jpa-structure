package com.example.demo.controller;

import com.example.demo.controller.v1.UserController;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.enu.Gender;
import com.example.demo.payload.ApiResponse;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerUpdateUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testUpdateUserSuccess() throws Exception {
        // Create a user request
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("updateduser");
        userRequest.setEmail("updated@example.com");
        userRequest.setPassword("Password123$");
        userRequest.setFullName("Updated User");
        userRequest.setPhoneNumber("+1234567890");
        userRequest.setGender(Gender.MALE);
        userRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userRequest.setProfilePictureUrl("https://example.com/profile.jpg");

        // Create a user response
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("updateduser");
        userResponse.setEmail("updated@example.com");
        userResponse.setFullName("Updated User");
        userResponse.setPhoneNumber("+1234567890");
        userResponse.setGender(Gender.MALE);
        userResponse.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userResponse.setProfilePictureUrl("https://example.com/profile.jpg");
        userResponse.setRole("USER");
        userResponse.setIsActive(true);
        userResponse.setIsEmailVerified(false);

        // Mock service response
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User updated successfully",
                userResponse
        );

        when(userService.updateUser(eq(1L), any(UserRequest.class))).thenReturn(apiResponse);

        // Perform request and verify response
        mockMvc.perform(patch("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("updateduser"))
                .andExpect(jsonPath("$.data.email").value("updated@example.com"))
                .andExpect(jsonPath("$.data.fullName").value("Updated User"));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        // Create a user request
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("updateduser");
        userRequest.setEmail("updated@example.com");
        userRequest.setPassword("Password123$");
        userRequest.setFullName("Updated User");

        // Mock service response for user not found
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "User not found",
                null
        );

        when(userService.updateUser(eq(999L), any(UserRequest.class))).thenReturn(apiResponse);

        // Perform request and verify response
        mockMvc.perform(patch("/api/v1/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void testUpdateUserWithInvalidData() throws Exception {
        // Create a user request with invalid data (missing required fields)
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(""); // Empty username
        userRequest.setEmail("invalid-email"); // Invalid email
        userRequest.setPassword("weak"); // Weak password
        userRequest.setFullName(""); // Empty full name

        // Perform request and verify response
        mockMvc.perform(patch("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }
}