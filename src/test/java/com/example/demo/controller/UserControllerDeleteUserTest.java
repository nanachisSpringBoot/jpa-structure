package com.example.demo.controller;

import com.example.demo.controller.v1.UserController;
import com.example.demo.payload.ApiResponse;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerDeleteUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testDeleteUserSuccess() throws Exception {
        // Mock service response for successful deletion
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User deleted successfully",
                null
        );

        when(userService.deleteUser(eq(1L))).thenReturn(apiResponse);

        // Perform request and verify response
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User deleted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        // Mock service response for user not found
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "User not found",
                null
        );

        when(userService.deleteUser(eq(999L))).thenReturn(apiResponse);

        // Perform request and verify response
        mockMvc.perform(delete("/api/v1/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void testDeleteUserInternalServerError() throws Exception {
        // Mock service response for internal server error
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error deleting user: Database connection failed",
                null
        );

        when(userService.deleteUser(eq(2L))).thenReturn(apiResponse);

        // Perform request and verify response
        mockMvc.perform(delete("/api/v1/users/2"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Error deleting user: Database connection failed"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}