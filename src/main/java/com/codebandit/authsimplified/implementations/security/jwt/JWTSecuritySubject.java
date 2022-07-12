package com.codebandit.authsimplified.implementations.security.jwt;

import com.codebandit.authsimplified.abstractions.dto.SimpleIdentity;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class JWTSecuritySubject {
    private final SimpleIdentity simpleIdentity;

    public JWTSecuritySubject(SimpleIdentity simpleIdentity) {
        this.simpleIdentity = simpleIdentity;
    }

    private Map<String, String> additionalKeyValuePairs;

    public final void setAdditionalKeyValuePairs(Map<String, String> additionalKeyValuePairs) {
        this.additionalKeyValuePairs = additionalKeyValuePairs;
    }

    public String [] getSubjectAsListOfKeyValuePairs() {
        Map<String, String> keyValuePairs = new HashMap<>(Map.of("username", simpleIdentity.getUsername()));
        keyValuePairs.put("authorities", simpleIdentity.getRBACAuthoritiesList().stream()
                                                                .map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.joining(",")));
        if(additionalKeyValuePairs != null) {
            keyValuePairs.putAll(additionalKeyValuePairs);
        }
        return keyValuePairs.keySet().stream().map(key -> key.concat(":")
                .concat(keyValuePairs.get(key))).toArray(String[]::new);
    }
}
