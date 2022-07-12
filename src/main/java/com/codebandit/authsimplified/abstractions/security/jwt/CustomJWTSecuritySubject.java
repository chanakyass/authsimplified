package com.codebandit.authsimplified.abstractions.security.jwt;

import com.codebandit.authsimplified.abstractions.dto.SimpleIdentity;
import com.codebandit.authsimplified.implementations.security.jwt.JWTSecuritySubject;

import java.util.Map;

public abstract class CustomJWTSecuritySubject extends JWTSecuritySubject {
    public abstract Map<String, String> getAdditionalKeyValuePairs();

    public CustomJWTSecuritySubject(SimpleIdentity simpleIdentity) {
        super(simpleIdentity);
    }
    @Override
    public final String[] getSubjectAsListOfKeyValuePairs() {
        super.setAdditionalKeyValuePairs(getAdditionalKeyValuePairs());
        return super.getSubjectAsListOfKeyValuePairs();
    }
}
