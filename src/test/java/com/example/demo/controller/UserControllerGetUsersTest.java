package com.example.demo.controller;

import com.example.demo.controller.v1.UserController;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.Pagination;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerGetUsersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUsers() throws Exception {
        // Prepare mock response
        List<UserResponse> userResponses = new ArrayList<>();
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Users retrieved successfully"
        );
        apiResponse.setData(userResponses);

        // Create pagination object
        Pagination pagination = new Pagination(0, 10, 0, 0, true);
        apiResponse.setPagination(pagination);

        // Keep extra map for backward compatibility
        Map<String, Object> extra = new HashMap<>();
        apiResponse.setExtra(extra);

        // Mock service method
        when(userService.getAllUsers(anyInt(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(apiResponse);

        // Perform request and verify response
        mockMvc.perform(get("/api/v1/users")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.pagination").exists())
                .andExpect(jsonPath("$.pagination.pageNumber").value(0))
                .andExpect(jsonPath("$.pagination.pageSize").value(10))
                .andExpect(jsonPath("$.pagination.totalElements").value(0))
                .andExpect(jsonPath("$.pagination.totalPages").value(0))
                .andExpect(jsonPath("$.pagination.last").value(true));
    }

    @Test
    public void testGetUsersWithFullNameFilter() throws Exception {
        // Prepare mock response
        List<UserResponse> userResponses = new ArrayList<>();
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Users retrieved successfully"
        );
        apiResponse.setData(userResponses);

        // Create pagination object
        Pagination pagination = new Pagination(0, 10, 0, 0, true);
        apiResponse.setPagination(pagination);

        // Keep extra map for backward compatibility
        Map<String, Object> extra = new HashMap<>();
        apiResponse.setExtra(extra);

        // Mock service method
        when(userService.getAllUsers(anyInt(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(apiResponse);

        // Perform request with fullName filter and verify response
        mockMvc.perform(get("/api/v1/users")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "asc")
                .param("fullName", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.pagination").exists())
                .andExpect(jsonPath("$.pagination.pageNumber").value(0))
                .andExpect(jsonPath("$.pagination.pageSize").value(10))
                .andExpect(jsonPath("$.pagination.totalElements").value(0))
                .andExpect(jsonPath("$.pagination.totalPages").value(0))
                .andExpect(jsonPath("$.pagination.last").value(true));
    }
}
