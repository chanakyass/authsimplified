package com.codebandit.authsimplified.implementations.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.codebandit.authsimplified.implementations.security.SecurityProperties;
import com.codebandit.authsimplified.exceptions.SimplifiedSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class JwtTokenUtilImpl implements JwtTokenUtil {

    private final SecurityProperties securityProperties;
    private final AlgorithmStrategy algorithmStrategy;

    private final String USERNAME = "username";
    private final String COMMA = ",";
    private final String COLON = ":";

    @Autowired
    public JwtTokenUtilImpl(SecurityProperties securityProperties, AlgorithmStrategy algorithmStrategy) {
        this.securityProperties = securityProperties;
        this.algorithmStrategy = algorithmStrategy;
    }

    @Override
    public void validate(String token) throws SimplifiedSecurityException {
        try {
            JWTVerifier verifier = JWT.require(algorithmStrategy.getAlgorithm(securityProperties.getStrategy()))
                    .withIssuer(securityProperties.getIssuer())
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        }
        catch (JWTVerificationException jwtException){
            throw new SimplifiedSecurityException("Problem with jwt verification");
        }
    }

    public String generateToken(JWTSecuritySubject securitySubject) throws SimplifiedSecurityException {
        String [] keyValuePairs = securitySubject.getSubjectAsListOfKeyValuePairs();
        String token = "";
        try {
            token = JWT.create()
                    .withSubject(String.join(COMMA, keyValuePairs))
                    .withIssuer(securityProperties.getIssuer())
                    .sign(algorithmStrategy.getAlgorithm(securityProperties.getStrategy()));
        } catch (JWTCreationException | IllegalArgumentException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            throw new SimplifiedSecurityException("Issue with generating token");
        }

        return token;

    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String subjectString = decodedJWT.getSubject();
            Map<String, String> decodedTokenMap = Arrays.stream(subjectString.split(COMMA))
                    .map(s -> s.split(COLON))
                    .collect(Collectors.toMap(
                            a -> a[0],  //key
                            a -> a[1]   //value
                    ));
            if(decodedTokenMap.containsKey(USERNAME)) {
                return decodedTokenMap.get(USERNAME);
            }
            else {
                throw new SimplifiedSecurityException("No username");
            }
        }
        catch (JWTDecodeException exception) {
            exception.printStackTrace();
            throw new SimplifiedSecurityException(exception.getLocalizedMessage());
        }
    }


    @Override
    public String extractTokenAndGetSubject(HttpServletRequest request) throws SimplifiedSecurityException {

        String token = Optional.of(extractToken(request)).orElseThrow(()->new SimplifiedSecurityException("Empty Token"));
        return getUsernameFromToken(token);
    }

    @Override
    public String extractToken(HttpServletRequest request) {

        // Get authorization header and validate
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (header == null || header.isEmpty() || !header.startsWith("Bearer ")) ?
                null : header.split(" ")[1].trim();

    }
}
