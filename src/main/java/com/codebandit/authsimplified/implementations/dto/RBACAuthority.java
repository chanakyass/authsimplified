package com.codebandit.authsimplified.implementations.dto;

import com.codebandit.authsimplified.enums.RBACAuthorityType;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@AllArgsConstructor
public class RBACAuthority implements GrantedAuthority {

    private RBACAuthorityType authority;

    @Override
    public final String getAuthority() {
        String ROLE = "ROLE";
        return ROLE.concat(authority.toString());
    }

    public final RBACAuthorityType getRBACAuthorityType() {
        return authority;
    }
}
