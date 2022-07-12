package com.codebandit.authsimplified.implementations.dto;

import com.codebandit.authsimplified.abstractions.dto.SimpleIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
public class UserDetailsImpl implements UserDetails {
    private final SimpleIdentity simpleIdentity;

    public UserDetailsImpl(SimpleIdentity simpleIdentity) {
        this.simpleIdentity = simpleIdentity;
    }

    public static UserDetails create(SimpleIdentity simpleIdentity) {
        return new UserDetailsImpl(simpleIdentity);
    }

    public SimpleIdentity getSimpleIdentity() {
        return simpleIdentity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleIdentity.getRBACAuthoritiesList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return simpleIdentity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
