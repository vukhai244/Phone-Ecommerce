package com.vn.account_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.account_service.dto.request.AuthenticationRequest;
import com.vn.account_service.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    // @PostMapping("/log-in")
    // ApiResponse<AuthenticationResponse> authenticate (@RequestBody
    // AuthenticationRequest authenticationRequest) {
    // boolean authenticationService.authenticate(authenticationRequest);
    // }

}
