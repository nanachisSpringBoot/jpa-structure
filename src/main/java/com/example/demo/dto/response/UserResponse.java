package com.example.demo.dto.response;

import com.example.demo.entity.User;
import com.example.demo.enu.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response information returned from the API")
public class UserResponse {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Username of the account", example = "johndoe")
    private String username;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Gender of the user", example = "MALE")
    private Gender gender;

    @Schema(description = "Date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "URL to the user's profile picture", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;

    @Schema(description = "Role of the user in the system", example = "USER")
    private String role;

    @Schema(description = "Indicates if the user account is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Indicates if the user's email is verified", example = "false")
    private Boolean isEmailVerified;

    @Schema(description = "Timestamp when the user was created", example = "2023-01-01T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated", example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;
}
