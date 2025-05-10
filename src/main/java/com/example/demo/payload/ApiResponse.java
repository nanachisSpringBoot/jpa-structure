package com.example.demo.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@Schema(description = "Standard API response wrapper for all endpoints")
public class ApiResponse<T> {
    @Schema(description = "HTTP status code", example = "200")
    private int status;

    @Schema(description = "Response message", example = "Operation completed successfully")
    private String message;

    @Schema(description = "Response data payload")
    private T data;

    @Schema(description = "Pagination information for list responses")
    private Pagination pagination;

    @Getter
    @Schema(description = "Additional information or metadata")
    private Map<String, Object> extra = new HashMap<>(); // Corrected type and initialized

    public ApiResponse(int statusCode, String message) {
        this.status = statusCode;
        this.message = message;
    }

    public ApiResponse(int statusCode, String message, T data) {
        this.status = statusCode;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int statusCode, String message, List<T> data) {
        this.status = statusCode;
        this.message = message;
        this.data = (T) data;
    }

    public ApiResponse(int statusCode, String message, T data, Map<String, Object> extra) { // Updated type
        this.status = statusCode;
        this.message = message;
        this.data = data;
        this.extra = extra;
    }

    public ApiResponse(int statusCode, String message, List<T> data, Map<String, Object> extra) {
        this.status = statusCode;
        this.message = message;
        this.data = (T) data;
        this.extra = extra;
    }

    public ApiResponse(int statusCode, String message, T data, Pagination pagination) {
        this.status = statusCode;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }

    public ApiResponse(int statusCode, String message, List<T> data, Pagination pagination) {
        this.status = statusCode;
        this.message = message;
        this.data = (T) data;
        this.pagination = pagination;
    }

    public ApiResponse(int statusCode, String message, T data, Pagination pagination, Map<String, Object> extra) {
        this.status = statusCode;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
        this.extra = extra;
    }

    public ApiResponse(int statusCode, String message, List<T> data, Pagination pagination, Map<String, Object> extra) {
        this.status = statusCode;
        this.message = message;
        this.data = (T) data;
        this.pagination = pagination;
        this.extra = extra;
    }
}
