package com.codebandit.authsimplified.implementations.security.jwt;

import com.codebandit.authsimplified.exceptions.SimplifiedSecurityException;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenUtil {

    String extractTokenAndGetSubject(HttpServletRequest request) throws SimplifiedSecurityException;

    String extractToken(HttpServletRequest request);

    void validate(String token) throws SimplifiedSecurityException;

    String getUsernameFromToken(String token);

    String generateToken( JWTSecuritySubject securitySubject) throws SimplifiedSecurityException;

}
