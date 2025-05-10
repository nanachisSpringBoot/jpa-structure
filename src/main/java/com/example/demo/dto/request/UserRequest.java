package com.example.demo.dto.request;

import com.example.demo.enu.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "User request information for creating or updating a user")
public class UserRequest {
    @Schema(description = "Username for the account", example = "johndoe", required = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Email address", example = "john.doe@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
        description = "Password for the account", 
        example = "Password123$", 
        required = true,
        minLength = 8
    )
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
             message = "Password must be at least 8 characters long and include at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;

    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Gender of the user", example = "MALE")
    private Gender gender;

    @Schema(description = "Date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "URL to the user's profile picture", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;
}
