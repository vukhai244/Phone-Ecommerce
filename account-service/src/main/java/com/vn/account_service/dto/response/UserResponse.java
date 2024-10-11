package com.vn.account_service.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private Set<String> role;
    private LocalDateTime createdAt;
}
