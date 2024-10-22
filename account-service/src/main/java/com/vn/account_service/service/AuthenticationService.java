package com.vn.account_service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vn.account_service.dto.request.AuthenticationRequest;
import com.vn.account_service.dto.request.IntrospectRequest;
import com.vn.account_service.dto.response.AuthenticationResponse;
import com.vn.account_service.dto.response.IntrospectResponse;
import com.vn.account_service.entity.Account;
import com.vn.account_service.exception.AuthException;
import com.vn.account_service.exception.ErrorCode;
import com.vn.account_service.exception.UsernameException;
import com.vn.account_service.repository.IAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private IAccountRepository accountRepository;

    @Autowired
    public AuthenticationService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expityTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = accountRepository.findByUserName(authenticationRequest.getUserName())
                .orElseThrow(() -> new UsernameException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticate = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticate)
            throw new AuthException(ErrorCode.UNAUTHENTICATID);
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUserName())
                .issuer("dev")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(account.getRole())) {
            account.getRole().forEach(s -> stringJoiner.add(s));
        }
        return stringJoiner.toString();
    }
}
