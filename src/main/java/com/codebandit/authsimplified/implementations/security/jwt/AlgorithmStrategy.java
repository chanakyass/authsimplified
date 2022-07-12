package com.codebandit.authsimplified.implementations.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.codebandit.authsimplified.implementations.security.SecurityProperties;
import com.codebandit.authsimplified.enums.Strategy;
import com.codebandit.authsimplified.exceptions.SimplifiedSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class AlgorithmStrategy {

    private final SecurityProperties securityProperties;
    private final KeyGenerator keyGenerator;

    @Autowired
    public AlgorithmStrategy(SecurityProperties securityProperties, KeyGenerator keyGenerator) {
        this.securityProperties = securityProperties;
        this.keyGenerator = keyGenerator;
    }

    public Algorithm getAlgorithm(Strategy strategy) throws SimplifiedSecurityException {
        Algorithm algorithm = null;
        try {
            switch (strategy) {
                case SYMMETRIC_ENCRYPTION:
                case AUTO:
                    algorithm = Algorithm.HMAC256(securityProperties.getSecretKey());
                    break;
                case ASYMMETRIC_ENCRYPTION:
                    algorithm = Algorithm.RSA256(keyGenerator.getPublicKeyFromKeyAtPath(securityProperties.getPublicKeyPath()),
                            keyGenerator.getPrivateKeyFromKeyAtPath(securityProperties.getPrivateKeyPath()));
                    break;
            }
        } catch (GeneralSecurityException | IOException ex) {
            throw new SimplifiedSecurityException("Problem with encryption");
        }
        return algorithm;
    }

}
