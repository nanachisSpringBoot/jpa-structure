package com.example.demo.controller.v1;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.payload.ApiResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "User management APIs")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest userRequest) {
        ApiResponse<UserResponse> response = userService.createUser(userRequest);
        return ResponseEntity.status(response.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Field to sort by", example = "id")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Sort direction (asc or desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,

            @Parameter(description = "Filter users by full name (optional)")
            @RequestParam(required = false) String fullName) {

        ApiResponse<List<UserResponse>> response = userService.getAllUsers(page, size, sortBy, direction, fullName);

        return ResponseEntity.status(response.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Parameter(description = "ID of the user to update", required = true, example = "1")
            @PathVariable Long userId,
            @Valid @RequestBody UserRequest userRequest) {

        ApiResponse<UserResponse> response = userService.updateUser(userId, userRequest);

        return ResponseEntity.status(response.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{userId}")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Delete a user",
        description = "Deletes a user by their ID. Returns a success message if deletion is successful.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", 
                description = "User successfully deleted"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404", 
                description = "User not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500", 
                description = "Internal server error"
            )
        }
    )
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true, example = "1")
            @PathVariable Long userId) {

        ApiResponse<Void> response = userService.deleteUser(userId);

        return ResponseEntity.status(response.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
