package com.codebandit.authsimplified.implementables.services;

import com.codebandit.authsimplified.implementables.dto.SimpleIdentity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserIdentityService {
    SimpleIdentity getSimpleIdentity(String uniqueIdentity) throws UsernameNotFoundException;
}
