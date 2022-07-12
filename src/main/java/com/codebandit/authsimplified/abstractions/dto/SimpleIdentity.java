package com.codebandit.authsimplified.abstractions.dto;

import com.codebandit.authsimplified.implementations.dto.RBACAuthority;

import java.util.List;

public interface SimpleIdentity {

    String getUsername();
    List<RBACAuthority> getRBACAuthoritiesList();

}
