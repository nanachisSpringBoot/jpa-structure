package com.example.demo.enu;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Gender options for user profiles")
public enum Gender {
    @Schema(description = "Male gender")
    MALE, 

    @Schema(description = "Female gender")
    FEMALE, 

    @Schema(description = "Other gender options")
    OTHER
}
