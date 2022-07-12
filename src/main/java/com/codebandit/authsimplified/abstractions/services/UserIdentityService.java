package com.codebandit.authsimplified.abstractions.services;

import com.codebandit.authsimplified.abstractions.dto.SimpleIdentity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserIdentityService {
    SimpleIdentity getSimpleIdentity(String uniqueIdentity) throws UsernameNotFoundException;
}
