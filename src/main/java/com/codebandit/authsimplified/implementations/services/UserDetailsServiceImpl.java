package com.codebandit.authsimplified.implementations.services;

import com.codebandit.authsimplified.abstractions.dto.SimpleIdentity;
import com.codebandit.authsimplified.abstractions.services.UserIdentityService;
import com.codebandit.authsimplified.implementations.dto.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserIdentityService userIdentityService;

    @Autowired
    public UserDetailsServiceImpl(UserIdentityService userIdentityService) {
        this.userIdentityService = userIdentityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         SimpleIdentity simpleIdentity = userIdentityService.getSimpleIdentity(username);
         return UserDetailsImpl.create(simpleIdentity);
    }
}
