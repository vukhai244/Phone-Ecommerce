package com.vn.account_service.dto.request;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Getter
@Setter
@NoArgsConstructor
public class UserCreationRequest {

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @NotBlank(message = "User name is mandatory")
    @Size(min = 5, max = 15, message = "User name must be between 5 and 15 characters")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phoneNumber;

    private String address;

    private Set<String> role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
